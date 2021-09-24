package game.sniper_monkey.world;

import game.sniper_monkey.player.Player;

import java.util.HashMap;

public class GameObjectViewFactory {
    private interface ViewCreator {
        GameObjectView createView(GameObject obj);
    }

    private static final HashMap<Class, ViewCreator> dispatch = new HashMap<>();

    static {
        dispatch.put(Player.class, new ViewCreator() {
            @Override
            public GameObjectView createView(GameObject obj) {
                return createFighterView((Player) obj);
            }
        });
        /*dispatch.put(Arrow.class, new ViewCreator() {
            @Override
            public GameObjectView createView(GameObject obj) {
                return createArrowView((Arrow)obj);
            }
        });*/
    }

    private static GameObjectView createFighterView(Player player) {
        //Create Specific fighter view in this method
        //return new GameObjectView();
        return null;
    }

    public static GameObjectView viewFromGameObject(GameObject obj) {
        ViewCreator viewCreator = dispatch.get(obj.getClass());
        if (viewCreator == null) /*throw exeption?*/ return null;
        return viewCreator.createView(obj);
    }
}
