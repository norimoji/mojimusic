package commands.audio;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.io.File;

/**
 * Created by Huy on 26/01/2017.
 */
public interface AbstractAudioCommands {

    void queueAutoPlay(final TextChannel channel, final File file) throws Exception;

    void queueAndPlay(final TextChannel channel, final String trackURL);

    void skipTrack(final TextChannel channel);

    void pause(final TextChannel channel);

    void resume(final TextChannel channel);

    void nowPlaying(final TextChannel channel);

    void shuffle(final TextChannel channel);

    void list(final TextChannel channel);

    void getMusicHelp(final TextChannel channel, final User user, String[] commands);

}
