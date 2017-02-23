package enums;

/**
 * Created by Huy on 21/01/2017.
 */
public enum GeneralKeys implements Description<GeneralKeys, String> {

    HELP,
    JOIN,
    LEAVE,
    ID,
    AVATAR,
    JOINSERVER,
    SHUTDOWN;

    @Override
    public final String toString() {
        return name().toLowerCase();
    }

    @Override
    public final String getDescription(GeneralKeys inputCommand) {
        String description;
        switch (inputCommand) {
            case JOIN: {
                description = this.getJoinDescription();
                break;
            } case LEAVE: {
                description = this.getLeaveDescription();
                break;
            }case ID: {
                description = this.getIdDescription();
                break;
            } case AVATAR: {
                description = this.getAvatarDescription();
                break;
            } case JOINSERVER: {
                description = this.getJoinServerDescription();
                break;
            } case SHUTDOWN: {
                description = this.getShutdownDescription();
                break;
            } default:
                description = "No such command function found.";
                break;
        }
        return description;
    }

    public final String getJoinDescription() {
        return "Joins the voice channel you are in.";
    }

    public final String getLeaveDescription() {
        return "Leaves the voice channel it is currently in.";
    }

    public final String getIdDescription() {
        return "Get your Discord ID.";
    }

    public final String getAvatarDescription() {
        return "Get your Discord profile picture.";
    }

    public final String getJoinServerDescription() {
        return "A URL link to add the bot to another server.";
    }

    public final String getShutdownDescription() {
        return "Terminates the bot immediately.";
    }
}
