package game.sniper_monkey.view.player.fighter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.PhysicalState;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.ReadablePlayer;
import game.sniper_monkey.utils.view.AnimationUtils;
import game.sniper_monkey.utils.view.SpriteUtils;
import game.sniper_monkey.view.GameObjectView;

import java.util.HashMap;
import java.util.Map;

// TODO docs
// TODO fix sprite & hitbox
public class HuntressView extends GameObjectView {

    private static final Vector2 drawOffset = new Vector2(-52, -48);
    private final ReadablePlayer model;
    private final float frameDuration = 0.1f;
    private long animationStartTime;
    private PhysicalState lastAnimation;

    private final Map<PhysicalState, Animation<Sprite>> animations = new HashMap<>();

    /**
     * Creates an HuntressView
     *
     * @param model The Player
     */
    public HuntressView(Player model) {
        super(drawOffset, SpriteUtils.getDefaultSprite(), model);
        this.model = model;
        //TODO: good.
        initAnimationHash();
        lastAnimation = PhysicalState.IDLING;
    }

    private void initAnimationHash() {
        Texture idle = new Texture("images/huntress_2/idle.png");
        Texture run = new Texture("images/huntress_2/run.png");
        Texture jump = new Texture("images/huntress_2/jump.png");
        Texture fall = new Texture("images/huntress_2/falling.png");
        Texture death = new Texture("images/huntress_2/death.png");
        Texture attack1 = new Texture("images/huntress_2/attack.png");
        Texture attack2 = new Texture("images/huntress_2/attack.png");
        animations.clear();
        animations.put(PhysicalState.IDLING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(idle, 10), Animation.PlayMode.LOOP_PINGPONG));
        animations.put(PhysicalState.MOVING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(run, 8), Animation.PlayMode.LOOP));
        animations.put(PhysicalState.JUMPING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(jump, 2), Animation.PlayMode.LOOP));
        animations.put(PhysicalState.FALLING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(fall, 2), Animation.PlayMode.LOOP));
        animations.put(PhysicalState.BLOCKING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(death, 10), Animation.PlayMode.LOOP_PINGPONG)); // TODO CHANGE
        animations.put(PhysicalState.DYING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(death, 10), Animation.PlayMode.NORMAL));
        animations.put(PhysicalState.ATTACKING1, new Animation<>(model.getAttackLength(0) / 6, AnimationUtils.cutSpriteSheet(attack1, 6), Animation.PlayMode.NORMAL));
        animations.put(PhysicalState.ATTACKING2, new Animation<>(model.getAttackLength(1) / 6, AnimationUtils.cutSpriteSheet(attack2, 6), Animation.PlayMode.NORMAL));
    }


    @Override
    public void updateSprite() {
        sprite = animations.get(model.getCurrentPhysicalState()).getKeyFrame(getStateTime());
        sprite.setFlip(!model.isLookingRight(), false);
    }

    // TODO move this somewhere else
    private float getStateTime() {
        if (model.getCurrentPhysicalState() != lastAnimation) {
            animationStartTime = System.currentTimeMillis();
            lastAnimation = model.getCurrentPhysicalState();
        }
        return (System.currentTimeMillis() - animationStartTime) / 1000f;
    }
}
