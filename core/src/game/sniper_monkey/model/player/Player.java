package game.sniper_monkey.model.player;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.Config;
import game.sniper_monkey.model.PhysicsPosition;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.model.player.fighter.attack.IAttack;
import game.sniper_monkey.model.world.CallbackTimer;
import game.sniper_monkey.model.world.GameObject;
import game.sniper_monkey.model.world.TimerObserver;
import game.sniper_monkey.utils.collision.CollisionResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A player GameObject to be controlled in the world, handling its movement, collision, blocking and attacking
 * using two internal Fighter subclasses.
 *
 * @author Elias Falk
 * @author Vincent Hellner
 * @author Kevin Jeryd
 * @author Dadi Andrason
 */
public class Player extends GameObject implements ReadablePlayer, ControllablePlayer, DamageablePlayer {

    private static final float MAX_X_VEL;
    private static final float VEL_GAIN;
    private static final float JUMP_GAIN;
    private static final int MAX_STAMINA;
    private static final int MAX_HEALTH;
    private static final float MIN_BLOCK;
    private static final float MAX_BLOCK;
    private static final float BASE_BLOCK_REGEN;
    private static final float BASE_BLOCK_DRAIN;
    private static final float BASE_STAMINA_REGEN;
    private static final float SWAP_COOLDOWN;
    private static final float BLOCK_COOLDOWN;
    private final FluctuatingAttribute stamina = new FluctuatingAttribute(MAX_STAMINA);
    private final FluctuatingAttribute health = new FluctuatingAttribute(MAX_HEALTH);
    private final FluctuatingAttribute blockFactor = new FluctuatingAttribute(MIN_BLOCK, MAX_BLOCK);
    private final CallbackTimer blockTimer = new CallbackTimer(BLOCK_COOLDOWN, () -> canBlock = true);
    private final CallbackTimer swapTimer = new CallbackTimer(SWAP_COOLDOWN, () -> canSwap = true);
    private final CallbackTimer hitStun = new CallbackTimer(0, () -> canAttack = true);
    private final List<SwappedFighterObserver> swappedFighterObservers = new ArrayList<>();
    private final Fighter primaryFighter;
    private final Fighter secondaryFighter;
    private final Map<PlayerInputAction, Boolean> inputActions = new HashMap<>();
    private final Vector2 spawnPos;
    private PhysicsPosition physicsPos = new PhysicsPosition(new Vector2(0, 0));
    boolean isGrounded = true;
    private Fighter activeFighter;
    private PhysicalState currentPhysicalState; // TODO set this to static for very many fun
    private boolean lookingRight;
    private State movementState = this::groundedState;
    private State abilityState = this::inactiveState;
    private boolean canBlock = true;
    private boolean canSwap = true;
    private boolean canAttack = true;


    static {
        String filepath = "cfg/player_values.cfg";
        Config.readConfigFile(filepath);
        MAX_X_VEL = Config.getNumber(filepath, "MAX_X_VEL");
        VEL_GAIN = Config.getNumber(filepath, "VEL_GAIN");
        JUMP_GAIN = Config.getNumber(filepath, "JUMP_GAIN");
        MAX_STAMINA = (int) Config.getNumber(filepath, "MAX_STAMINA");
        MAX_HEALTH = (int) Config.getNumber(filepath, "MAX_HEALTH");
        MIN_BLOCK = Config.getNumber(filepath, "MIN_BLOCK");
        MAX_BLOCK = Config.getNumber(filepath, "MAX_BLOCK");
        BASE_BLOCK_REGEN = Config.getNumber(filepath, "BASE_BLOCK_REGEN");
        BASE_BLOCK_DRAIN = Config.getNumber(filepath, "BASE_BLOCK_DRAIN");
        BASE_STAMINA_REGEN = Config.getNumber(filepath, "BASE_STAMINA_REGEN");
        SWAP_COOLDOWN = Config.getNumber(filepath, "SWAP_COOLDOWN");
        BLOCK_COOLDOWN = Config.getNumber(filepath, "BLOCK_COOLDOWN");
    }


    /**
     * Creates a player.
     *
     * @param spawnPos         The spawn position of the player. Will initially be placed here, as well as spawn there when swapping fighter.
     * @param primaryFighter   The primary fighter, this will initially act as the active fighter.
     * @param secondaryFighter The secondary fighter. Will become the active fighter after the player swaps fighter.
     * @param collisionMask    The collision mask of the fighter.
     * @see game.sniper_monkey.utils.collision.CollisionMasks
     */
    public Player(Vector2 spawnPos, Fighter primaryFighter, Fighter secondaryFighter, int collisionMask) {
        super(spawnPos, true);
        physicsPos.setPosition(spawnPos);
        this.spawnPos = spawnPos;
        this.primaryFighter = primaryFighter;
        this.secondaryFighter = secondaryFighter;
        currentPhysicalState = PhysicalState.IDLING;
        initActiveFighter(primaryFighter);
        resetInputActions();
        setHitboxMask(collisionMask);
    }

    /**
     * Creates a player with a position in the world
     *
     * @param spawnPos The initial position of the player.
     */
    public Player(Vector2 spawnPos, Fighter primaryFighter, Fighter secondaryFighter) {
        this(spawnPos, primaryFighter, secondaryFighter, 0);
    }

    /**
     * Creates a Player object
     */
    public Player(Fighter primaryFighter, Fighter secondaryFighter) {
        this(new Vector2(0, 0), primaryFighter, secondaryFighter);
    }

    /* Ability states */

    /**
     * Checks if the player is blocking.
     * If it is, slowly decrease the blockDefenseFactor.
     * If it isn't, reset the blockDefenseFactor and set the next state.
     */
    private void blockingState() {
        currentPhysicalState = PhysicalState.DYING;
        if (inputActions.get(PlayerInputAction.BLOCK)) {
            blockFactor.setRegenerating(false);
            blockFactor.setDraining(true, BASE_BLOCK_DRAIN);
            blockTimer.reset();
        } else {
            blockFactor.setDraining(false);
            blockFactor.setRegenerating(true, BASE_BLOCK_REGEN); // TODO discuss: should stamina effect the speed of block regen?
            abilityState = this::inactiveState;
            blockTimer.start();
            movementState.performState();
        }
    }

    public float getHealth() {
        return health.getCurrentValue();
    }

    private void attackingState() {
        hitStun.reset();
        if (!activeFighter.isAttacking()) {
            hitStun.start();
            abilityState = this::inactiveState;
        }
    }

    private void swappingFighterState() {
        swapFighters();
        swapTimer.reset();
        swapTimer.start();
        physicsPos.setPosition(spawnPos);
        physicsPos.setVelocity(new Vector2(0, 0));
        super.setPosition(physicsPos.getPosition());
        for (SwappedFighterObserver observer : swappedFighterObservers) {
            observer.onFighterSwap(this);
        }
        abilityState = this::inactiveState;
        canSwap = false;
    }

    public float getAttackLength(int attackNum) {
        return activeFighter.getAttackLength(attackNum);
    }

    private void inactiveState() {
        if (inputActions.get(PlayerInputAction.ATTACK1) && canAttack && stamina.getCurrentValue() - activeFighter.getStaminaCost(0) >= 0 && !activeFighter.isAttacking()) {
            performAttack(0);
            currentPhysicalState = PhysicalState.ATTACKING1;
            abilityState = this::attackingState;
            return;
        } else if (inputActions.get(PlayerInputAction.ATTACK2) && canAttack && stamina.getCurrentValue() - activeFighter.getStaminaCost(1) >= 0 && !activeFighter.isAttacking()) {
            performAttack(1);
            currentPhysicalState = PhysicalState.ATTACKING2;
            abilityState = this::attackingState;
            return;
        } else if (canBlock && inputActions.get(PlayerInputAction.BLOCK)) {
            abilityState = this::blockingState;
            canBlock = false;
            return;
        } else if (inputActions.get(PlayerInputAction.SWAP_FIGHTER)) {
            // TODO swapFighter
            if (canSwap) {
                abilityState = this::swappingFighterState;
                return;
            }
        }
        movementState.performState();
    }

    /* Movement states */
    private void inAirState() {
        if (isGrounded) {
            movementState = this::groundedState;
            groundedState();
            return;
        }

        if (inputActions.get(PlayerInputAction.DROP)) {
            physicsPos.setVelocity(physicsPos.getVelocity().add(new Vector2(0, -JUMP_GAIN / 5)));
        }

        handleHorizontalMovement();
        if (physicsPos.getVelocity().y < 0) {
            currentPhysicalState = PhysicalState.FALLING;
        } else if (physicsPos.getVelocity().y > 0) {
            currentPhysicalState = PhysicalState.JUMPING;
        }

    }

    private void groundedState() {
        currentPhysicalState = PhysicalState.IDLING;
        handleHorizontalMovement();

        if (inputActions.get(PlayerInputAction.JUMP)) {
            jump();
            movementState = this::inAirState;
            return;
        }
        if (physicsPos.getVelocity().y < 0) {
            movementState = this::inAirState;
            return;
        }
    }

    // State helper methods
    private void moveLeft() {
        Vector2 newVel = physicsPos.getVelocity().add(new Vector2(-VEL_GAIN, 0));
        if (newVel.x <= -MAX_X_VEL) {
            newVel = new Vector2(-MAX_X_VEL, physicsPos.getVelocity().y);
        }
        physicsPos.setVelocity(newVel);
    }

    private void moveRight() {
        Vector2 newVel = physicsPos.getVelocity().add(new Vector2(VEL_GAIN, 0));
        if (newVel.x >= MAX_X_VEL) {
            newVel = new Vector2(MAX_X_VEL, physicsPos.getVelocity().y);
        }
        physicsPos.setVelocity(newVel);
    }

    private void jump() {
        Vector2 newVel = physicsPos.getVelocity().add(new Vector2(0, JUMP_GAIN));
        physicsPos.setVelocity(newVel);
    }

    private void handleHorizontalMovement() {
        if (inputActions.get(PlayerInputAction.MOVE_RIGHT)) {
            currentPhysicalState = PhysicalState.MOVING;
            moveRight();
        }
        if (inputActions.get(PlayerInputAction.MOVE_LEFT)) {
            currentPhysicalState = PhysicalState.MOVING;
            moveLeft();
        }
    }

    private void swapFighters() {
        if (primaryFighter.equals(activeFighter)) {
            initActiveFighter(secondaryFighter);
        } else {
            initActiveFighter(primaryFighter);
        }
    }

    private void performAttack(int attackNum) {
        activeFighter.performAttack(attackNum, this.getPos(), getHitboxMask(), isLookingRight());
        stamina.decrease(activeFighter.getStaminaCost(attackNum));
        hitStun.setTimerLength(activeFighter.getHitStunTime(attackNum));
        canAttack = false;
    }

    @Override
    public PhysicalState getCurrentPhysicalState() {
        return currentPhysicalState;
    }


    @Override
    public boolean isLookingRight() {
        return lookingRight;
    }

    private void initInputActions() {
        inputActions.put(PlayerInputAction.JUMP, false);
        inputActions.put(PlayerInputAction.ATTACK1, false);
        inputActions.put(PlayerInputAction.ATTACK2, false);
        inputActions.put(PlayerInputAction.BLOCK, false);
        inputActions.put(PlayerInputAction.MOVE_RIGHT, false);
        inputActions.put(PlayerInputAction.MOVE_LEFT, false);
        inputActions.put(PlayerInputAction.DROP, false);
        inputActions.put(PlayerInputAction.SWAP_FIGHTER, false);
    }

    private void resetInputActions() {
        if (inputActions.isEmpty()) {
            initInputActions();
        } else {
            for (Map.Entry<PlayerInputAction, Boolean> inputAction : inputActions.entrySet()) {
                inputAction.setValue(false);
            }
        }
    }

    @Override
    public void setInputAction(PlayerInputAction action) {
        inputActions.replace(action, true);
    }

    @Override
    public void takeDamage(float damageAmount) {
        if (inputActions.get(PlayerInputAction.BLOCK)) { // change when state checking has been implemented
            health.decrease(damageAmount * (1 - activeFighter.DEFENSE_FACTOR) * (1 - blockFactor.getCurrentValue()));
        } else {
            health.decrease(damageAmount * (1 - activeFighter.DEFENSE_FACTOR));
        }
    }

    /**
     * Checks if the player is dead.
     *
     * @return true if the player is dead, false if the player is alive.
     */
    public boolean isDead() {
        return health.getCurrentValue() == 0;
    }

    @Override
    public Class<? extends Fighter> getActiveFighterClass() {
        return activeFighter.getClass();
    }

    @Override
    public Class<? extends Fighter> getInactiveFighterClass() {
        if (activeFighter == primaryFighter) {
            return secondaryFighter.getClass();
        } else {
            return primaryFighter.getClass();
        }
    }

    /**
     * Returns the class of the active fighter's attack, specified by the attack number.
     *
     * @param attackNum The attack specifier. Starts at 0.
     * @return The class of the specified attack of the active fighter.
     */
    public Class<? extends IAttack> getAttackClass(int attackNum) {
        return activeFighter.getAttackClass(attackNum);
    }

    private void initActiveFighter(Fighter fighter) {
        activeFighter = fighter;
        setHitboxPos(physicsPos.getPosition());
        setHitboxSize(fighter.getHitboxSize());
        stamina.setRegenerating(true, BASE_STAMINA_REGEN * activeFighter.SPEED_FACTOR);
    }

    private void handleLookingDirection() {
        boolean isMovingRight = physicsPos.getVelocity().x > 0;
        boolean isMovingLeft = physicsPos.getVelocity().x < 0;
        if (isMovingRight) {
            lookingRight = true;
        } else if (isMovingLeft) {
            lookingRight = false;
        }
    }

    @Override
    public void update(float deltaTime) {
        stamina.update(deltaTime);
        abilityState.performState();
        blockFactor.update(deltaTime);
        updatePlayerPos(deltaTime);
        handleLookingDirection();
        resetInputActions();
    }

    private void updatePlayerPos(float deltaTime) {
        physicsPos.update(deltaTime);
        isGrounded = false;
        physicsPos = CollisionResponse.handleCollision(deltaTime, getHitbox(), getHitboxMask(), physicsPos, () -> {}, () -> {
            if(physicsPos.getVelocity().y < 0) isGrounded = true;
        });
        super.setPosition(physicsPos.getPosition());
    }


    /**
     * Subscribes the observer to changes made when the health attribute changes.
     *
     * @param observer The observer that wants to be subscribed to the changes.
     */
    public void registerHealthObserver(FluctuatingAttributeObserver observer) {
        health.registerObserver(observer);
    }

    /**
     * Unsubscribes the observer to changes made when the health attribute changes.
     *
     * @param observer The observer that wants to be unsubscribed to the changes.
     */
    public void unregisterHealthObserver(FluctuatingAttributeObserver observer) {
        health.unRegisterObserver(observer);
    }

    /**
     * Subscribes the observer to changes made when the stamina attribute changes.
     *
     * @param observer The observer that wants to be subscribed to the changes.
     */
    public void registerStaminaObserver(FluctuatingAttributeObserver observer) {
        stamina.registerObserver(observer);
    }

    /**
     * Unsubscribes the observer to changes made when the stamina attribute changes.
     *
     * @param observer The observer that wants to be unsubscribed to the changes.
     */
    public void unregisterStaminaObserver(FluctuatingAttributeObserver observer) {
        stamina.unRegisterObserver(observer);
    }

    /**
     * Subscribes the observer to changes made when the block attribute changes.
     *
     * @param observer The observer that wants to be subscribed to the changes.
     */
    public void registerBlockObserver(FluctuatingAttributeObserver observer) {
        blockFactor.registerObserver(observer);
    }

    /**
     * Unsubscribes the observer to changes made when the block attribute changes.
     *
     * @param observer The observer that wants to be unsubscribed to the changes.
     */
    public void unregisterBlockObserver(FluctuatingAttributeObserver observer) {
        blockFactor.unRegisterObserver(observer);
    }

    /**
     * Subscribes the observer to changes made when the block cooldown changes.
     *
     * @param timerObserver The observer that wants to be subscribed to the changes.
     */
    public void registerBlockCooldownObserver(TimerObserver timerObserver) {
        blockTimer.registerTimerObserver(timerObserver);
    }

    /**
     * Unsubscribes the observer to changes made when the block cooldown changes.
     *
     * @param timerObserver The observer that wants to be unsubscribed to the changes.
     */
    public void unregisterBlockCooldownObserver(TimerObserver timerObserver) {
        blockTimer.unRegisterTimerObserver(timerObserver);
    }

    /**
     * Subscribes the observer to changes made when the swap cooldown changes.
     *
     * @param timerObserver The observer that wants to be subscribed to the changes.
     */
    public void registerSwapCooldownObserver(TimerObserver timerObserver) {
        swapTimer.registerTimerObserver(timerObserver);
    }

    /**
     * Unsubscribes the observer to changes made when the swap cooldown changes.
     *
     * @param timerObserver The observer that wants to be subscribed to the changes.
     */
    public void unregisterSwapCooldownObserver(TimerObserver timerObserver) {
        swapTimer.unRegisterTimerObserver(timerObserver);
    }

    /**
     * Subscribes the observer to be notified when the player has swapped fighter.
     *
     * @param observer The observer that wants to be subscribed to when the player swapped fighter.
     */
    public void registerSwappedFighterObserver(SwappedFighterObserver observer) {
        swappedFighterObservers.add(observer);
    }

    /**
     * Unsubscribes the observer to not be notified when the player has swapped fighter.
     *
     * @param observer The observer that wants to be subscribed to when the player swapped fighter.
     */
    public void unregisterSwappedFighterObserver(SwappedFighterObserver observer) {
        swappedFighterObservers.remove(observer);
    }

    /**
     * Subscribes the observer to changes made when the hitstun changes.
     *
     * @param timerObserver The observer that wants to be subscribed to the changes.
     */
    public void registerHitStunObserver(TimerObserver timerObserver) {
        hitStun.registerTimerObserver(timerObserver);
    }

    /**
     * Unsubscribes the observer to changes made when the hitstun changes.
     *
     * @param timerObserver The observer that wants to be subscribed to the changes.
     */
    public void unregisterHitStunObserver(TimerObserver timerObserver) {
        hitStun.unRegisterTimerObserver(timerObserver);
    }

    @FunctionalInterface
    private interface State {
        void performState();
    }
}
