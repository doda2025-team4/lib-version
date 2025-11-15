package com.github.doda2025_team4.lib;

import java.io.InputStream;
import java.io.IOException;
import java.lang.RuntimeException;
import java.util.Properties;


public class VersionUtil
{
    private Properties libProps;

    public VersionUtil() {
        InputStream in = getClass().getClassLoader().getResourceAsStream("lib.properties");
        if (in == null) {
            throw new RuntimeException("Could not find library properties.");
        }

        libProps = new Properties();
        try {
            libProps.load(in);
        } catch(IOException e) {
            throw new RuntimeException("Could not find library properties.");
        }
    }

    public String getVersion() {
        return libProps.getProperty("version");
    }

    public String getName() {
        return libProps.getProperty("name");
    }
}
