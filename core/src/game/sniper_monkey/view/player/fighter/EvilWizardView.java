package game.sniper_monkey.view.player.fighter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.player.fighter.EvilWizard;
import game.sniper_monkey.view.GameObjectView;
import game.sniper_monkey.player.Player;
import game.sniper_monkey.world.GameObject;

public class EvilWizardView extends GameObjectView {
    Player model;
    public EvilWizardView(Player model) {
        super(new Vector2(-106, -83), new Sprite(new Texture("evil_wizard_2/Idle.png"), 0, 0, 250, 250), model);
        this.model = model;
        //TODO
    }
}
