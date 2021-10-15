package game.sniper_monkey.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import game.sniper_monkey.model.ISoundManagerObserver;
import game.sniper_monkey.model.SoundEffect;
import game.sniper_monkey.model.world.World;

import java.util.HashMap;
import java.util.Map;

public final class SoundPlayer implements ISoundManagerObserver {

    private static SoundPlayer INSTANCE;
    private final Map<SoundEffect, Sound> sounds;

    private SoundPlayer() {
        sounds = new HashMap<>();
        sounds.put(SoundEffect.JUMP, Gdx.audio.newSound(Gdx.files.internal("untitled.wav")));
    }

    public static SoundPlayer getInstance() {
        if (INSTANCE == null) INSTANCE = new SoundPlayer();
        return INSTANCE;
    }

    @Override
    public void OnSoundEffectPlayed(SoundEffect sound) {
        if(sounds.containsKey(sound))
            sounds.get(sound).play();
    }

    @Override
    public void OnMusicPlayed() {

    }
}
