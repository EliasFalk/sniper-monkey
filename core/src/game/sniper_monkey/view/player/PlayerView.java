package game.sniper_monkey.view.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.view.GameObjectView;
import game.sniper_monkey.player.Player;
import game.sniper_monkey.world.GameObject;

public class PlayerView extends GameObjectView {
    Player model;
    public PlayerView(Player model) {
        super(new Vector2(0,0), new Sprite(), model);
    }
}
