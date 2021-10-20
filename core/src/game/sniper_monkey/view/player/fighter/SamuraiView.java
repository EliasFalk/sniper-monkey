package game.sniper_monkey.view.player.fighter;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.PhysicalState;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.ReadablePlayer;
import game.sniper_monkey.utils.view.SpriteUtils;
import game.sniper_monkey.view.GameObjectView;

import java.util.HashMap;
import java.util.Map;

public class SamuraiView extends GameObjectView {

    private static final Vector2 drawOffset = new Vector2(-81, -78);
    private final ReadablePlayer model;
    private final float frameDuration = 0.1f;
    private final Map<PhysicalState, Animation<Sprite>> animations = new HashMap<>();
    private long animationStartTime;
    private PhysicalState lastAnimation;


    /**
     * Creates a samurai view
     */
    public SamuraiView(Player model) {
        super(drawOffset, SpriteUtils.getDefaultSprite(), model);
        this.model = model;
    }
}
