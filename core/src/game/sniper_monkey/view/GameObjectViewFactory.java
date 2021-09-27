package game.sniper_monkey.view;

import game.sniper_monkey.player.Player;
import game.sniper_monkey.world.GameObject;

import java.util.HashMap;

public class GameObjectViewFactory {
    private interface ViewCreator {
        GameObjectView createView(GameObject obj);
    }

    private static final HashMap<Class<?>, ViewCreator> viewCreatorDispatch = new HashMap<>();
    static {
        viewCreatorDispatch.put(Player.class, obj -> createFighterView((Player) obj));
        //viewCreatorDispatch.put(Arrow.class, obj -> createArrowView((Arrow) obj));
    }

    private static final HashMap<Class<?>, ViewCreator> fighterDispatch = new HashMap<>();
    static {
        //fighterDispatch.put(MageFighter.class, obj -> createMageFighterView((MageFighter) obj));
    }

    private static GameObjectView createFighterView(Player player) {
        /*ViewCreator viewCreator = fighterDispatch.get(player.getFighter().getClass());
        if (viewCreator == null) throw exception? return null;
        return viewCreator.createView(player.getFighter());*/
        return null;
    }

    private static GameObjectView createMageFighterView(/*MageFighter fighter*/)
    {
        return null;
    }

    public static GameObjectView viewFromGameObject(GameObject obj) {
        ViewCreator viewCreator = viewCreatorDispatch.get(obj.getClass());
        if (viewCreator == null) /*throw exception?*/ return null;
        return viewCreator.createView(obj);
    }
}
