package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.PhysicsPosition;
import game.sniper_monkey.model.world.CallbackTimer;
import game.sniper_monkey.model.world.GameObject;

public class Projectile extends GameObject {

    private final float damage;
    private final float timeToLive;
    public final Vector2 size;
    private CallbackTimer timeToLiveTimer;
    private PhysicsPosition physicsPosition;

    public Projectile(float damage, float timeToLive, Vector2 playerPos, Vector2 attackSize, int collisionMask, Vector2 velocity) {
        super(playerPos, true);
        timeToLiveTimer = new CallbackTimer(timeToLive, this::delete);
        timeToLiveTimer.reset();
        timeToLiveTimer.start();
        setHitboxMask(collisionMask);
        this.size = attackSize;
        this.damage = damage;
        this.timeToLive = timeToLive;
        physicsPosition = new PhysicsPosition(playerPos);
        physicsPosition.setVelocity(velocity);
    }

    public Projectile(float damage, float timeToLive, Vector2 playerPos, Vector2 attackSize, int collisionMask) {
        super(playerPos, true);
        timeToLiveTimer = new CallbackTimer(timeToLive, this::delete);
        timeToLiveTimer.reset();
        timeToLiveTimer.start();
        setHitboxMask(collisionMask);
        this.size = attackSize;
        this.damage = damage;
        this.timeToLive = timeToLive;
    }


    @Override
    public void update(float deltaTime) {
//        CollisionEngine.getCollidingObjects(); // Prepare for checking collisions and doing takeDmg on player
    }
}
