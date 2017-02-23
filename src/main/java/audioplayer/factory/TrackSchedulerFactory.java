package audioplayer.factory;

import audioplayer.AbstractAudioPlayer;
import audioplayer.TrackScheduler;
import com.google.inject.Singleton;

/**
 * Created by Phong on 11/02/2017.
 */
@Singleton
public final class TrackSchedulerFactory implements AbstractTrackSchedulerFactory {

    @Override
    public TrackScheduler createTrackScheduler(AbstractAudioPlayer audioPlayer) {
        return new TrackScheduler(audioPlayer);
    }

}
