package modules;

import commands.audio.AbstractAudioCommands;
import commands.audio.AudioCommands;
import commands.general.AbstractGeneralCommands;
import commands.general.GeneralCommands;

/**
 * Created by Huy on 21/01/2017.
 */
public final class CommandModule extends CommonModule {

    @Override
    protected void configure() {
        bind(AbstractGeneralCommands.class).to(GeneralCommands.class);
        bind(AbstractAudioCommands.class).to(AudioCommands.class);
    }

}
