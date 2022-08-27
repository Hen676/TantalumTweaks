package hen676.dragonlite.config;

import hen676.dragonlite.Henlper;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties props = new Properties();
    private static final File file = new File(FabricLoader.getInstance().getConfigDir().resolve(Henlper.MOD_ID + ".properties").toString());
    private static boolean toggle = false;

    public static void init() {
        try {
            if (!file.exists())
                createOrSaveConfig();
            if (!toggle)
                loadConfig();
        } catch (Exception e) {
            throw new RuntimeException("Error loading configuration: " + e, e);
        }
    }

    private static void catchMethod(Exception e, String error) {
        Henlper.LOGGER.error(error);
        Henlper.LOGGER.trace(e);
        toggle = true;
    }

    private static void loadConfig() {
        try {
            try (FileInputStream propStream = new FileInputStream(file)) {
                props.load(propStream);
            }
            for (Field field : Config.class.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()))
                    field.set(null, getValue(field.getName(), field.getType()));
            }
        } catch (Exception e) {
            catchMethod(e, "Failed to load henlper Config!");
        }
    }

    public static void createOrSaveConfig() {
        try {
            for (Field field : Config.class.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()))
                    props.setProperty(field.getName(), field.get(field.getType()).toString());
            }
            try (FileOutputStream propStream = new FileOutputStream(file)) {
                props.store(propStream, "Properties of henlper");
            }
        } catch (Exception e) {
            catchMethod(e, "Failed to generate henlper Config!");
        }
    }

    private static Object getValue(String key, Class<?> type) {
        String value = props.getProperty(key);
        if (value == null)
            throw new IllegalArgumentException("Missing properties value: " + key);
        if (type == String.class)
            return value;
        if (type == boolean.class)
            return Boolean.parseBoolean(value);
        if (type == int.class)
            return Integer.parseInt(value);
        if (type == double.class)
            return Double.parseDouble(value);
        if (type == float.class)
            return Float.parseFloat(value);
        throw new IllegalArgumentException("Unknown properties value type: " + type.getName());
    }
}
