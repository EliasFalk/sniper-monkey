package game.sniper_monkey.model.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import game.sniper_monkey.model.Config;
import game.sniper_monkey.model.collision.CollisionEngine;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Represents the model for the world where all game objects are existing in.
 * It keeps track of the state of the game, updates timers as well as adds objects to the world and updates them
 *
 *
 * @author Elias Falk
 * @author Vincent Hellner
 * @author Kevin Jeryd
 * @author Dadi Andrason
 */
public final class World {
    private static World INSTANCE;

    private final List<GameObject> gameObjects;
    private final Queue<GameObject> queuedForAddition;
    private final Queue<GameObject> queuedForRemoval;

    private final List<IWorldObserver> observers;
    private final CallbackTimer roundTimer;
    private final List<ITimerObserver> timerObservers;
    private State currentState = this::startFightingState;

    @FunctionalInterface
    interface State {
        void performState();
    }

    private World() {
        gameObjects = new ArrayList<>();
        observers = new ArrayList<>();
        timerObservers = new ArrayList<>();
        queuedForAddition = new ArrayDeque<>();
        queuedForRemoval = new ArrayDeque<>();

        Config.readConfigFile("cfg/game.cfg");
        float roundTime = Config.getNumber("cfg/game.cfg", "ROUND_TIME");
        roundTimer = new CallbackTimer(roundTime, () -> currentState = this::endGameState);
    }

    //TODO documentation
    //Singleton
    public static World getInstance() {
        if (INSTANCE == null) INSTANCE = new World();
        return INSTANCE;
    }

    //TODO documentation
    public int getRoundDuration() {
        return (int) roundTimer.getTimeLeft();
    }

    /**
     * Starts the fightingState of the game by activating the roundTimer and setting the next state to playingState
     */
    private void startFightingState() {
        roundTimer.start();
        System.out.println("START FIGHT!");
        currentState = this::playingState;
    }

    /**
     * The state of the game while actively combating.
     * If the roundTimer is done, then switch to the endGameState.
     * If the roundTimer is still counting, show the time left until the game is done.
     */
    private void playingState() {
        notifyObserversOfTimerChange((int) roundTimer.getTimeLeft());
    }

    /**
     * The state of the game when the combating has ended.
     * Actively waits for input to start the game again.
     */
    private void endGameState() {
        System.out.println("GAME ENDED!");
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            startFightingState();
        }
    }

    //TODO documentation
    public void registerObserver(IWorldObserver observer) {
        observers.add(observer);
    }

    //TODO documentation
    public void unregisterObserver(IWorldObserver observer) {
        observers.remove(observer);
    }

    private void notifyObserversOfNewObject(GameObject obj) {
        for (IWorldObserver observer : observers) {
            observer.onObjectAddedToWorld(obj);
        }
    }

    private void notifyObserversOfRemovedObject(GameObject obj) {
        for (IWorldObserver observer : observers) {
            observer.onObjectRemovedFromWorld(obj);
        }
    }

    //TODO documentation
    //TIMER OBSERVER STUFF
    public void registerTimerObserver(ITimerObserver timerObserver) {
        timerObservers.add(timerObserver);
    }

    public void unregisterTimerObserver(ITimerObserver deleteTimerObserver) {
        timerObservers.remove(deleteTimerObserver);
    }

    private void notifyObserversOfTimerChange(int time) {
        for (ITimerObserver timerObserver : timerObservers) {
            timerObserver.onTimerChange(time);
        }
    }

    /**
     * Calls update on all GameObjects in the world
     */
    public void update(float deltaTime) {
        addQueuedGameObjects();
        removeQueuedGameObjects();

        currentState.performState();
        for (GameObject obj : gameObjects) {
            obj.update(deltaTime);
        }

        // TODO maybe move this somewhere else.
    }

    private void addQueuedGameObjects() {
        while(!queuedForAddition.isEmpty()) {
            GameObject obj = queuedForAddition.remove();
            gameObjects.add(obj);
            notifyObserversOfNewObject(obj);
            CollisionEngine.registerGameObject(obj, obj.isDynamic());
        }
    }

    private void removeQueuedGameObjects() {
        while(!queuedForRemoval.isEmpty()) {
            GameObject obj = queuedForRemoval.remove();
            gameObjects.remove(obj);
            notifyObserversOfRemovedObject(obj);
            CollisionEngine.unregisterGameObject(obj);;
        }
    }

    /**
     * Queues a GameObject for removal from the world,
     * which means it is removed at the start of the next frame
     *
     * @param obj the object to remove
     */
    public void queueRemoveGameObject(GameObject obj) {
        queuedForRemoval.add(obj);
    }

    /**
     * Queues a GameObject for addition to the world,
     * which means it is added at the start of the next frame
     *
     * @param obj the object to add
     */
    public void queueAddGameObject(GameObject obj) {
        queuedForAddition.add(obj);
    }
}
