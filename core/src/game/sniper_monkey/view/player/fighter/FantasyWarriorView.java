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

/**
 * View for a FantasyWarrior
 *
 * @author Dadi Andrason
 */
public class FantasyWarriorView extends GameObjectView {

    private static final Vector2 drawOffset = new Vector2(-78, -72);
    private final ReadablePlayer model;
    private final float frameDuration = 0.1f;
    private final Map<PhysicalState, Animation<Sprite>> animations = new HashMap<>();
    private PhysicalState lastAnimation;
    private long animationStartTime;


    /**
     * Creates a samurai view
     * @param model The player to use as model
     */
    public FantasyWarriorView(Player model) {
        super(drawOffset, SpriteUtils.getDefaultSprite(), model);
        this.model = model;
        initAnimationHash();
        lastAnimation = PhysicalState.IDLING;
    }

    private void initAnimationHash() {
        Texture idle = new Texture("images/fantasyWarrior/Idle.png");
        Texture run = new Texture("images/fantasyWarrior/Run.png");
        Texture jump = new Texture("images/fantasyWarrior/Jump.png");
        Texture fall = new Texture("images/fantasyWarrior/Fall.png");
        Texture death = new Texture("images/fantasyWarrior/Death.png");
        Texture attack1 = new Texture("images/fantasyWarrior/Attack1.png");
        Texture attack2 = new Texture("images/fantasyWarrior/Attack3.png");
        animations.clear();
        animations.put(PhysicalState.IDLING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(idle, 10), Animation.PlayMode.LOOP));
        animations.put(PhysicalState.MOVING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(run, 8), Animation.PlayMode.LOOP));
        animations.put(PhysicalState.JUMPING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(jump, 3), Animation.PlayMode.LOOP));
        animations.put(PhysicalState.FALLING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(fall, 3), Animation.PlayMode.LOOP));
        animations.put(PhysicalState.BLOCKING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(death, 7), Animation.PlayMode.LOOP_PINGPONG));
        animations.put(PhysicalState.DYING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(death, 7), Animation.PlayMode.NORMAL));
        animations.put(PhysicalState.ATTACKING1, new Animation<>(model.getAttackLength(0) / 7, AnimationUtils.cutSpriteSheet(attack1, 7), Animation.PlayMode.NORMAL));
        animations.put(PhysicalState.ATTACKING2, new Animation<>(model.getAttackLength(1) / 8, AnimationUtils.cutSpriteSheet(attack2, 8), Animation.PlayMode.NORMAL));
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
