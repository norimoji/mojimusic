package modules;

import com.google.inject.AbstractModule;
import lombok.val;
import modules.annotations.DiscordAccountType;
import modules.annotations.Prefix;
import modules.annotations.Token;

import static com.google.inject.name.Names.bindProperties;
import static loader.PropertiesLoader.PROPERTIES;
import static net.dv8tion.jda.core.AccountType.BOT;

/**
 * Created by Huy on 21/01/2017.
 */
abstract class CommonModule extends AbstractModule {

    @Override
    protected void configure() {
        val property = PROPERTIES.getProperties();
        bindProperties(binder(), property);

        bindConstant().annotatedWith(DiscordAccountType.class).to(BOT);
        bindConstant().annotatedWith(Token.class).to(property.getProperty("Token", ""));
        bindConstant().annotatedWith(Prefix.class).to(property.getProperty("CommandPrefix", "!"));
    }

}
