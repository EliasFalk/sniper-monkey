package game.sniper_monkey.model;

public interface IAudibleEventObserver {
    void OnAudibleEvent(AudibleEvent sound);
    void OnMusicPlayed();
}
