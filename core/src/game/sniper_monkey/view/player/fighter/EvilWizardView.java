package game.sniper_monkey.view.player.fighter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.player.FighterAnimation;
import game.sniper_monkey.player.Player;
import game.sniper_monkey.utils.time.Time;
import game.sniper_monkey.utils.view.AnimationUtils;
import game.sniper_monkey.utils.view.SpriteUtils;
import game.sniper_monkey.view.GameObjectView;

import java.util.HashMap;
import java.util.Map;

public class EvilWizardView extends GameObjectView {
    private static final Vector2 drawOffset = new Vector2(-106, -83);
    private final Player model;
    private final float frameDuration = 0.1f;

    private final Map<FighterAnimation, Animation<Sprite>> animations = new HashMap<>();

    private void initAnimationHash() {
        // TODO add remaining animations
        Texture idle = new Texture("images/evil_wizard_2/Idle.png");
        Texture run = new Texture("images/evil_wizard_2/Run.png");
        Texture jump = new Texture("images/evil_wizard_2/Jump.png");
        Texture fall = new Texture("images/evil_wizard_2/Fall.png");
        animations.clear();
        animations.put(FighterAnimation.IDLING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(idle, 8), Animation.PlayMode.LOOP));
        animations.put(FighterAnimation.MOVING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(run, 8), Animation.PlayMode.LOOP));
        animations.put(FighterAnimation.JUMPING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(jump, 2), Animation.PlayMode.LOOP));
        animations.put(FighterAnimation.FALLING, new Animation<>(frameDuration, AnimationUtils.cutSpriteSheet(fall, 2), Animation.PlayMode.LOOP));
    }


    public EvilWizardView(Player model) {
        super(drawOffset, SpriteUtils.getDefaultSprite(), model);
        this.model = model;
        //TODO: good.
        initAnimationHash();
    }

    @Override
    public void updateSprite() {
        sprite = animations.get(model.getCurrentFighterAnimation()).getKeyFrame(Time.getElapsedTime());
        sprite.setFlip(!model.isLookingRight(), false);

    }
}
