package commands.general;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.managers.AudioManager;

/**
 * Created by Huy on 21/01/2017.
 */
public interface AbstractGeneralCommands {

    void getHelp(TextChannel channel, User user, String[] commands);

    void joinChannel(AudioManager audioManager, User user);

    void leaveChannel(AudioManager audioManager);

    void getId(TextChannel channel, User user);

    void getAvatarURL(TextChannel channel, User user);

    void joinServer(TextChannel channel, User user);

    void shutdown(TextChannel channel);
}
