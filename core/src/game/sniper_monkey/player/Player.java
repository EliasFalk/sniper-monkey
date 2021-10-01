package game.sniper_monkey.player;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.PhysicsPosition;
import game.sniper_monkey.collision.CollisionEngine;
import game.sniper_monkey.player.fighter.Fighter;
import game.sniper_monkey.world.GameObject;

import java.util.HashMap;
import java.util.Map;

public class Player extends GameObject {
    interface State {
        void performState();
    }

    boolean isGrounded = true;

    private float blockDefenseFactor;

    // TODO read these values from config file
    private static final float MAX_X_VEL = 200f;
    private static final float VEL_GAIN = 25f;
    private static final float JUMP_GAIN = 300f;
    private static final int MAX_STAMINA = 100;
    private static final int MAX_HEALTH = 100;

    private final FluctuatingAttribute stamina = new FluctuatingAttribute(MAX_STAMINA);
    private final FluctuatingAttribute health = new FluctuatingAttribute(MAX_HEALTH);
    private final FluctuatingAttribute blockFactor = new FluctuatingAttribute(0.4f, 0.7f);

    private Fighter activeFighter;
    private final Fighter primaryFighter;
    private final Fighter secondaryFighter;
    private FighterAnimation currentFighterAnimation; // TODO set this to static for very many fun

    private boolean lookingRight;

    private State abilityState = this::inactiveState;
    private State movementState = this::groundedState;

    private PhysicsPosition physicsPos = new PhysicsPosition(new Vector2(0, 0));
    private final Map<PlayerInputAction, Boolean> inputActions = new HashMap<>();

    /* Ability states */

    /**
     * Checks if the player is blocking.
     * If it is, slowly decrease the blockDefenseFactor.
     * If it isn't, reset the blockDefenseFactor and set the next state.
     */
    private void blockingState() {
        // TODO set correct blocking values, read these from config. If different values in different movement states, use percentage of config value.
        blockFactor.setDrainAmount(0.2f);
        if (inputActions.get(PlayerInputAction.BLOCK)) {
            blockFactor.setRegenerating(false);
            blockFactor.setDraining(true);
        } else {
            blockFactor.setDraining(false);
            blockFactor.setRegenerating(true, 0.1f);
            abilityState = this::inactiveState;
            movementState.performState();
        }
    }

    private void attacking1State() {

    }

    private void attacking2State() {

    }

    private void swappingFighterState() {

    }

    private void inactiveState() {
        if (inputActions.get(PlayerInputAction.ATTACK1)) {
            // TODO performAttack
            abilityState = this::attacking1State;
            return;
        } else if (inputActions.get(PlayerInputAction.ATTACK2)) {
            // TODO performAttack
            abilityState = this::attacking2State;
            return;
        } else if (inputActions.get(PlayerInputAction.BLOCK)) {
            abilityState = this::blockingState;
            return;
        } else if (inputActions.get(PlayerInputAction.SWAP_FIGHTER)) {
            // TODO swapFighter
            abilityState = this::swappingFighterState;
            return;
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

    public FighterAnimation getCurrentFighterAnimation() {
        return currentFighterAnimation;
    }

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
        if (false/*currentState == blockingState*/) { // change when statechecking has been implemented
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

    private void initActiveFighter(Fighter fighter) {
        activeFighter = fighter;
        setHitboxPos(physicsPos.getPosition());
        setHitboxSize(fighter.getHitboxSize());
        stamina.setRegenerationAmount(10f * activeFighter.SPEED_FACTOR);
    }

    /**
     * Creates a player with a position in the world
     *
     * @param position The initial position of the player.
     */
    public Player(Vector2 position, Fighter primaryFighter, Fighter secondaryFighter) {
        super(position);
        physicsPos.setPosition(position);
        this.primaryFighter = primaryFighter;
        this.secondaryFighter = secondaryFighter;
        currentFighterAnimation = FighterAnimation.IDLING;
        initActiveFighter(primaryFighter);
        resetInputActions();
        blockDefenseFactor = 0.4f;
    }

    /**
     * Creates a Player object
     */
    public Player(Fighter primaryFighter, Fighter secondaryFighter) {
        this(new Vector2(0, 0), primaryFighter, secondaryFighter);
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
        System.out.println(blockFactor.getCurrentValue());
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
        boolean collidesXAxisNextFrame = CollisionEngine.getCollision(getHitbox(), new Vector2(physicsPos.getVelocity().x, 0).scl(deltaTime));
        if (collidesXAxisNextFrame) {
            // while it doesn't collide with an x position approaching the object it will collide with, then see if it collides with an x position a tiny bit closer until it collides.
            while (!CollisionEngine.getCollision(getHitbox(), new Vector2(Math.signum(physicsPos.getVelocity().x) / 2f, 0))) {
                setHitboxPos(new Vector2(getHitbox().getPosition().x + Math.signum(physicsPos.getVelocity().x) / 2f, getHitbox().getPosition().y));
            }
            // Then set x velocity to zero, and the x position is already set to the closest it can get to the object it collides with.
            physicsPos.setVelocity(new Vector2(0, physicsPos.getVelocity().y));
        }

        setHitboxPos(getHitbox().getPosition().add(physicsPos.getVelocity().x * deltaTime, 0));

        isGrounded = false;
        if (CollisionEngine.getCollision(getHitbox(), new Vector2(0, physicsPos.getVelocity().y).scl(deltaTime))) {
            if (physicsPos.getVelocity().y < 0) isGrounded = true;
            while (!CollisionEngine.getCollision(getHitbox(), new Vector2(0, Math.signum(physicsPos.getVelocity().y) / 2f))) {
                setHitboxPos(new Vector2(getHitbox().getPosition().x, getHitbox().getPosition().y + Math.signum(physicsPos.getVelocity().y) / 2f));
            }
            physicsPos.setVelocity(new Vector2(physicsPos.getVelocity().x, 0));
        }
        setHitboxPos(getHitbox().getPosition().add(0, physicsPos.getVelocity().y * deltaTime));
        physicsPos.setPosition(getHitbox().getPosition());
        // TODO change
        CollisionEngine.regenerateSpatialHash();
    }
}
