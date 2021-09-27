package game.sniper_monkey.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public final class World {
    private static World INSTANCE;

    private ArrayList<GameObject> gameObjects;
    private ArrayList<IWorldObserver> observers;

    interface State {
        void performState();
    }

    private int roundDuration = 60;
    Timer roundTimer = new Timer(roundDuration);

    /**
     * Starts the fightingState of the game by activating the roundTimer and setting the next state to playingState
     */
    private void startFightingState() {
        roundDuration = 60;
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

        int timeLeft = 60-roundTimer.getTimePassed();

        if(roundTimer.isDone()) {
            currentState = this::endGameState;
        } else {
            //TODO: Input what should happen during playingState

            //Mostly for clean output in the console, can be removed whenever
            if(roundDuration != timeLeft) {
                System.out.println("ROUNDTIMER: " + timeLeft);
                roundDuration -= 1;
            }
        }
    }

    /**
     * The state of the game when the combating has ended.
     * Actively waits for input to start the game again.
     */
    private void endGameState() {
        System.out.println("GAME ENDED!");
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            startFightingState();
        }
    }

    State currentState = this::startFightingState;

    private World() {
        gameObjects = new ArrayList<>();
        observers = new ArrayList<>();
    }

    //Singleton
    public static World getInstance() {
        if (INSTANCE == null) INSTANCE = new World();
        return INSTANCE;
    }

    public void registerObserver(IWorldObserver observer) {
        observers.add(observer);
    }

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

    /**
     * Calls update on all GameObjects in the world
     */
    public void update(float deltaTime) {
        for (GameObject obj : gameObjects) {
            obj.update(deltaTime);
        }
    }

    /**
     * Adds a GameObject to the world
     *
     * @param obj the object to add
     */
    public void addGameObject(GameObject obj) {
        gameObjects.add(obj);
        notifyObserversOfNewObject(obj);
        CollisionEngine.insertIntoSpatialHash(obj, obj.getHitbox());
    }

    /**
     * Removes a GameObject from the world if it exists
     *
     * @param obj the object to remove
     */
    public void deleteGameObject(GameObject obj) {
        gameObjects.remove(obj);
        notifyObserversOfRemovedObject(obj);
    }
}
