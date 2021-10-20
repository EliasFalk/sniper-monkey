package game.sniper_monkey.model.player;

public interface FluctuatingAttributeObserver {
    void onValueChange(float min, float max, float current);
}
