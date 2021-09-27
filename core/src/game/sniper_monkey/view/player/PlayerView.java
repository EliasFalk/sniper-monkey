package game.sniper_monkey.view.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.view.GameObjectView;
import game.sniper_monkey.player.Player;
import game.sniper_monkey.world.GameObject;

public class PlayerView extends GameObjectView {
    Player model;
    public PlayerView(Player model) {
        super(model.getPos(), new Sprite(new Texture("evil_wizard_2/Attack1.png"), 0, 0, 250, 250), model);
        this.model = model;
    }

    @Override
    protected void readModelData() {
        drawPosition = model.getPos();
    }
}
