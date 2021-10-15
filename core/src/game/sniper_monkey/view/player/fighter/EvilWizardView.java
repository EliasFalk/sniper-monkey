package game.sniper_monkey.view.player.fighter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.PhysicalState;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.ReadablePlayer;
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
    private final ReadablePlayer model;
    private final float frameDuration = 0.1f;

    private PhysicalState lastAnimation;

    private final Map<PhysicalState, Animation<Sprite>> animations = new HashMap<>();

    /**
     * Creates an EvilWizardView
     * @param model The Player
     */
    public EvilWizardView(Player model) {
        super(drawOffset, SpriteUtils.getDefaultSprite(), model);
        this.model = model;
        //TODO: good.
        initAnimationHash();
        lastAnimation = PhysicalState.IDLING;
    }

    private void initAnimationHash() {
        // TODO add remaining animations
        Texture idle = new Texture("images/evil_wizard_2/Idle.png");
        Texture run = new Texture("images/evil_wizard_2/Run.png");
        Texture jump = new Texture("images/evil_wizard_2/Jump.png");
        Texture fall = new Texture("images/evil_wizard_2/Fall.png");
        Texture death = new Texture("images/evil_wizard_2/Death.png");
        Texture attack1 = new Texture("images/evil_wizard_2/Attack1.png");
        animations.clear();
        animations.put(PhysicalState.IDLING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(idle, 8), Animation.PlayMode.LOOP));
        animations.put(PhysicalState.MOVING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(run, 8), Animation.PlayMode.LOOP));
        animations.put(PhysicalState.JUMPING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(jump, 2), Animation.PlayMode.LOOP));
        animations.put(PhysicalState.FALLING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(fall, 2), Animation.PlayMode.LOOP));
        animations.put(PhysicalState.DYING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(death, 7), Animation.PlayMode.LOOP_PINGPONG));
        animations.put(PhysicalState.ATTACKING1, new Animation<>(model.getAttack1Length()/8, AnimationUtils.cutSpriteSheet(attack1, 8), Animation.PlayMode.NORMAL));
    }

    //TODO documentation
    @Override
    public void updateSprite() {
        if(model.getCurrentPhysicalState() != lastAnimation) {
            Time.resetElapsedTime();
            lastAnimation = model.getCurrentPhysicalState();
        }
        sprite = animations.get(model.getCurrentPhysicalState()).getKeyFrame(Time.getElapsedTime());
        sprite.setFlip(!model.isLookingRight(), false);
    }
}
