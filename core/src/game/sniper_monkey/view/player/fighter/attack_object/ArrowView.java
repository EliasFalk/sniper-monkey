package game.sniper_monkey.view.player.fighter.attack_object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.fighter.attack.attack_object.Arrow;
import game.sniper_monkey.view.GameObjectView;

/**
 * View for an arrow
 *
 * @author Dadi Andrason
 * @author Kevin Jeryd
 */
public class ArrowView extends GameObjectView {
    Arrow model;

    /**
     * Creates an arrow view
     * @param model The arrow to use as model
     */
    public ArrowView(Arrow model) {
        super(new Vector2(0, 0), new Sprite(new Texture("images/huntress_2/arrow_fired.png"), 0, 0, 35, 7), model);
        this.model = model;
    }

    @Override
    public void updateSprite() {

        sprite.setFlip(!model.isLookingRight(), false);
        sprite.setPosition(model.getHitbox().getPosition().x, model.getHitbox().getPosition().y);
    }


}
