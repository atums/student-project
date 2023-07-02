package com.apys.learning.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

public class Config {
    public static final String DB_URL = "db.url";
    public static final String DB_LOGIN = "db.login";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_LIMIT = "db.limit";
    public static final String CR_URL = "cr.url";

    private static Properties prop = new Properties();

    public static String getProperties(String name) {
        if(prop.isEmpty()) {
            //TODO Исправить в pom.xml
            try (FileReader fr = new FileReader("src/main/resources/config.properties")){
                prop.load(fr);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return prop.getProperty(name);
    }
}
