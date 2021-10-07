package game.sniper_monkey.model.player;

public interface ReadablePlayer {
    FighterAnimation getCurrentFighterAnimation();

    boolean isLookingRight();

    Class<?> getActiveFighterClass();
}
