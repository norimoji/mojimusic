package audioplayer;

import com.google.inject.Inject;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import lombok.Getter;
import net.dv8tion.jda.core.audio.AudioSendHandler;

/**
 * Created by Huy on 02/02/2017.
 */
@Getter
public final class MusicManager {

    private final AudioPlayer audioPlayer;
    private final TrackScheduler trackScheduler;
    private final AudioSendHandler audioSendHandler;

    @Inject
    public MusicManager(AudioPlayerConsumer audioPlayer, TrackScheduler trackScheduler, AudioSendHandler audioSendHandler) {
        this.audioPlayer = audioPlayer.get();
        this.trackScheduler = trackScheduler;
        this.audioSendHandler = audioSendHandler;
        this.audioPlayer.addListener(this.trackScheduler);
    }

}

