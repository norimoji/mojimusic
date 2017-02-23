package listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import commands.PermissionVerificationList;
import commands.general.AbstractGeneralCommands;
import enums.GeneralKeys;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.val;
import modules.annotations.Prefix;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import static enums.GeneralKeys.*;

/**
 * Created by Phong on 21/01/2017.
 */
@Singleton
@Getter(AccessLevel.PRIVATE)
public final class BotListener extends AbstractListener {

    private final AbstractGeneralCommands generalCommands;
    private final PermissionVerificationList userVerification;

    @Inject
    public BotListener(@Prefix String prefix, AbstractGeneralCommands generalCommands, PermissionVerificationList permissionVerification) {
        super(prefix);
        this.generalCommands = generalCommands;
        this.userVerification = permissionVerification;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getContent().contains("-")) {
            if (this.userVerification.userCheck(event.getAuthor())) {
                val command = event.getMessage().getContent().split(" ", 2);
                val textChannel = event.getTextChannel();
                val audioManager = event.getGuild().getAudioManager();
                val user = event.getAuthor();
                this.processEvent(command, textChannel, audioManager, user);
            } else {
                event.getTextChannel().sendMessage("You do not have the permission to use that command. " +
                        "Consult your admin to resolve this issue.").queue();
            }
        }
    }

    private void processEvent(String[] command, TextChannel textChannel, AudioManager audioManager, User user) {
        val inputCommand = command[0];

        val prefix          = super.getPrefix();
        val help            = prefix + HELP.toString();
        val join            = prefix + JOIN.toString();
        val leave           = prefix + LEAVE.toString();
        val id              = prefix + ID.toString();
        val avatarURL       = prefix + AVATAR.toString();
        val joinServer      = prefix + JOINSERVER.toString();
        val shutdown        = prefix + SHUTDOWN.toString();

        if (inputCommand.equals(help)) {
            val helpManual = this.generateHelpManual(prefix);
            this.getGeneralCommands().getHelp(textChannel, user, helpManual);
        }

        if (inputCommand.equals(join)) {
            this.getGeneralCommands().joinChannel(audioManager, user);
        }

        if (inputCommand.equals(leave)) {
            this.getGeneralCommands().leaveChannel(audioManager);
        }

        if (inputCommand.equals(id)) {
            this.getGeneralCommands().getId(textChannel, user);
        }

        if (inputCommand.equals(avatarURL)) {
            this.getGeneralCommands().getAvatarURL(textChannel, user);
        }

        if (inputCommand.equals(joinServer)) {
            this.getGeneralCommands().joinServer(textChannel, user);
        }

        if (inputCommand.equals(shutdown)) {
            this.getGeneralCommands().shutdown(textChannel);
        }
    }

    private String[] generateHelpManual(String prefix) {
        val generalKeys = GeneralKeys.values();
        val length = generalKeys.length - 1;
        val commands = new String[length];

        for (int i = 0; i < length;) {
            val key = generalKeys[++i];
            int index = i;
            commands[--index] = prefix + key.toString() + " | " + key.getDescription(key);
        }
        return commands;
    }

}
