package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Null;
import game.sniper_monkey.model.PhysicsPosition;
import game.sniper_monkey.model.collision.CollisionEngine;
import game.sniper_monkey.model.world.CallbackTimer;
import game.sniper_monkey.model.world.GameObject;
import game.sniper_monkey.view.GameObjectViewFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AttackObject extends GameObject {

    private final float damage;
    private final float timeToLive;
    private CallbackTimer timeToLiveTimer;
    private Vector2 velocity;

    private final Map<Class<?>, Consumer<GameObject>> objectCollidedDispatch;


    public AttackObject(float damage, float timeToLive, Vector2 spawnPos, int collisionMask, Vector2 velocity) {
        super(spawnPos, true);
        timeToLiveTimer = new CallbackTimer(timeToLive, this::delete);
        timeToLiveTimer.reset();
        timeToLiveTimer.start();
        setHitboxMask(collisionMask);

        this.damage = damage;
        this.timeToLive = timeToLive;
        this.velocity = velocity;

        objectCollidedDispatch = new HashMap<>();
    }

    // TODO make dis melee
    public AttackObject(float damage, float timeToLive, Vector2 spawnPos, int collisionMask) {
        this(damage, timeToLive, spawnPos, collisionMask, new Vector2(0,0));
    }


    private void checkCollision() {
        List<GameObject> collidedObjects = CollisionEngine.getCollidingObjects(this.getHitbox(), new Vector2(0,0),getHitboxMask());
        for (GameObject gameObject : collidedObjects) {
            try {
                objectCollidedDispatch.get(gameObject.getClass()).accept(gameObject);
            } catch (NullPointerException ignored) {}
        }
    }

    protected void addHitResponse(Class<?> gameObjectType, Consumer<GameObject> response) {
        objectCollidedDispatch.put(gameObjectType, response);
    }

    @Override
    public void update(float deltaTime) {
        checkCollision();
    }
}
