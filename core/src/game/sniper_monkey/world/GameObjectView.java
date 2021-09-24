package game.sniper_monkey.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObjectView {
    private GameObject model;
    protected Vector2 drawPosition;
    protected Sprite sprite;

    public GameObjectView(Vector2 drawPosition, Sprite sprite, GameObject model) {
        this.drawPosition = drawPosition;
        this.sprite = sprite;
    }

    public boolean hasModel(GameObject model) {
        return this.model == model;
    }

    public void render(SpriteBatch batch) {
        batch.draw(sprite, drawPosition.x, drawPosition.y);
    }
}
