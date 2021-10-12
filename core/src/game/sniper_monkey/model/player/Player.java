package game.sniper_monkey.model.player;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.Config;
import game.sniper_monkey.model.PhysicsPosition;
import game.sniper_monkey.model.collision.CollisionEngine;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.model.world.CallbackTimer;
import game.sniper_monkey.model.world.GameObject;
import game.sniper_monkey.model.world.TimerObserver;

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
public class Player extends GameObject {
    // TODO read these values from config file
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
    private final FluctuatingAttribute stamina = new FluctuatingAttribute(MAX_STAMINA);
    private final FluctuatingAttribute health = new FluctuatingAttribute(MAX_HEALTH);
    private final FluctuatingAttribute blockFactor = new FluctuatingAttribute(MIN_BLOCK, MAX_BLOCK);
    private final CallbackTimer blockTimer = new CallbackTimer(.2f, () -> canBlock = true);
    private final CallbackTimer swapTimer = new CallbackTimer(SWAP_COOLDOWN, () -> canSwap = true);
    private final List<SwappedFighterObserver> swappedFighterObservers = new ArrayList<>();
    private final Fighter primaryFighter;
    private final Fighter secondaryFighter;
    private final Map<PlayerInputAction, Boolean> inputActions = new HashMap<>();
    private final float blockDefenseFactor;
    private final PhysicsPosition physicsPos = new PhysicsPosition(new Vector2(0, 0));
    boolean isGrounded = true;
    private Fighter activeFighter;
    private FighterAnimation currentFighterAnimation; // TODO set this to static for very many fun
    private boolean lookingRight;
    private State movementState = this::groundedState;
    private State abilityState = this::inactiveState;
    private boolean canBlock = true;
    private boolean canSwap = true;


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
    }

    /**
     * Creates a player with a position in the world
     *
     * @param position The initial position of the player.
     */
    public Player(Vector2 position, Fighter primaryFighter, Fighter secondaryFighter, int collisionMask) {
        super(position, true);
        physicsPos.setPosition(position);
        this.primaryFighter = primaryFighter;
        this.secondaryFighter = secondaryFighter;
        currentFighterAnimation = FighterAnimation.IDLING;
        initActiveFighter(primaryFighter);
        resetInputActions();
        blockDefenseFactor = 0.4f;
        setHitboxMask(collisionMask);
    }

    /**
     * Creates a player with a position in the world
     *
     * @param position The initial position of the player.
     */
    public Player(Vector2 position, Fighter primaryFighter, Fighter secondaryFighter) {
        this(position, primaryFighter, secondaryFighter, 0);
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
        // TODO set correct blocking values, read these from config. If different values in different movement states, use percentage of config value.
        currentFighterAnimation = FighterAnimation.DYING;
        if (inputActions.get(PlayerInputAction.BLOCK)) {
            blockFactor.setRegenerating(false);
            blockFactor.setDraining(true, BASE_BLOCK_DRAIN);
            blockTimer.reset();
        } else {
            blockFactor.setDraining(false);
            blockFactor.setRegenerating(true, BASE_BLOCK_REGEN);
            abilityState = this::inactiveState;
            blockTimer.start();
            movementState.performState();
        }
    }

    private void attacking1State() {

    }

    private void attacking2State() {

    }

    private void swappingFighterState() {
        swapFighters();
        swapTimer.reset();
        swapTimer.start();
        physicsPos.setPosition(new Vector2(100, 100)); // TODO set to spawn pos
        super.setPosition(physicsPos.getPosition());
        for (SwappedFighterObserver observer : swappedFighterObservers) {
            observer.onFighterSwap(this);
        }
        abilityState = this::inactiveState;
        canSwap = false;
    }

    private void inactiveState() {
        if (inputActions.get(PlayerInputAction.ATTACK1)) {
            // TODO performAttack
            stamina.decrease(activeFighter.getStaminaDecrease(1));
            abilityState = this::attacking1State;
            return;
        } else if (inputActions.get(PlayerInputAction.ATTACK2)) {
            // TODO performAttack
            stamina.decrease(activeFighter.getStaminaDecrease(2));
            abilityState = this::attacking2State;
            return;
        } else if (inputActions.get(PlayerInputAction.BLOCK)) {
            if (canBlock) {
                abilityState = this::blockingState;
                canBlock = false;
                return;
            }
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
            currentFighterAnimation = FighterAnimation.FALLING;
        } else if (physicsPos.getVelocity().y > 0) {
            currentFighterAnimation = FighterAnimation.JUMPING;
        }

    }

    // State helper methods
    private void groundedState() {
        currentFighterAnimation = FighterAnimation.IDLING;
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
            currentFighterAnimation = FighterAnimation.MOVING;
            moveRight();
        }
        if (inputActions.get(PlayerInputAction.MOVE_LEFT)) {
            currentFighterAnimation = FighterAnimation.MOVING;
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

    //TODO documentation
    public FighterAnimation getCurrentFighterAnimation() {
        return currentFighterAnimation;
    }

    //TODO documentation
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

    /**
     * Updates the inputActions map by setting the action to true, which translates to "player try to do this action".
     *
     * @param action The input action to be set.
     */
    public void setInputAction(PlayerInputAction action) {
        inputActions.replace(action, true);
    }

    /**
     * Decreases the players health.
     *
     * @param damageAmount a float 0..n. Is the damage that the other fighter has done to the player.
     */
    public void takeDamage(float damageAmount) {
        if (false/*currentState == blockingState*/) { // change when state checking has been implemented
            health.decrease(damageAmount * (1 - activeFighter.DEFENSE_FACTOR) * (1 - blockDefenseFactor));
        } else {
            health.decrease(damageAmount * (1 - activeFighter.DEFENSE_FACTOR)); // TODO make getter for defense factor instead?
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

    /**
     * Get the type of the active fighter. Will be a subclass of Fighter.
     *
     * @return Type of the active fighter.
     */
    public Class<?> getActiveFighterClass() {
        return activeFighter.getClass();
    }

    /**
     * Returns the class of the inactive fighter.
     *
     * @return The class of the inactive fighter.
     */
    public Class<?> getInactiveFighterClass() {
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
    public Class<?> getAttackClass(int attackNum) {
        return activeFighter.getAttackClass(attackNum);
    }

    private void initActiveFighter(Fighter fighter) {
        activeFighter = fighter;
        setHitboxPos(physicsPos.getPosition());
        setHitboxSize(fighter.getHitboxSize());
        stamina.setRegenerationAmount(BASE_STAMINA_REGEN * activeFighter.SPEED_FACTOR);
    }

    private void handleLookingDirection() {
        if (physicsPos.getVelocity().x > 0) {
            lookingRight = true;
        } else if (physicsPos.getVelocity().x < 0) {
            lookingRight = false;
        }
    }

    /**
     * Updates the class player every frame
     *
     * @param deltaTime The time between frames.
     */
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
        handleCollision(deltaTime);
        super.setPosition(physicsPos.getPosition());
    }

    // TODO refactor this behemoth
    private void handleCollision(float deltaTime) {

        // executes shawn mendez. inspired by shawn's collision algorithm
        boolean collidesXAxisNextFrame = CollisionEngine.getCollision(getHitbox(), new Vector2(physicsPos.getVelocity().x, 0).scl(deltaTime), getHitboxMask());
        if (collidesXAxisNextFrame) {
            // while it doesn't collide with an x position approaching the object it will collide with, then see if it collides with an x position a tiny bit closer until it collides.
            while (!CollisionEngine.getCollision(getHitbox(), new Vector2(Math.signum(physicsPos.getVelocity().x) / 2f, 0), getHitboxMask())) {
                setHitboxPos(new Vector2(getHitbox().getPosition().x + Math.signum(physicsPos.getVelocity().x) / 2f, getHitbox().getPosition().y));
            }
            // Then set x velocity to zero, and the x position is already set to the closest it can get to the object it collides with.
            physicsPos.setVelocity(new Vector2(0, physicsPos.getVelocity().y));
        }

        setHitboxPos(getHitbox().getPosition().add(physicsPos.getVelocity().x * deltaTime, 0));

        isGrounded = false;
        if (CollisionEngine.getCollision(getHitbox(), new Vector2(0, physicsPos.getVelocity().y).scl(deltaTime), getHitboxMask())) {
            if (physicsPos.getVelocity().y < 0) isGrounded = true;
            while (!CollisionEngine.getCollision(getHitbox(), new Vector2(0, Math.signum(physicsPos.getVelocity().y) / 2f), getHitboxMask())) {
                setHitboxPos(new Vector2(getHitbox().getPosition().x, getHitbox().getPosition().y + Math.signum(physicsPos.getVelocity().y) / 2f));
            }
            physicsPos.setVelocity(new Vector2(physicsPos.getVelocity().x, 0));
        }
        setHitboxPos(getHitbox().getPosition().add(0, physicsPos.getVelocity().y * deltaTime));
        physicsPos.setPosition(getHitbox().getPosition());
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
     * Subscribes the observer to changes made when the attack cooldown changes.
     *
     * @param timerObserver The observer that wants to be subscribed to the changes.
     */
    public void registerAttackCooldownObserver(TimerObserver timerObserver) {
        // TODO since cooldown effects all attacks should set this in player
    }

    /**
     * Subscribes the observer to changes made when the attack cooldown changes.
     *
     * @param timerObserver The observer that wants to be unsubscribed to the changes.
     */
    public void unregisterAttackCooldownObserver(TimerObserver timerObserver) {
        // TODO
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

    @FunctionalInterface
    private interface State {
        void performState();
    }
}
