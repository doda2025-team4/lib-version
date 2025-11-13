package com.github.doda2025_team4.lib;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        App app = new App();
        try {
            String libVersion = app.getVersion();
            System.out.println("I am version: %s".formatted(libVersion));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getVersion() throws IOException {
        InputStream in = getClass().getClassLoader().getResourceAsStream("lib.properties");
        if (in == null) {
            throw new IOException("properties not found!");
        }

        Properties libProps = new Properties();
        libProps.load(in);

        return libProps.getProperty("version");
    }
}
