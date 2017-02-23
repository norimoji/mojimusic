package audioplayer;

import com.google.inject.Inject;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import lombok.Getter;
import lombok.val;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

import static enums.MarkdownDecorator.BOLD;
import static enums.MarkdownDecorator.CODE_BLOCK;
import static java.lang.System.lineSeparator;

/**
 * Created by Huy on 02/02/2017.
 */
public final class TrackScheduler extends AudioEventAdapter {

    @Getter
    private final AudioPlayer audioPlayer;
    private final BlockingQueue<AudioTrack> queue;
    private final AtomicReference<TextChannel> outputChannel;

    @Inject
    public TrackScheduler(AbstractAudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer.get();
        this.queue = new LinkedBlockingQueue<>();
        this.outputChannel = new AtomicReference<>();
    }

    public void queueTrack(TextChannel textChannel, AudioTrack audioTrack) {
        if (this.audioPlayer.startTrack(audioTrack, true)) {
            this.outputChannel.set(textChannel);
            this.setGameInfo(audioTrack);
        } else {
            this.queue.offer(audioTrack);
        }
    }

    public void nextTrack() {
        if(this.queue.isEmpty()) {
            this.audioPlayer.stopTrack();
            this.outputChannel.get().getJDA().getPresence().setGame(null);
        } else {
            this.audioPlayer.startTrack(this.queue.element(), false);
            this.outputChannel.get().sendMessage(this.nowPlaying()).queue();
            this.setGameInfo(this.queue.element());
            this.queue.poll();
        }
    }

    public void shuffleToggle(){
        List<AudioTrack> queueTracks = new ArrayList<>(this.queue);
        AudioTrack currentTrack = queueTracks.get(0);
        queueTracks.remove(0);
        Collections.shuffle(queueTracks);
        queueTracks.add(0, currentTrack);
        this.queue.clear();
        this.queue.addAll(queueTracks);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            this.nextTrack();
        }
    }

    public String getQueuedTracks() {
        if (this.queue.isEmpty()) {
            return "Queue is empty";
        }

        val tracksInQueue = new StringBuilder("Songs currently in queue:" + lineSeparator() + lineSeparator());
        int counter = 1;
        for (AudioTrack audioTrack : this.queue) {
            tracksInQueue.append(" ").append(BOLD).append(counter++).append(". ")
                    .append(audioTrack.getInfo().title).append(BOLD)
                    .append(CODE_BLOCK).append(" [")
                    .append(this.longFormatter(audioTrack))
                    .append("]").append(CODE_BLOCK)
                    .append(lineSeparator());
        }
        return tracksInQueue.toString();
    }

    private String longFormatter(AudioTrack track) {
        long convertingValue = track.getDuration();
        int hours = (int) (convertingValue / (1000 * 60 * 60)) % 24;
        int minutes = (int) (convertingValue / (1000 * 60) % 60);
        int seconds = (int) (convertingValue / 1000) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private String nowPlaying() {
        StringBuilder audioTrack = new StringBuilder("Now playing: ");
        audioTrack.append(BOLD).append(this.queue.element().getInfo().title).append(BOLD);
        return audioTrack.toString();
    }

    private void setGameInfo(AudioTrack audioTrack){
        this.outputChannel.get().getJDA().getPresence().setGame(Game.of(audioTrack.getInfo().title));
    }
}
