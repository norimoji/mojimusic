package modules;

import listeners.AudioListener;
import listeners.BotListener;
import listeners.ListenerProvider;
import lombok.val;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

/**
 * Created by Huy on 21/01/2017.
 */
public final class ListenerAdapterModule extends CommonModule {

    @Override
    protected void configure() {
        val listenerBinder = newSetBinder(binder(), ListenerAdapter.class);
        listenerBinder.addBinding().to(BotListener.class);
        listenerBinder.addBinding().to(AudioListener.class);

        bind(ListenerProvider.class);
    }

}
