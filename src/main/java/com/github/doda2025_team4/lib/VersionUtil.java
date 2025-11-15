package com.github.doda2025_team4.lib;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;


public class VersionUtil
{
    public String getVersion() throws IOException {
        final InputStream in = getClass().getClassLoader().getResourceAsStream("lib.properties");
        if (in == null) {
            throw new IOException("properties not found!");
        }

        final Properties libProps = new Properties();
        libProps.load(in);

        return libProps.getProperty("version");
    }
}
