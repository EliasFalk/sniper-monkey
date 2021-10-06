package game.sniper_monkey.view.player.fighter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.FighterAnimation;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.utils.time.Time;
import game.sniper_monkey.utils.view.AnimationUtils;
import game.sniper_monkey.utils.view.SpriteUtils;
import game.sniper_monkey.view.GameObjectView;

import java.util.HashMap;
import java.util.Map;

/**
 * A view for the evil wizard with animation support.
 *
 * @author Vincent Hellner
 * @author Elias Falk
 * @author Kevin Jeryd
 */
public class EvilWizardView extends GameObjectView {
    private static final Vector2 drawOffset = new Vector2(-106, -83);
    private final Player model;
    private final float frameDuration = 0.1f;

    private final Map<FighterAnimation, Animation<Sprite>> animations = new HashMap<>();

    /**
     * Creates an EvilWizardView
     * @param model The Player
     */
    public EvilWizardView(Player model) {
        super(drawOffset, SpriteUtils.getDefaultSprite(), model);
        this.model = model;
        //TODO: good.
        initAnimationHash();
    }

    private void initAnimationHash() {
        // TODO add remaining animations
        Texture idle = new Texture("images/evil_wizard_2/Idle.png");
        Texture run = new Texture("images/evil_wizard_2/Run.png");
        Texture jump = new Texture("images/evil_wizard_2/Jump.png");
        Texture fall = new Texture("images/evil_wizard_2/Fall.png");
        Texture death = new Texture("images/evil_wizard_2/Death.png");
        animations.clear();
        animations.put(FighterAnimation.IDLING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(idle, 8), Animation.PlayMode.LOOP));
        animations.put(FighterAnimation.MOVING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(run, 8), Animation.PlayMode.LOOP));
        animations.put(FighterAnimation.JUMPING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(jump, 2), Animation.PlayMode.LOOP));
        animations.put(FighterAnimation.FALLING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(fall, 2), Animation.PlayMode.LOOP));
        animations.put(FighterAnimation.DYING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(death, 7), Animation.PlayMode.LOOP_PINGPONG));
    }

    @Override
    public void updateSprite() {
        sprite = animations.get(model.getCurrentFighterAnimation()).getKeyFrame(Time.getElapsedTime());
        sprite.setFlip(!model.isLookingRight(), false);
    }
}