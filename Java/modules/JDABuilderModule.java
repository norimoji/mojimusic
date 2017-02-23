package modules;

import bot.JDABuilderImpl;

/**
 * Created by Huy on 21/01/2017.
 */
public final class JDABuilderModule extends CommonModule {

    @Override
    protected void configure() {
        super.configure();

        install(new CommandModule());
        install(new ListenerAdapterModule());
        install(new AudioModule());

        bind(JDABuilderImpl.class);
    }

}
