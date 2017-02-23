package audioplayer;

import com.google.inject.Inject;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

/**
 * Created by Huy on 02/02/2017.
 */
public final class AudioPlayerProvider implements AbstractAudioPlayer {

    private final AudioPlayerManager audioManager;

    @Inject
    public AudioPlayerProvider(AudioPlayerManager audioManager) {
        this.audioManager = audioManager;
    }

    @Override
    public final AudioPlayer get() {
        return this.audioManager.createPlayer();
    }

}
