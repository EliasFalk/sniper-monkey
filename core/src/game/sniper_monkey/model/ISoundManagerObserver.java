package game.sniper_monkey.model;

public interface ISoundManagerObserver {
    void OnSoundEffectPlayed(SoundEffect sound);
    void OnMusicPlayed();
}
