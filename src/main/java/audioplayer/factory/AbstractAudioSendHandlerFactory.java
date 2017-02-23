package audioplayer.factory;

import audioplayer.AbstractAudioPlayer;
import net.dv8tion.jda.core.audio.AudioSendHandler;

/**
 * Created by Phong on 11/02/2017.
 */
@FunctionalInterface
public interface AbstractAudioSendHandlerFactory {

    AudioSendHandler createAudioSendHandler(AbstractAudioPlayer audioPlayer);

}
