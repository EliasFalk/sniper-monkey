package game.sniper_monkey.view;

import game.sniper_monkey.model.player.fighter.*;
import game.sniper_monkey.model.player.fighter.attack.attack_object.Shuriken;
import game.sniper_monkey.model.world_brick.WorldBrick;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.view.player.fighter.*;
import game.sniper_monkey.view.world_brick.WorldBrickView;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.model.player.fighter.attack.attack_object.Arrow;
import game.sniper_monkey.model.world.GameObject;
import game.sniper_monkey.model.world_brick.WorldBrick;
import game.sniper_monkey.view.world_brick.WorldBrickView;

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
        viewCreatorDispatch.put(WorldBrick.class, obj -> new WorldBrickView((WorldBrick) obj));
        viewCreatorDispatch.put(Arrow.class, obj -> new ArrowView((Arrow) obj));
        viewCreatorDispatch.put(Shuriken.class, obj -> new ShurikenView((Shuriken) obj));
    }

    static {
        fighterDispatch.put(EvilWizard.class, obj -> new EvilWizardView((Player) obj));
        fighterDispatch.put(HuntressBow.class, obj -> new HuntressView((Player) obj));
        fighterDispatch.put(Samurai.class, obj -> new SamuraiView((Player) obj));
        fighterDispatch.put(FantasyWarrior.class, obj -> new FantasyWarriorView((Player) obj));
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
