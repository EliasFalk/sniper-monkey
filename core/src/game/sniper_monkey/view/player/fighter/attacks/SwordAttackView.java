package game.sniper_monkey.view.player.fighter.attacks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.fighter.attack.Projectile;
import game.sniper_monkey.model.player.fighter.attack.SwordAttack;
import game.sniper_monkey.model.world.GameObject;
import game.sniper_monkey.view.GameObjectView;

public class SwordAttackView extends GameObjectView {


    /**
     * Creates a GameObjectView
     *
     * @param drawOffset An offset to draw the sprite at.
     * @param sprite     The sprite to draw.
     * @param model      The GameObject used as the model.
     */
    public SwordAttackView(Projectile model) {
        super(new Vector2(0,0), new Sprite(new Texture("images/CuteForest/Tileset.png"), 16, 0, 16, 16), model);
    }
}
