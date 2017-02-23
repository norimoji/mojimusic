package modules;

import audioplayer.*;
import audioplayer.AudioPlayerProvider;
import audioplayer.factory.*;
import com.google.inject.Singleton;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import net.dv8tion.jda.core.audio.AudioSendHandler;

/**
 * Created by Huy on 24/01/2017.
 */
public final class AudioModule extends CommonModule {

    @Override
    protected void configure() {
        bind(AudioPlayerManager.class).to(DefaultAudioPlayerManager.class).in(Singleton.class);
        bind(AbstractAudioPlayerManager.class).to(AudioManager.class);

//        bind(AudioPlayerFactory.class);
        bind(AudioPlayerProvider.class);
        bind(AbstractAudioPlayer.class).to(AudioPlayerConsumer.class);
        bind(TrackScheduler.class);
        bind(MusicManager.class);
        bind(AudioEventProcessor.class);
        bind(AudioSendHandler.class).to(AudioSendHandlerImpl.class);

        bind(AbstractTrackSchedulerFactory.class).to(TrackSchedulerFactory.class);
        bind(AbstractAudioSendHandlerFactory.class).to(AudioSendHandlerFactory.class);

        bind(MusicManagerHandler.class);
    }

}
