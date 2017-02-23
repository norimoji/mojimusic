package audioplayer;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.val;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

import java.io.*;

import static enums.MarkdownDecorator.BOLD;

/**
 * Created by Phong on 09/02/2017.
 */
@Singleton
public class AudioEventProcessor extends AudioEventAdapter {

    private final AbstractAudioPlayerManager audioManager;
    private final MusicManagerHandler musicManagerHandler;

    @Inject
    public AudioEventProcessor(AbstractAudioPlayerManager audioManager, MusicManagerHandler guildAudioManagerHandler) {
        this.audioManager = audioManager;
        this.musicManagerHandler = guildAudioManagerHandler;
    }

    public final void queueAndPlay(TextChannel channel, String trackUrl) {
        val guildMusicManager = this.musicManagerHandler.getMusicManager(channel.getGuild());
        this.audioManager.getAudioManager().loadItemOrdered(guildMusicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                channel.sendMessage("Enqueued " + BOLD + audioTrack.getInfo().title + BOLD + " to be played.").queue();
                AudioEventProcessor.this.play(channel, guildMusicManager, audioTrack);
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                AudioTrack firstTrack = audioPlaylist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = audioPlaylist.getTracks().get(0);
                }

                channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + audioPlaylist.getName() + ")").queue();
                AudioEventProcessor.this.play(channel, guildMusicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nothing found by " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                channel.sendMessage("Could not play: " + e.getMessage()).queue();
            }
        });
    }

    private void play(TextChannel textChannel, MusicManager guildAudioManager, AudioTrack track) {
        connectToFirstVoiceChannel(textChannel.getGuild().getAudioManager());
        guildAudioManager.getTrackScheduler().queueTrack(textChannel,track);
    }

    private void connectToFirstVoiceChannel(AudioManager audioManager) {
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
            for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                audioManager.openAudioConnection(voiceChannel);
                break;
            }
        }
    }

    public void queueFile(final TextChannel channel, File fileLocation) throws IOException {
        try {
            BufferedReader  bf = new BufferedReader(new FileReader(fileLocation));
            String line;
            while ((line = bf.readLine()) != null && !line.equals("")) {
                this.queueAndPlay(channel, line);
            }
            bf.close();
        } catch (IOException e) {
            channel.sendMessage("The file you are trying to access is incorrect, please check the file location.").queue();
        }
    }
}
