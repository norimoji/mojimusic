package audioplayer;

import audioplayer.factory.MusicManagerFactory;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.val;
import net.dv8tion.jda.core.entities.Guild;

import java.util.HashMap;
import java.util.Map;

@Singleton
public final class MusicManagerHandler implements AbstractMusicManagerHandler {

    private final MusicManager musicManager;
    private final MusicManagerFactory musicManagerFactory;

    private final Map<Long, MusicManager> musicManagers;

    @Inject
    public MusicManagerHandler(MusicManager musicManager, MusicManagerFactory musicManagerFactory) {
        this.musicManager = musicManager;
        this.musicManagerFactory = musicManagerFactory;
        this.musicManagers = new HashMap<>();
    }

    @Override
//    public final synchronized MusicManager getMusicManager(Guild guild) {
//        val guildId = Long.parseLong(guild.getId());
//        MusicManager guildAudioManager = this.musicManagers.computeIfAbsent(guildId, k -> this.musicManager);
//        guild.getAudioManager().setSendingHandler(guildAudioManager.getAudioSendHandler());
//        return guildAudioManager;
//    }

    public final synchronized MusicManager getMusicManager(Guild guild) {
        val guildId = Long.parseLong(guild.getId());
        val tempMusicManager = this.musicManagers.get(guildId);

        if (tempMusicManager == null) {
            val musicManager = this.musicManagerFactory.get();
            this.musicManagers.put(guildId, musicManager);
        }
        guild.getAudioManager().setSendingHandler(this.musicManagers.get(guildId).getAudioSendHandler());
        return this.musicManagers.get(guildId);
    }
}
