package listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import commands.PermissionVerificationList;
import commands.audio.AbstractAudioCommands;
import enums.AudioKeys;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.val;
import modules.annotations.Prefix;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.File;

import static enums.AudioKeys.*;

/**
 * Created by Huy on 26/01/2017.
 */
@Singleton
@Getter(AccessLevel.PRIVATE)
public final class AudioListener extends AbstractListener {

    private final AbstractAudioCommands audioCommands;
    private final PermissionVerificationList userVerification;

    @Inject
    public AudioListener(@Prefix String prefix, AbstractAudioCommands audioCommands, PermissionVerificationList permissionVerification) {
        super(prefix);
        this.audioCommands = audioCommands;
        this.userVerification = permissionVerification;

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(this.userVerification.userCheck(event.getAuthor())) {
            val command = event.getMessage().getContent().split(" ", 2);
            try {
                this.processEvent(event, command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void processEvent(MessageReceivedEvent event, String[] command) throws Exception {
        val inputCommand = command[0];

        val prefix          = super.getPrefix();
        val play            = prefix + PLAY.toString();
        val skip            = prefix + SKIP.toString();
        val pause           = prefix + PAUSE.toString();
        val resume          = prefix + RESUME.toString();
        val nowPlaying      = prefix + NOWPLAYING.toString();
        val shuffle         = prefix + SHUFFLE.toString();
        val list            = prefix + LIST.toString();
        val musicHelp       = prefix + MUSICHELP.toString();

        if (event.getGuild() != null) {
            if (command.length == 2) {
                if (inputCommand.equals(play) && command[1].startsWith("http") || command[1].contains(".mp3")) {
                    this.getAudioCommands().queueAndPlay(event.getTextChannel(), command[1]);
                }

                if (inputCommand.equals(play) && command[1].startsWith("C")) {
                    this.getAudioCommands().queueAutoPlay(event.getTextChannel(), new File(command[1]));
                }
            }

            if (inputCommand.equals(skip)) {
                this.getAudioCommands().skipTrack(event.getTextChannel());
            }

            if (inputCommand.equals(pause)) {
                this.getAudioCommands().pause(event.getTextChannel());
            }

            if (inputCommand.equals(resume)) {
                this.getAudioCommands().resume(event.getTextChannel());
            }

            if (inputCommand.equals(nowPlaying)) {
                this.getAudioCommands().nowPlaying(event.getTextChannel());
            }

            if (inputCommand.equals(shuffle)) {
                this.getAudioCommands().shuffle(event.getTextChannel());
            }

            if (inputCommand.equals(list)) {
                this.getAudioCommands().list(event.getTextChannel());
            }

            if (inputCommand.equals(musicHelp)) {
                val commands = this.generateHelpManual(prefix);
                this.getAudioCommands().getMusicHelp(event.getTextChannel(), event.getAuthor(), commands);
            }
        }
    }

    private String[] generateHelpManual(String prefix) {
        val audioKeys = AudioKeys.values();
        val length = audioKeys.length - 1;
        val commands = new String[length];

        for (int i = 0; i < length;) {
            val key = audioKeys[++i];
            int index = i;
            commands[--index] = prefix + key.toString() + " | " + key.getDescription(key);
        }
        return commands;
    }

}
