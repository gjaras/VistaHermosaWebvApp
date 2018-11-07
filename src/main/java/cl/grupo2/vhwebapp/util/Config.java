package cl.grupo2.vhwebapp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gjaras.util.exceptions.MissingConfigurationException;
import java.net.URL;
import javax.servlet.ServletContext;



public class Config {

    private static Config INSTANCE = null;

    private static final Logger LOG = LoggerFactory.getLogger(Config.class);

    private final Properties p;

    private Config() {
        this.p = getProperties();
    }

    public Properties getProperties() {
        File config = new File("config.properties");

        try {
            LOG.debug("Cargando archivo de configuracion...");
            Properties prop = new Properties();

            prop.load(new FileInputStream(config));

            return prop;
        } catch (IOException e) {
            if (!config.exists()) {
                LOG.error(
                        "Archivo de configuracion ({}) no existe.",
                        config.getAbsolutePath()
                );
            } else {
                LOG.error(
                        "Error al leer el archivo de configuracion: {}",
                        e.getMessage()
                );
            }
            throw new RuntimeException(e);
        }
    }

    private String getProperty(final String key) {
        return p.getProperty(key);
    }

    public static String get(
            final String key
    ) throws MissingConfigurationException {
        if (INSTANCE == null) {
            INSTANCE = new Config();
        }
        String result = INSTANCE.getProperty(key);
        if (result == null) {
            throw new MissingConfigurationException(key);
        } else {
            return result.trim();
        }
    }

    public static String getOrNull(
            final String key
    ) {
        if (INSTANCE == null) {
            INSTANCE = new Config();
        }
        return INSTANCE.getProperty(key == null ? key : key.trim());
    }
}
