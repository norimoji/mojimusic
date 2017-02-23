package audioplayer;

import com.google.inject.Inject;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.core.audio.AudioSendHandler;

/**
 * Created by Huy on 26/01/2017.
 */
public final class AudioSendHandlerImpl implements AudioSendHandler {

    private final AudioPlayer audioPlayer;
    private AudioFrame lastFrame;

    @Inject
    public AudioSendHandlerImpl(AbstractAudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer.get();
    }

    @Override
    public boolean canProvide() {
        this.setLastFrame();
        return this.isFrameNotNull();
    }

    @Override
    public byte[] provide20MsAudio() {
        try {
            this.setLastFrame();
            return (this.isFrameNotNull()) ? this.lastFrame.data : null;
        } finally {
            this.lastFrame = null;
        }
    }

    @Override
    public boolean isOpus() {
        return true;
    }

    private void setLastFrame() {
        if (this.lastFrame == null) {
            this.lastFrame = this.audioPlayer.provide();
        }
    }

    private boolean isFrameNotNull() {
        return this.lastFrame != null;
    }

}
