package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.PhysicsPosition;
import game.sniper_monkey.model.collision.CollisionEngine;
import game.sniper_monkey.model.world.CallbackTimer;
import game.sniper_monkey.model.world.GameObject;

public class Projectile extends GameObject {

    private final float damage;
    private final float timeToLive;
    public final Vector2 size;
    private CallbackTimer timeToLiveTimer;
    private PhysicsPosition physicsPosition;
    private Vector2 velocity;

    public Projectile(float damage, float timeToLive, Vector2 playerPos, Vector2 attackSize, int collisionMask, Vector2 velocity) {
        super(playerPos, true);
        timeToLiveTimer = new CallbackTimer(timeToLive, this::delete);
        timeToLiveTimer.reset();
        timeToLiveTimer.start();
        setHitboxMask(collisionMask);
        this.size = attackSize;
        this.damage = damage;
        this.timeToLive = timeToLive;
        this.velocity = velocity;
        physicsPosition = new PhysicsPosition(playerPos);
        physicsPosition.setVelocity(velocity);
    }

    // TODO make dis melee
    public Projectile(float damage, float timeToLive, Vector2 playerPos, Vector2 attackSize, int collisionMask) {
        super(playerPos, false);
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
        physicsPosition.update(deltaTime);
        System.out.println(CollisionEngine.getCollidingObjects(this.getHitbox(), velocity.scl(deltaTime), getHitboxMask()).toString()); // Prepare for checking collisions and doing takeDmg on player

        if (CollisionEngine.getCollision(this.getHitbox(), velocity.scl(deltaTime), getHitboxMask())) {
            System.out.println("hitted lmao");
        }
    }
}
