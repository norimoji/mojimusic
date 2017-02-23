package commands.audio;

import audioplayer.AudioEventProcessor;
import audioplayer.MusicManagerHandler;
import audioplayer.TrackScheduler;
import com.google.inject.Inject;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.val;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.io.File;

import static enums.MarkdownDecorator.*;
import static java.lang.System.lineSeparator;


/**
 * Created by Huy on 26/01/2017.
 */
public final class AudioCommands implements AbstractAudioCommands {

    private final AudioEventProcessor audioEventProcessor;
    private final MusicManagerHandler musicManagerHandler;

    @Inject
    public AudioCommands(AudioEventProcessor audioEventProcessor, MusicManagerHandler guildAudioManagerHandler) {
        this.audioEventProcessor = audioEventProcessor;
        this.musicManagerHandler = guildAudioManagerHandler;
    }

    private TrackScheduler getTrackScheduler(TextChannel channel) {
        return this.musicManagerHandler.getMusicManager(channel.getGuild()).getTrackScheduler();
    }

    private AudioPlayer getAudioPlayer(TextChannel channel) {
        return this.musicManagerHandler.getMusicManager(channel.getGuild()).getAudioPlayer();
    }

    @Override
    public void queueAutoPlay(TextChannel channel, File file) throws Exception {
        this.audioEventProcessor.queueFile(channel,file);
    }

    @Override
    public void queueAndPlay(TextChannel channel, String trackURL) {
        this.audioEventProcessor.queueAndPlay(channel,trackURL);
    }

    @Override
    public void skipTrack(TextChannel channel) {
        this.getTrackScheduler(channel).nextTrack();
    }

    @Override
    public void pause(TextChannel channel) {
        this.getAudioPlayer(channel).setPaused(true);
    }

    @Override
   public void resume(TextChannel channel) {
        this.getAudioPlayer(channel).setPaused(false);
        channel.getJDA().getPresence().setGame(Game.of(this.getAudioPlayer(channel).getPlayingTrack().getInfo().title));
    }

    @Override
    public void nowPlaying(TextChannel channel) {
        val audioTrack = this.getAudioPlayer(channel).getPlayingTrack();
        val trackInfo = this.getNowPlaying(channel, audioTrack).append(CODE_BLOCK)
                .append(" [").append(this.formatTrackDuration(audioTrack)).append("]").append(CODE_BLOCK).toString();
        channel.sendMessage(trackInfo).queue();
    }

    @Override
    public void shuffle(TextChannel channel) {
        this.getTrackScheduler(channel).shuffleToggle();
        channel.sendMessage("Shufflling...").queue();
    }

    @Override
    public void list(TextChannel channel) {
        channel.sendMessage(this.getTrackScheduler(channel).getQueuedTracks()).queue();
    }

    @Override
    public void getMusicHelp(TextChannel channel, User user, String[] commands) {
        channel.sendMessage(user.getAsMention() + this.getHelpManual(commands)).queue();
    }

    private String getHelpManual(String[] commands) {
        val helpManual = new StringBuilder(CODE_BLOCK_MULTILINE + "Music Commands:" + lineSeparator());

        for (String command : commands) {
            helpManual.append(command).append(lineSeparator());
        }

        return helpManual.append(CODE_BLOCK_MULTILINE).toString();
    }

    private String formatTrackDuration(AudioTrack track) {
        long convertingValue = track.getDuration();
        int hours = (int) (convertingValue / (1000 * 60 * 60)) % 24;
        int minutes = (int) (convertingValue / (1000 * 60) % 60);
        int seconds = (int) (convertingValue / 1000) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private StringBuilder getNowPlaying(TextChannel channel, AudioTrack track) {
        val nowPlaying = new StringBuilder("Now Playing in ");
        val voiceChannels = channel.getGuild().getVoiceChannels();
        val bot = channel.getJDA().getSelfUser();

        for (val voiceChannel : voiceChannels) {
            val members = voiceChannel.getMembers();

            if (members.size() > 0) {
                for (val member : members) {
                    if (member.getUser().getId().equals(bot.getId())) {
                        val voiceChannelName = CODE_BLOCK + voiceChannel.getName() + CODE_BLOCK + ": ";

                        nowPlaying.append(voiceChannelName).append(BOLD)
                                .append(track.getInfo().title).append(BOLD);
                        break;
                    }
                }
            }
        }
        return nowPlaying;
    }

}