package game.sniper_monkey.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.world.GameObject;
import game.sniper_monkey.physics.PhysicsAttribute;
import game.sniper_monkey.physics.PhysicsEngine;

public class TestPlayer extends GameObject {
    private PhysicsAttribute physicsAttribute = new PhysicsAttribute(new Vector2(0, 0), new Vector2(0, 0), new Vector2(0, 0), 100f, 1f);

    public TestPlayer() {
        super(true);
    }

    @Override
    public void update(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            physicsAttribute.acceleration.add(new Vector2(-100, 0));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            physicsAttribute.acceleration.add(new Vector2(100, 0));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.U)) {
            physicsAttribute.acceleration.add(new Vector2(0, 100));
        }
        physicsAttribute = PhysicsEngine.getNextState(physicsAttribute, deltaTime);
        setPosition(physicsAttribute.position.cpy());
    }
}
