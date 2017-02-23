package loader;

import lombok.Getter;
import lombok.val;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Huy on 21/01/2017.
 */
@Getter
public enum PropertiesLoader {

    PROPERTIES;

    final Properties properties;

    PropertiesLoader() {
        this.properties = new Properties();
        this.loadPropertiesFile();
    }

    private void loadPropertiesFile() {
        try {
            val inputStream = ClassLoader.getSystemResourceAsStream("config/options.properties");
            this.properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
