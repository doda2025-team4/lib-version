package com.github.doda2025_team4.lib;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileReader;
import java.io.IOException;

/**
 * Unit test for simple VersionUtil.
 */
public class VersionUtilTest
{
    private String pomVersion;

    @BeforeEach
    void setUp() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        pomVersion = model.getVersion();
    }

    /**
     * Test getVersion does not throw and returns correct version.
     */
    @Test
    void testGetVersion()
    {
        VersionUtil versionUtil = new VersionUtil();
        String version = assertDoesNotThrow(() -> {
            return versionUtil.getVersion();
        });
        assertEquals(pomVersion, version);
    }
}
