package game.sniper_monkey.utils.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import game.sniper_monkey.model.player.fighter.EvilWizard;

public class HUDUtils {

    /**
     * Returns a cutout region of the first image of the idle sprite sheet representing the fighter.
     *
     * @param fighter The fighter with a corresponding idle sprite sheet.
     * @return A cutout region of the first image of the idle sprite sheet representing the fighter.
     * @throws IllegalArgumentException If no display name can be found for that class.
     */
    public static TextureRegion getCorrespondingTextureRegion(Class<?> fighter) {
        if (fighter == EvilWizard.class) {
            Texture idle = new Texture("images/evil_wizard_2/Idle.png");
            return new TextureRegion(idle, 104, 69, 64, 100);
        }
        // TODO do for future fighters when they have been implemented.
        else {
            throw new IllegalArgumentException("No Sprite found for this fighter class.");
        }
    }

    /**
     * Returns the corresponding display name for a specific fighter class.
     *
     * @param fighter The subclass of Fighter.
     * @return The display name of the fighter.
     * @throws IllegalArgumentException If no display name can be found for that class.
     */
    public static String getFighterDisplayName(Class<?> fighter) {
        if (fighter == EvilWizard.class) {
            return "Evil Wizard";
        }
        // TODO do for future fighters when they have been implemented.
        else {
            throw new IllegalArgumentException("No display name found for this fighter class.");
        }
    }

    /**
     * Returns the corresponding display name for a specific attack class.
     *
     * @param attack The subclass of Attack.
     * @return The display name of the attack.
     * @throws IllegalArgumentException If no display name can be found for that class.
     */
    public static String getAttackDisplayName(Class<?> attack) {
        // TODO implement method
        return "";
    }
}
