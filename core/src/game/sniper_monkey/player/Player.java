package game.sniper_monkey.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.PhysicsPosition;
import game.sniper_monkey.collision.Hitbox;
import game.sniper_monkey.player.fighter.Fighter;
import game.sniper_monkey.world.GameObject;
import game.sniper_monkey.world.Timer;

import java.util.HashMap;
import java.util.Map;

public class Player extends GameObject {
    interface State {
        void performState();
    }

    private float blockDefenseFactor;

    private Timer timer = new Timer(5);

    // TODO read these values from config file
    private final float MAX_X_VEL = 300f;
    private final float VEL_GAIN = 40f;
    private final float JUMP_GAIN = 200f;


    private Fighter activeFighter;
    private final Fighter primaryFighter;
    private final Fighter secondaryFighter;

    State currentState = this::groundedState;
    PhysicsPosition position = new PhysicsPosition(new Vector2(0, 0));
    private final Map<PlayerInputAction, Boolean> inputActions = new HashMap<PlayerInputAction, Boolean>();

    private void initInputActions() {
        inputActions.put(PlayerInputAction.JUMP, false);
        inputActions.put(PlayerInputAction.ATTACK1, false);
        inputActions.put(PlayerInputAction.ATTACK2, false);
        inputActions.put(PlayerInputAction.BLOCK, false);
        inputActions.put(PlayerInputAction.MOVE_RIGHT, false);
        inputActions.put(PlayerInputAction.MOVE_LEFT, false);
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
        if(usedAbility()) {
            return;
        }
        handleHorizontalMovement();
    }

    private boolean usedAbility() {
        if(inputActions.get(PlayerInputAction.ATTACK1)) {
            // TODO activeFighter.performState(...);
            currentState = this::attackingState;
            return true;
        } else if(inputActions.get(PlayerInputAction.ATTACK2)) {
            // TODO activeFighter.performState(...);
            currentState = this::attackingState;
            return true;
        } else if(inputActions.get(PlayerInputAction.BLOCK)) {
            blockDefenseFactor = 0.6f;
            timer.start();
            currentState = this::blockingState;
            return true;
        }
        return false;
    }

    private boolean isGrounded() {
        //TODO check if player collides with platform
        return true;
    }

    // TODO change name
    private void setAvatarState() {
        if(isGrounded()) {
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
        } else if (inputActions.get(PlayerInputAction.MOVE_LEFT)) {
            moveLeft();
            setAvatarState();
        }
    }

    private void groundedState() {
        if(usedAbility()) {
            return;
        }
        handleHorizontalMovement();

        if (inputActions.get(PlayerInputAction.JUMP)) {
            jump();
            currentState = this::inAirState;
        }
    }

    /**
     * Checks if the player is blocking.
     * If it is, slowly decrease the blockDefenseFactor.
     * If it isn't, reset the blockDefenseFactor and set the next state.
     */
    private void blockingState() {
        if(!inputActions.get(PlayerInputAction.BLOCK)) {
            blockDefenseFactor = 0;
            setAvatarState();
        } else {
            if (timer.isDone()) {
                blockDefenseFactor = 0.3f;
            } else {
                blockDefenseFactor -= 0.0007;
            }
        }
    }

    private void attackingState() {
        // TODO create attacking state
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
        setHitbox(new Hitbox(position.getPosition(), fighter.getHitboxSize()));
        // TODO Set stamina regen
        // maybe more??
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
        blockDefenseFactor = 0;
    }

    /**
     * Creates a Player object
     */
    public Player(Fighter primaryFighter, Fighter secondaryFighter) {
        this(new Vector2(0, 0), primaryFighter, secondaryFighter);
    }

    /**
     * Updates the class player every frame
     * @param deltaTime The time between frames.
     */
    @Override
    public void update(float deltaTime) {
        currentState.performState();
        resetInputActions();
        position.update(deltaTime);
        setPos(position.getPosition());

    }
}
