package listeners;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Set;

/**
 * Created by Huy on 30/01/2017.
 */
@Singleton
public final class ListenerProvider implements Provider<Set<ListenerAdapter>> {

    private final Set<ListenerAdapter> listenerBinder;

    @Inject
    public ListenerProvider(Set<ListenerAdapter> setBinder) {
        this.listenerBinder = setBinder;
    }

    @Override
    public Set<ListenerAdapter> get() {
        return this.listenerBinder;
    }

}
