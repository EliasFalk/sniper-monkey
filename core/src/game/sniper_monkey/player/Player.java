package game.sniper_monkey.player;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.PhysicsPosition;
import game.sniper_monkey.collision.CollisionEngine;
import game.sniper_monkey.player.fighter.Fighter;
import game.sniper_monkey.world.GameObject;
import game.sniper_monkey.world.Timer;

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
    private Timer blockTimer = new Timer(5);

    private static final Stamina playerStamina = new Stamina(0, MAX_STAMINA);

    private static final Health playerHealth = new Health(MAX_HEALTH);

    private Fighter activeFighter;
    private final Fighter primaryFighter;
    private final Fighter secondaryFighter;

    State currentState = this::groundedState;
    PhysicsPosition position = new PhysicsPosition(new Vector2(0, 0));
    private final Map<PlayerInputAction, Boolean> inputActions = new HashMap<>();

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
        if(inputActions.isEmpty()) {
            initInputActions();
        } else {
            for (Map.Entry<PlayerInputAction, Boolean> inputAction : inputActions.entrySet()) {
                inputAction.setValue(false);
            }
        }
    }

    /**
     * Updates the inputActions map by setting the action to true, which translates to "player try to do this action".
     * @param action The input action to be set.
     */
    public void setInputAction(PlayerInputAction action) {
        inputActions.replace(action, true);
    }

    private void inAirState() {
        regenerateBlockFactor(0.0005);
        if(usedAbility()) {
            return;
        }
        handleHorizontalMovement();
    }

    private boolean usedAbility() {
        if(inputActions.get(PlayerInputAction.ATTACK1)) {
            activeFighter.performAttack(1);
            playerStamina.decrease(activeFighter.getStaminaDecrease(1));
            currentState = this::attackingState;
            return true;
        } else if(inputActions.get(PlayerInputAction.ATTACK2)) {
            activeFighter.performAttack(2);
            playerStamina.decrease(activeFighter.getStaminaDecrease(2));
            currentState = this::attackingState;
            return true;
        } else if(inputActions.get(PlayerInputAction.BLOCK)) {
            blockTimer.start();
            currentState = this::blockingState;
            return true;
        }
        return false;
    }

    // TODO change name
    private void setAvatarState() {
        if (isGrounded) {
            currentState = this::groundedState;
        } else {
            currentState = this::inAirState;
        }
    }

    private void moveLeft() {
        Vector2 newVel = position.getVelocity().add(new Vector2(-VEL_GAIN, 0));
        if (newVel.x <= -MAX_X_VEL) {
            newVel = new Vector2(-MAX_X_VEL, position.getVelocity().y);
        }
        position.setVelocity(newVel);
    }

    private void moveRight() {
        Vector2 newVel = position.getVelocity().add(new Vector2(VEL_GAIN, 0));
        if (newVel.x >= MAX_X_VEL) {
            newVel = new Vector2(MAX_X_VEL, position.getVelocity().y);
        }
        position.setVelocity(newVel);
    }

    private void jump() {
        Vector2 newVel = position.getVelocity().add(new Vector2(0, JUMP_GAIN));
        position.setVelocity(newVel);
    }

    private void handleHorizontalMovement() {
        if (inputActions.get(PlayerInputAction.MOVE_RIGHT)) {
            moveRight();
            setAvatarState();
        }
        if (inputActions.get(PlayerInputAction.MOVE_LEFT)) {
            moveLeft();
            setAvatarState();
        }
    }

    private void groundedState() {
        regenerateBlockFactor(0.0007);
        if (usedAbility()) {
            return;
        }
        handleHorizontalMovement();

        if (inputActions.get(PlayerInputAction.JUMP)) {
            jump();
            currentState = this::inAirState;
        }
    }

    private void swapFighters() {
        if (primaryFighter.equals(activeFighter)) {
            initActiveFighter(secondaryFighter);
        } else {
            initActiveFighter(primaryFighter);
        }
    }

    /**
     * Checks if the player is blocking.
     * If it is, slowly decrease the blockDefenseFactor.
     * If it isn't, reset the blockDefenseFactor and set the next state.
     */
    private void blockingState() {
        if (!inputActions.get(PlayerInputAction.BLOCK)) {
            setAvatarState();
        } else {
            if (blockTimer.isDone()) {
                blockDefenseFactor = 0.7f;
            } else {
                increaseBlock(0.0007);
            }
        }
    }

    private void increaseBlock (double increaseAmount) {
        if (blockDefenseFactor + increaseAmount > 1) {
            blockDefenseFactor = 1;
        } else {
            blockDefenseFactor += increaseAmount;
        }
    }

    private void regenerateBlockFactor(double decreaseAmount) {
        if (blockDefenseFactor - decreaseAmount < 0.4) {
            blockDefenseFactor = 0.4f;
        } else {
            blockDefenseFactor -= decreaseAmount;
        }
    }

    private void attackingState() {
        // TODO create attacking state
    }


    public void takeDamage(float damageAmount) {
        if (false/*currentState == blockingState*/) { // change when statechecking has been implemented
            playerHealth.onDamage(damageAmount * (1 - activeFighter.DEFENSE_FACTOR) * (1 - blockDefenseFactor));
        } else {
            playerHealth.onDamage(damageAmount*(1-activeFighter.DEFENSE_FACTOR)); // TODO make getter for defense factor instead?
        }
    }

    public boolean isDead() {
        return playerHealth.isDead();
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
        setHitboxPos(position.getPosition());
        setHitboxSize(fighter.getHitboxSize());
        playerStamina.setRegenerationFactor(10f * activeFighter.SPEED_FACTOR);
    }

    /**
     * Creates a player with a position in the world
     *
     * @param position The initial position of the player.
     */
    public Player(Vector2 position, Fighter primaryFighter, Fighter secondaryFighter) {
        super(position);
        this.primaryFighter = primaryFighter;
        this.secondaryFighter = secondaryFighter;

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

    /**
     * Updates the class player every frame
     *
     * @param deltaTime The time between frames.
     */
    @Override
    public void update(float deltaTime) {
        playerStamina.update(deltaTime);
        currentState.performState();
        resetInputActions();
        updatePlayerPos(deltaTime);
    }

    private void updatePlayerPos(float deltaTime) {
        position.update(deltaTime);
        handleCollision(deltaTime);
        setHitboxPos(position.getPosition());
        super.setPos(position.getPosition());
    }

    // TODO refactor this behemoth
    private void handleCollision(float deltaTime) {
        // executes shawn mendez. inspired by shawn's collision algorithm
        boolean collidesXAxisNextFrame = CollisionEngine.getCollision(getHitbox(), new Vector2(position.getVelocity().x, 0).scl(deltaTime));
        if (collidesXAxisNextFrame) {
            // while it doesn't collide with an x position approaching the object it will collide with, then see if it collides with an x position a tiny bit closer until it collides.
            while (!CollisionEngine.getCollision(getHitbox(), new Vector2(Math.signum(position.getVelocity().x) / 2f, 0))) {
                setHitboxPos(new Vector2(getHitbox().getPosition().x + Math.signum(position.getVelocity().x) / 2f, getHitbox().getPosition().y));
            }
            // Then set x velocity to zero, and the x position is already set to the closest it can get to the object it collides with.
            position.setVelocity(new Vector2(0, position.getVelocity().y));
        }

        setHitboxPos(getHitbox().getPosition().add(position.getVelocity().x * deltaTime, 0));

        isGrounded = false;
        if (CollisionEngine.getCollision(getHitbox(), new Vector2(0, position.getVelocity().y).scl(deltaTime))) {
            if(position.getVelocity().y < 0) isGrounded = true;
            while (!CollisionEngine.getCollision(getHitbox(), new Vector2(0, Math.signum(position.getVelocity().y) / 2f))) {
                setHitboxPos(new Vector2(getHitbox().getPosition().x, getHitbox().getPosition().y + Math.signum(position.getVelocity().y) / 2f));
            }
            setAvatarState();
            position.setVelocity(new Vector2(position.getVelocity().x, 0));
        }
        setHitboxPos(getHitbox().getPosition().add(0, position.getVelocity().y * deltaTime));
        position.setPosition(getHitbox().getPosition());
    }
}
