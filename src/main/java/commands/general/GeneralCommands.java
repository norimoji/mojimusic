package commands.general;

import com.google.inject.Singleton;
import lombok.val;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.managers.AudioManager;

import static enums.MarkdownDecorator.CODE_BLOCK_MULTILINE;
import static java.lang.System.lineSeparator;

/**
 * Created by Huy on 21/01/2017.
 */
@Singleton
public class GeneralCommands implements AbstractGeneralCommands {

    @Override
    public void getHelp(TextChannel channel, User user, String[] commands) {
        channel.sendMessage(user.getAsMention() + this.getHelpManual(commands)).queue();
    }

    @Override
    public void joinChannel(AudioManager audioManager, User user) {
        if (!audioManager.isAttemptingToConnect()) {
            val voiceChannels = audioManager.getGuild().getVoiceChannels();

            for (val voiceChannel : voiceChannels) {
                for (val connectedUser : voiceChannel.getMembers()) {
                    val requester = connectedUser.getUser().getId();

                    if (requester.equals(user.getId())) {
                        audioManager.openAudioConnection(voiceChannel);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void leaveChannel(AudioManager audioManager) {
        audioManager.closeAudioConnection();
    }

    @Override
    public void getId(TextChannel channel, User user) {
        val message = ", your ID is: " + CODE_BLOCK_MULTILINE + user.getId() + CODE_BLOCK_MULTILINE;
        channel.sendMessage(user.getAsMention() + message).queue();
    }

    @Override
    public void getAvatarURL(TextChannel channel, User user) {
        val avatar = (user.getAvatarUrl() != null) ? user.getAvatarUrl() : user.getDefaultAvatarUrl();
        channel.sendMessage(user.getAsMention() + " " + avatar).queue();
    }

    @Override
    public void joinServer(TextChannel channel, User user) {
        val botUser = channel.getJDA().getSelfUser();
        val inviteLink = new StringBuilder(" https://discordapp.com/oauth2/authorize?client_id=");
        inviteLink.append(botUser.getId()).append("&scope=bot");

        val message = ", Bots cannot use invite links! Click here to invite me: ";

        channel.sendMessage(user.getAsMention() + message + inviteLink).queue();
    }

    @Override
    public void shutdown(TextChannel channel) {
        channel.getJDA().shutdown();
    }

    private String getHelpManual(String[] commands) {
        val helpManual = new StringBuilder(CODE_BLOCK_MULTILINE + "General Commands:" + lineSeparator() + lineSeparator());

        for (String command : commands) {
            helpManual.append(command).append(lineSeparator());
        }

        return helpManual.append(CODE_BLOCK_MULTILINE).toString();
    }
}
