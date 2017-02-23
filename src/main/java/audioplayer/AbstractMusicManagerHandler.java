package audioplayer;

import net.dv8tion.jda.core.entities.Guild;

/**
 * Created by Phong on 09/02/2017.
 */
public interface AbstractMusicManagerHandler {

    MusicManager getMusicManager(Guild guild);

}

