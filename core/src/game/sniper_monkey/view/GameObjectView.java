package game.sniper_monkey.view;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.world.GameObject;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class GameObjectView {
    private GameObject model;
    protected Vector2 drawPosition;
    protected Sprite sprite;

    public GameObjectView(Vector2 drawPosition, Sprite sprite, GameObject model) {
        this.drawPosition = drawPosition;
        this.sprite = sprite;
        this.model = model;
    }

    public boolean hasModel(GameObject model) {
        return this.model == model;
    }

    protected abstract void readModelData();

    public void render(ShapeRenderer sr, SpriteBatch batch) {
        readModelData();
        batch.draw(sprite, drawPosition.x, drawPosition.y);
        sr.setColor(0, 0, 0, 1);
        Vector2 pos = model.getHitbox().getPosition();
        Vector2 size = model.getHitbox().getSize();
        sr.rect(pos.x, pos.y, size.x, size.y);
    }
}
