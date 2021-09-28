package game.sniper_monkey.view;

import game.sniper_monkey.platform.Platform;
import game.sniper_monkey.player.Player;
import game.sniper_monkey.view.platform.PlatformView;
import game.sniper_monkey.view.player.fighter.*;
import game.sniper_monkey.world.GameObject;
import game.sniper_monkey.player.fighter.*;

import java.util.HashMap;

public class GameObjectViewFactory {

    //Functional interface used for dispatch lambdas
    private interface ViewCreator {
        GameObjectView createView(GameObject obj);
    }

    private static final HashMap<Class<?>, ViewCreator> viewCreatorDispatch = new HashMap<>();
    static {
        //Lambdas calling the corresponding create function based on the type of the GameObject supplied.
        viewCreatorDispatch.put(Player.class, obj -> createFighterView((Player) obj));
        viewCreatorDispatch.put(Platform.class, obj -> createPlatformView((Platform) obj));
    }

    private static final HashMap<Class<?>, ViewCreator> fighterDispatch = new HashMap<>();
    static {
        fighterDispatch.put(EvilWizard.class, obj -> createEvilWizardView((Player) obj));
    }

    private static GameObjectView createFighterView(Player player) {
        //If the type is Player then use fighterDispatch to determine the exact createView function to use with the player as model
        ViewCreator viewCreator = fighterDispatch.get(player.getActiveFighterClass());
        if (viewCreator == null) /*throw exception?*/ return null;
        return viewCreator.createView(player);
    }

    private static GameObjectView createEvilWizardView(Player player)
    {
        return new EvilWizardView(player);
    }

    private static GameObjectView createPlatformView(Platform platform) {
        return new PlatformView(platform);
    }

    /**
     * Creates and returns a view corresponding to the type of the GameObject supplied.
     * @param obj The GameObject to create a view based on.
     * @return A view GameObjectView obj as model.
     */
    public static GameObjectView viewFromGameObject(GameObject obj) {
        ViewCreator viewCreator = viewCreatorDispatch.get(obj.getClass());
        if (viewCreator == null) /*throw exception?*/ return null;
        return viewCreator.createView(obj);
    }
}
