package enums;

/**
 * Created by Huy on 14/01/2017.
 */
public enum AudioKeys implements Description<AudioKeys, String> {

    MUSICHELP,
    PLAY,
    SKIP,
    NOWPLAYING,
    PAUSE,
    RESUME,
    SHUFFLE,
    LIST;

    @Override
    public final String toString() {
        if (super.name().equals(NOWPLAYING.name())) {
            return "np";
        }
        if (super.name().equals(LIST.name())){
            return "ls";
        }
        return super.name().toLowerCase();
    }

    @Override
    public final String getDescription(AudioKeys inputCommand) {
        String description;
        switch (inputCommand) {
            case PLAY: {
                description = this.getPlayDescription();
                break;
            } case SKIP: {
                description = this.getSkipDescription();
                break;
            } case NOWPLAYING: {
                description = this.getNowPlayingDescription();
                break;
            } case PAUSE: {
                description = this.getPauseDescription();
                break;
            } case RESUME: {
                description = this.getResumeDescription();
                break;
            } case SHUFFLE: {
                description = this.getShuffleDescription();
                break;
            } case LIST: {
                description = this.getQueueDescription();
                break;
            } default:
                description = "No such command function found.";
                break;
            }
        return description;
    }

    public final String getPlayDescription() {
        return "Queues a song (must provide source).";
    }

    public final String getSkipDescription() {
        return "Skips the current song.";
    }

    public final String getNowPlayingDescription() {
        return "Shows the current song playing.";
    }

    public final String getPauseDescription() {
        return "Pauses the current song.";
    }

    public final String getResumeDescription() {
        return "Resume playback.";
    }

    public final String getShuffleDescription() {
        return "Shuffles the auto-playlist.";
    }

    public final String getQueueDescription() {
        return "Lists all the songs that are currently in the queue.";
    }

}
