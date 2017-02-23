package bot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import listeners.ListenerProvider;
import modules.annotations.DiscordAccountType;
import modules.annotations.Token;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

@Singleton
public final class JDABuilderImpl extends JDABuilder {

    @Inject
    public JDABuilderImpl(@DiscordAccountType AccountType accountType, @Token String token, ListenerProvider listeners) {
        super(accountType);
        super.setToken(token);
        super.setAutoReconnect(true);
        super.addListener(listeners.get().toArray());
    }

}
