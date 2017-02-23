package audioplayer;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import lombok.Getter;

/**
 * Created by Huy on 02/02/2017.
 */
@Getter
@Singleton
public final class AudioManager implements AbstractAudioPlayerManager {

    private final AudioPlayerManager audioManager;

    @Inject
    public AudioManager(AudioPlayerManager audioPlayerManager) {
        this.audioManager = audioPlayerManager;

        AudioSourceManagers.registerRemoteSources(this.audioManager);
        AudioSourceManagers.registerLocalSource(this.audioManager);
    }

}
