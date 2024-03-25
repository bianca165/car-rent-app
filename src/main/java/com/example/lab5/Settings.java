package com.example.lab5;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fileInputStream = new FileInputStream("C:\\Users\\curca\\Desktop\\sem 1\\java\\lab5\\src\\main\\java\\settings.properties")) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getRepoType() {
        return properties.getProperty("Repository");
    }
}
