package game.sniper_monkey.utils.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import game.sniper_monkey.model.player.fighter.EvilWizard;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.model.player.fighter.HuntressBow;
import game.sniper_monkey.model.player.fighter.attack.BowAttack;
import game.sniper_monkey.model.player.fighter.attack.EvilMagicHammerAttack;
import game.sniper_monkey.model.player.fighter.attack.EvilMagicSwingAttack;
import game.sniper_monkey.model.player.fighter.attack.IAttack;

/**
 * A utility class that returns the corresponding display name, image or similar given a class.
 *
 * @author Elias Falk
 */
public class HUDUtils {

    /**
     * Returns a cutout region of the first image of the idle sprite sheet representing the fighter.
     *
     * @param fighter The fighter with a corresponding idle sprite sheet.
     * @return A cutout region of the first image of the idle sprite sheet representing the fighter.
     * @throws IllegalArgumentException If no display name can be found for that class.
     */
    public static TextureRegion getCorrespondingTextureRegion(Class<? extends Fighter> fighter) {
        if (fighter == EvilWizard.class) {
            Texture idle = new Texture("images/evil_wizard_2/Idle.png");
            return new TextureRegion(idle, 104, 69, 64, 100);
        } else if(fighter == HuntressBow.class) {
            Texture idle = new Texture("images/huntress_2/idle.png");
            return new TextureRegion(idle, 52, 48, 46, 52);
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
    public static String getFighterDisplayName(Class<? extends Fighter> fighter) {
        if (fighter == EvilWizard.class) {
            return "Evil Wizard";
        } else if(fighter == HuntressBow.class) {
            return "Huntress Bow";
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
    public static String getAttackDisplayName(Class<? extends IAttack> attack) {
        if (attack == EvilMagicHammerAttack.class) {
            return "Evil Hammer Smash";
        } else if (attack == EvilMagicSwingAttack.class) {
            return "Evil Magic Swing";
        } else if (attack == BowAttack.class) {
            return "Bow Attack";
        } else {
            return "";
//            throw new IllegalArgumentException("No display name found for this attack class.");
        }
    }
}
