package audioplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

/**
 * Created by Phong on 11/02/2017.
 */
@FunctionalInterface
public interface AbstractAudioPlayer {

    AudioPlayer get();

}
