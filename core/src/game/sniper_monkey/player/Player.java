package game.sniper_monkey.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.PhysicsPosition;
import game.sniper_monkey.world.GameObject;

import java.util.HashMap;
import java.util.Map;

public class Player extends GameObject {
    interface State {
        void performState();
    }

    State currentState = this::groundedState;
    PhysicsPosition position = new PhysicsPosition(new Vector2(0,0));
    private final Map<PlayerInputAction, Boolean> inputActions = new HashMap<PlayerInputAction, Boolean>();

    private void initInputActions() {
        inputActions.put(PlayerInputAction.JUMP, false);
        inputActions.put(PlayerInputAction.ATTACK1, false);
        inputActions.put(PlayerInputAction.ATTACK2, false);
        inputActions.put(PlayerInputAction.BLOCK, false);
        inputActions.put(PlayerInputAction.MOVERIGHT, false);
        inputActions.put(PlayerInputAction.MOVELEFT, false);
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
            // activeFighter.performState(...);
            currentState = this::attackingState;
            return true;
        } else if(inputActions.get(PlayerInputAction.ATTACK2)) {
            // activeFighter.performState(...);
            currentState = this::attackingState;
            return true;
        } else if(inputActions.get(PlayerInputAction.BLOCK)) {
            // block;
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
        if(inputActions.get(PlayerInputAction.MOVERIGHT)) {
            // update physics position on player right
            setAvatarState();
        } else if(inputActions.get(PlayerInputAction.MOVELEFT)) {
            // update physics position on player left
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

    }

    private void attackingState() {

    }

    /**
     * Creates a player with a sprite and a position in the world
     * @param position
     * @param sprite
     */
    public Player(Vector2 position, Sprite sprite) {
        super(position, sprite);
        resetInputActions();
        this.position.setVelocity(this.position.getVelocity().add(new Vector2(-0,0)));
    }

    /**
     * Creates a Player object with a sprite
     * @param sprite
     */
    public Player(Sprite sprite) {
        super(sprite);
        resetInputActions();
        this.position.setVelocity(this.position.getVelocity().add(new Vector2(-500000,0)));
    }

    /**
     * Updates the class player every frame
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        currentState.performState();
        resetInputActions();
        position.update(deltaTime);
        setPos(position.getPosition());
    }
}
