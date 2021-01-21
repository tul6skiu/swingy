package mswingy.utils;

import mswingy.model.Mode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigGame {
    private static final Properties config = new Properties();
    private static int villainsCount;
    private static int maxLvl;
    private static Mode mode;

    static {
        try {
            File configFile = findConfigFile();
            InputStream input1 = new FileInputStream(configFile);
            config.load(input1);
            villainsCount = Integer.parseInt(config.getProperty("villain.amount"));
            if (villainsCount <= 0) // todo: написать свои exception
                System.out.println("No villains!");
            if (villainsCount >= 50)
                System.out.println("To much villains!");
            maxLvl = Integer.parseInt(config.getProperty("maxLvl"));
            if (maxLvl < 0)
                System.out.println("Maximum level cannot be negative!");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private static File findConfigFile() {
        String dir = System.getProperty("user.dir");
        if ("target".equals(dir.substring(dir.lastIndexOf('/') + 1))) {
            return new File("classes/application.properties");
        } else {
            return new File("target/classes/application.properties");
        }
    }


    public static Properties getConfig() {
        return config;
    }

    public static int getMaxLvl() {
        return maxLvl;
    }

    public static Mode getMode() {
        return mode;
    }

    public static void setMode(Mode currentMode) {
        ConfigGame.mode = currentMode;
    }
}
