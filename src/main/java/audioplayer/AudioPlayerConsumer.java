package audioplayer;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

/**
 * Created by Phong on 11/02/2017.
 */
@Singleton
public final class AudioPlayerConsumer implements AbstractAudioPlayer {

    private final AudioPlayerProvider audioPlayerProvider;
    private final AudioPlayer audioPlayer;

    @Inject
    public AudioPlayerConsumer(AudioPlayerProvider audioPlayerProvider) {
        this.audioPlayer = audioPlayerProvider.get();
        this.audioPlayerProvider = audioPlayerProvider;
    }

    @Override
    public final AudioPlayer get() {
        return this.audioPlayer;
    }

    public final AudioPlayerConsumer getAudioPlayerConsumer(){
        AudioPlayerConsumer audioPlayerConsumer = new AudioPlayerConsumer(this.audioPlayerProvider);
        return audioPlayerConsumer;
    }

}
