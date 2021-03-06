package game.sniper_monkey.view.player.fighter.attack_object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.fighter.attack.attack_object.Shuriken;
import game.sniper_monkey.view.GameObjectView;

/**
 * View for a shuriken
 *
 * @author Dadi Andrason
 */
public class ShurikenView extends GameObjectView {

    Shuriken model;

    /**
     * Create a shuriken view
     * @param model The Shuriken to use as model
     */
    public ShurikenView(Shuriken model) {
        super(new Vector2(0,0), new Sprite(new Texture("images/samurai/shuriken.png"), 0,0,16,14), model);
        this.model = model;

    }

    @Override
    public void updateSprite() {
        sprite.setPosition(model.getHitbox().getPosition().x, model.getHitbox().getPosition().y);
    }

}
