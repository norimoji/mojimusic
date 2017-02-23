package audioplayer.factory;

import audioplayer.AudioPlayerConsumer;
import audioplayer.AudioPlayerProvider;
import audioplayer.MusicManager;
import audioplayer.TrackScheduler;
import com.google.inject.Inject;
import com.google.inject.Provider;
import lombok.val;
import net.dv8tion.jda.core.audio.AudioSendHandler;

/**
 * Created by Phong on 11/02/2017.
 */
public final class MusicManagerFactory implements Provider<MusicManager> {

    private final AudioPlayerConsumer audioPlayer;
    private final TrackScheduler trackScheduler;
    private final AudioSendHandler audioSendHandler;

    private final AbstractTrackSchedulerFactory trackFactory;
    private final AbstractAudioSendHandlerFactory sendHandlerFactory;

    @Inject
    public MusicManagerFactory(AudioPlayerConsumer audioPlayer, AbstractTrackSchedulerFactory trackSchedulerFactory, AbstractAudioSendHandlerFactory audioSendHandlerFactory, AudioPlayerProvider audioPlayerProvider) {
        this.audioPlayer = audioPlayer.getAudioPlayerConsumer();
        this.trackScheduler = trackSchedulerFactory.createTrackScheduler(this.audioPlayer);
        this.audioSendHandler = audioSendHandlerFactory.createAudioSendHandler(this.audioPlayer);

        this.trackFactory = trackSchedulerFactory;
        this.sendHandlerFactory = audioSendHandlerFactory;
    }

    @Override
    public MusicManager get() {
        val tempAudioPlayer = this.audioPlayer.getAudioPlayerConsumer();
        val tempTrackScheduler = this.trackFactory.createTrackScheduler(tempAudioPlayer);
        val tempAudioSendHandler = this.sendHandlerFactory.createAudioSendHandler(tempAudioPlayer);

        val tempMusicManager = new MusicManager(tempAudioPlayer, tempTrackScheduler, tempAudioSendHandler);
        return tempMusicManager;
    }
}
