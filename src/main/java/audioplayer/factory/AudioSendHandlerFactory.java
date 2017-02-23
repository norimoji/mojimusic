package audioplayer.factory;

import audioplayer.AbstractAudioPlayer;
import audioplayer.AudioSendHandlerImpl;
import com.google.inject.Singleton;
import net.dv8tion.jda.core.audio.AudioSendHandler;

/**
 * Created by Phong on 11/02/2017.
 */
@Singleton
public final class AudioSendHandlerFactory implements AbstractAudioSendHandlerFactory {

    @Override
    public AudioSendHandler createAudioSendHandler(AbstractAudioPlayer audioPlayer) {
        return new AudioSendHandlerImpl(audioPlayer);
    }

}
