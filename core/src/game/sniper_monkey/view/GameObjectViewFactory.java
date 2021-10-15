package game.sniper_monkey.view;

import game.sniper_monkey.model.platform.Platform;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.fighter.EvilWizard;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.model.player.fighter.HuntressBow;
import game.sniper_monkey.model.world.GameObject;
import game.sniper_monkey.view.platform.PlatformView;
import game.sniper_monkey.view.player.fighter.EvilWizardView;
import game.sniper_monkey.view.player.fighter.HuntressView;

import java.util.HashMap;

/**
 * A static class creating GameObjectViews using GameObjects by determining their type at runtime.
 *
 * @author Vincent Hellner
 */
public final class GameObjectViewFactory {

    private GameObjectViewFactory() {
    }

    private static final HashMap<Class<? extends GameObject>, ViewCreator> viewCreatorDispatch = new HashMap<>();
    private static final HashMap<Class<? extends Fighter>, ViewCreator> fighterDispatch = new HashMap<>();

    static {
        //Lambdas calling the corresponding create function based on the type of the GameObject supplied.
        viewCreatorDispatch.put(Player.class, obj -> createFighterView((Player) obj));
        viewCreatorDispatch.put(Platform.class, obj -> new PlatformView((Platform) obj));
    }

    static {
        fighterDispatch.put(EvilWizard.class, obj -> new EvilWizardView((Player) obj));
        fighterDispatch.put(HuntressBow.class, obj -> new HuntressView((Player) obj));
    }

    private static GameObjectView createFighterView(Player player) {
        //If the type is Player then use fighterDispatch to determine the exact createView function to use with the player as model
        ViewCreator viewCreator = fighterDispatch.get(player.getActiveFighterClass());
        if (viewCreator == null) /*throw exception?*/ return null;
        return viewCreator.createView(player);
    }

    /**
     * Creates and returns a view corresponding to the type of the GameObject supplied.
     *
     * @param obj The GameObject to create a view based on.
     * @return A view GameObjectView obj as model.
     */
    public static GameObjectView viewFromGameObject(GameObject obj) {
        ViewCreator viewCreator = viewCreatorDispatch.get(obj.getClass());
        if (viewCreator == null) /*throw exception?*/ return null;
        return viewCreator.createView(obj);
    }

    //Functional interface used in dispatch lambdas
    @FunctionalInterface
    private interface ViewCreator {
        GameObjectView createView(GameObject obj);
    }
}
