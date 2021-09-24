package game.sniper_monkey.world;

import game.sniper_monkey.player.Player;

import java.util.HashMap;
import java.util.Map;

public class GameObjectViewFactory
{
    private interface ViewCreator
    {
        GameObjectView createView(GameObject obj);
    }

    private static final HashMap<Class, ViewCreator> dispatch = new HashMap<>();
    static
    {
        dispatch.put(Player.class, new ViewCreator() {
            @Override
            public GameObjectView createView(GameObject obj) {
                return createFighterView();
            }
        });
        /*dispatch.put(Arrow.class, new ViewCreator() {
            @Override
            public GameObjectView createView(GameObject obj) {
                return createArrowView();
            }
        });*/
    }

    private static GameObjectView createFighterView()
    {
        //Create Specific fighter view in this method
        //return new GameObjectView();
        return null;
    }

    public static GameObjectView viewFromGameObject(GameObject obj)
    {
        ViewCreator viewCreator = dispatch.get(obj.getClass());
        if(viewCreator == null) /*throw exeption?*/ return null;
        return viewCreator.createView(obj);
    }
}
