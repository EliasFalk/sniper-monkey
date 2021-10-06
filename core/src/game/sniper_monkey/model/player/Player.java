package game.sniper_monkey.model.player;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.Callback;
import game.sniper_monkey.model.Config;
import game.sniper_monkey.model.collision.CollisionEngine;
import game.sniper_monkey.model.collision.Hitbox;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.model.world.GameObject;
import game.sniper_monkey.physics.PhysicsAttribute;
import game.sniper_monkey.physics.PhysicsEngine;

import java.util.HashMap;
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
    private final Fighter primaryFighter;
    private final Fighter secondaryFighter;
    private final Map<PlayerInputAction, Boolean> inputActions = new HashMap<>();
    private final float blockDefenseFactor;
    private PhysicsAttribute phsssss = new PhysicsAttribute(new Vector2(0, 0), new Vector2(0, 0), new Vector2(0, 0), 100f, 0);
    boolean isGrounded = true;
    private Fighter activeFighter;
    private FighterAnimation currentFighterAnimation; // TODO set this to static for very many fun
    private boolean lookingRight;
    private State movementState = this::groundedState;
    private State abilityState = this::inactiveState;


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
    public Player(Vector2 position, Fighter primaryFighter, Fighter secondaryFighter) {
        super(position, true);
        phsssss.setPosition(position);
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
        } else {
            blockFactor.setDraining(false);
            blockFactor.setRegenerating(true, BASE_BLOCK_REGEN);
            abilityState = this::inactiveState;
            movementState.performState();
        }
    }

    private void attacking1State() {

    }

    private void attacking2State() {

    }

    private void swappingFighterState() {
        // TODO swap fighter

        // if swapped fighter go to inactive state. Swapping fighter could take some time?
        if (true) {
            abilityState = this::inactiveState;
        }
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

        if (inputActions.get(PlayerInputAction.DROP)) {
            phsssss.addAcceleration(-1000, 0);
        }

        handleHorizontalMovement();
        if (phsssss.getVelocity().y < 0) {
            currentFighterAnimation = FighterAnimation.FALLING;
        } else if (phsssss.getVelocity().y > 0) {
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
        if (phsssss.getVelocity().y < 0) {
            movementState = this::inAirState;
            return;
        }
    }

    private void moveLeft() {
        phsssss.addAcceleration(-300, 0);
    }

    private void moveRight() {
        phsssss.addAcceleration(300, 0);
    }

    private void jump() {
        phsssss.addAcceleration(0,(float) Math.pow(10,4)*3);
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

    private void initActiveFighter(Fighter fighter) {
        activeFighter = fighter;
        setHitboxPos(phsssss.getPosition());
        setHitboxSize(fighter.getHitboxSize());
        stamina.setRegenerationAmount(BASE_STAMINA_REGEN * activeFighter.SPEED_FACTOR);
    }

    private void handleLookingDirection() {
        if (phsssss.getVelocity().x > 0) {
            lookingRight = true;
        } else if (phsssss.getVelocity().x < 0) {
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

        phsssss = PhysicsEngine.getNextState(phsssss, deltaTime);
        System.out.println(phsssss.getVelocity());
//        handleCollision(deltaTime);
        // float deltaTime, Hitbox hitbox, Vector2 velocity, Callback onXCollision, Callback onYCollision
        isGrounded = false;
        phsssss.setFrictionCoefficient(0);
        PhysicsAttribute collidedPosition = handleCollision(deltaTime, getHitbox(), phsssss.getVelocity(), () -> {System.out.println("Collided X");}, () -> {
            System.out.println("Collided Y");
            phsssss.setFrictionCoefficient(200f);
            isGrounded = true;});
        phsssss = collidedPosition;
        super.setPosition(phsssss.getPosition());
    }

    // TODO refactor this behemoth
    private void handleCollision(float deltaTime) {

        // executes shawn mendez. inspired by shawn's collision algorithm
        boolean collidesXAxisNextFrame = CollisionEngine.getCollision(getHitbox(), new Vector2(phsssss.getVelocity().x, 0).scl(deltaTime));
        if (collidesXAxisNextFrame) {
            // while it doesn't collide with an x position approaching the object it will collide with, then see if it collides with an x position a tiny bit closer until it collides.
            while (!CollisionEngine.getCollision(getHitbox(), new Vector2(Math.signum(phsssss.getVelocity().x) / 2f, 0))) {
                setHitboxPos(new Vector2(getHitbox().getPosition().x + Math.signum(phsssss.getVelocity().x) / 2f, getHitbox().getPosition().y));
            }
            // Then set x velocity to zero, and the x position is already set to the closest it can get to the object it collides with.
            phsssss.setVelocity(new Vector2(0, phsssss.getVelocity().y));
        }

        setHitboxPos(getHitbox().getPosition().add(phsssss.getVelocity().x * deltaTime, 0));

        isGrounded = false;
        if (CollisionEngine.getCollision(getHitbox(), new Vector2(0, phsssss.getVelocity().y).scl(deltaTime))) {
            if (phsssss.getVelocity().y < 0) isGrounded = true;
            while (!CollisionEngine.getCollision(getHitbox(), new Vector2(0, Math.signum(phsssss.getVelocity().y) / 2f))) {
                setHitboxPos(new Vector2(getHitbox().getPosition().x, getHitbox().getPosition().y + Math.signum(phsssss.getVelocity().y) / 2f));
            }
            phsssss.setVelocity(new Vector2(phsssss.getVelocity().x, 0));
        }
        setHitboxPos(getHitbox().getPosition().add(0, phsssss.getVelocity().y * deltaTime));
        phsssss.setPosition(getHitbox().getPosition());
    }

    private PhysicsAttribute handleCollision(float deltaTime, Hitbox hitbox, Vector2 velocity, Callback onXCollision, Callback onYCollision) {
        Vector2 vel = velocity.cpy();

        boolean collidesXAxisNextFrame = CollisionEngine.getCollision(hitbox, new Vector2(vel.x, 0).scl(deltaTime));
        if (collidesXAxisNextFrame) {
            while (!CollisionEngine.getCollision(hitbox, new Vector2(Math.signum(vel.x) / 2f, 0))) {
                hitbox.setPosition(new Vector2(hitbox.getPosition().x + Math.signum(vel.x) / 2f, hitbox.getPosition().y));
            }
            vel.x = 0;
            onXCollision.call();
        }

        hitbox.setPosition(hitbox.getPosition().add(vel.x * deltaTime, 0));

        if (CollisionEngine.getCollision(hitbox, new Vector2(0, vel.y).scl(deltaTime))) {
            while (!CollisionEngine.getCollision(hitbox, new Vector2(0, Math.signum(vel.y) / 2f))) {
                hitbox.setPosition(new Vector2(hitbox.getPosition().x, hitbox.getPosition().y + Math.signum(vel.y) / 2f));
            }
            vel.y = 0;
            onYCollision.call();
        }
        hitbox.setPosition(hitbox.getPosition().add(0, vel.y * deltaTime));
        return new PhysicsAttribute(hitbox.getPosition(), vel, phsssss.getAcceleration(), phsssss.getMass(), phsssss.getFrictionCoefficient());
    }

    @FunctionalInterface
    private interface State {
        void performState();
    }
}
