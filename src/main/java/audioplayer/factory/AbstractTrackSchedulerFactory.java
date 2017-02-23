package audioplayer.factory;

import audioplayer.AbstractAudioPlayer;
import audioplayer.TrackScheduler;

/**
 * Created by Phong on 11/02/2017.
 */
@FunctionalInterface
public interface AbstractTrackSchedulerFactory {

    TrackScheduler createTrackScheduler(AbstractAudioPlayer audioPlayer);

}
