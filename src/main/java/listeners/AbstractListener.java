package listeners;

import lombok.AccessLevel;
import lombok.Getter;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by Huy on 21/01/2017.
 */
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractListener extends ListenerAdapter {

    private final String prefix;

    AbstractListener(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);
    }

}
