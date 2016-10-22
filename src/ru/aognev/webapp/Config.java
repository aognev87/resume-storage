package ru.aognev.webapp;

import java.io.*;
import java.util.Properties;

/**
 * Created by aognev on 21.10.2016.
 */
public class Config {
    private static final File PROPS = new File ("config\\resumes.properties");
    private static final Config INSTANCE = new Config();

    private Properties props = new Properties();
    private File storageDir;
    private String creds[];

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));

            creds = new String[]{
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
            };
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public String[] getCreds() {
        return creds;
    }
}
