package game.sniper_monkey.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import game.sniper_monkey.model.IAudibleEventObserver;
import game.sniper_monkey.model.AudibleEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class SoundPlayer implements IAudibleEventObserver {

    private static SoundPlayer INSTANCE;
    private final Map<AudibleEvent, Sound> sounds;
    private final Random random;
    private static final float PITCH_VARIATION = 0.1f;

    private SoundPlayer() {
        random = new Random();
        sounds = new HashMap<>();
        sounds.put(AudibleEvent.JUMP, Gdx.audio.newSound(Gdx.files.internal("Jump.wav")));
    }

    public static SoundPlayer getInstance() {
        if (INSTANCE == null) INSTANCE = new SoundPlayer();
        return INSTANCE;
    }

    @Override
    public void OnAudibleEvent(AudibleEvent event) {
        if(sounds.containsKey(event))
        {
            Sound sound = sounds.get(event);
            float randomPitchVar = (random.nextFloat() - 0.5f) * (PITCH_VARIATION / 0.5f);
            sound.setPitch(sound.play(), 1 + randomPitchVar);
        }
    }

    @Override
    public void OnMusicPlayed() {

    }
}
