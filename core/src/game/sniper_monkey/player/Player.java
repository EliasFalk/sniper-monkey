package game.sniper_monkey.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.sun.tools.jconsole.JConsoleContext;
import game.sniper_monkey.PhysicsPosition;
import game.sniper_monkey.world.GameObject;
import game.sniper_monkey.world.Timer;

import java.lang.constant.Constable;
import java.util.HashMap;
import java.util.Map;

public class Player extends GameObject {
    interface State {
        void performState();
    }

    private float blockDefenseFactor;

    private Timer timer = new Timer(5);

    State currentState = this::groundedState;
    PhysicsPosition position = new PhysicsPosition(new Vector2(0,0));
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

    private void handleHorizontalMovement() {
        if(inputActions.get(PlayerInputAction.MOVE_RIGHT)) {
            // TODO update physics position on player right
            setAvatarState();
        } else if(inputActions.get(PlayerInputAction.MOVE_LEFT)) {
            // TODO update physics position on player left
            setAvatarState();
        }
    }

    private void groundedState() {
        if(usedAbility()) {
            return;
        }
        handleHorizontalMovement();

        if(inputActions.get(PlayerInputAction.JUMP)) {
            position.setVelocity(position.getVelocity().add(new Vector2(0, 50)));
            currentState = this::inAirState;
        }
    }

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
     * Creates a player with a position in the world
     * @param position The initial position of the player.
     */
    public Player(Vector2 position) {
        super(position);
        resetInputActions();
        this.position.setVelocity(this.position.getVelocity().add(new Vector2(-0,0)));
        blockDefenseFactor = 0;
    }

    /**
     * Creates a Player object
     */
    public Player() {
        resetInputActions();
        this.position.setVelocity(this.position.getVelocity().add(new Vector2(-500000,0)));
        blockDefenseFactor = 0;
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
        if(Gdx.input.isKeyPressed(Input.Keys.C)) {
            setInputAction(PlayerInputAction.BLOCK);
        }
    }
}
