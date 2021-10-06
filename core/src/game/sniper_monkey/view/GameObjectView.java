package game.sniper_monkey.view;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.world.GameObject;

/**
 * A view with a GameObject as its model to read data from
 *
 * @author Vincent Hellner
 * @author Elias Falk
 */
public abstract class GameObjectView {
    private final GameObject model;
    protected Vector2 drawOffset;
    protected Sprite sprite;

    /**
     * Creates a GameObjectView
     *
     * @param drawOffset An offset to draw the sprite at.
     * @param sprite     The sprite to draw.
     * @param model      The GameObject used as the model.
     */
    public GameObjectView(Vector2 drawOffset, Sprite sprite, GameObject model) {
        this.drawOffset = drawOffset;
        this.sprite = sprite;
        this.model = model;
    }

    /**
     * Checks whether the supplied GameObject is the model of this view.
     *
     * @param model The model to check.
     * @return Is "model" the model of this view?
     */
    public boolean hasModel(GameObject model) {
        return this.model == model;
    }

    /**
     * Renders the view using a SpriteBatch and ShapeRenderer
     *
     * @param sr    The ShapeRenderer to use.
     * @param batch The SpriteBatch to use.
     */
    public void render(ShapeRenderer sr, SpriteBatch batch, boolean debugMode) {
        batch.draw(sprite, model.getPos().x + drawOffset.x, model.getPos().y + drawOffset.y);
        if (debugMode) {
            sr.setColor(0, 0, 0, 1);
            Vector2 pos = model.getHitbox().getPosition();
            Vector2 size = model.getHitbox().getSize();
            sr.rect(pos.x, pos.y, size.x, size.y);
        }
    }

    /**
     * Overridable method for updating the sprite
     */
    public void updateSprite() { }
}
