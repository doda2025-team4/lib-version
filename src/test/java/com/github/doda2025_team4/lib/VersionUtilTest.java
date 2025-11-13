package com.github.doda2025_team4.lib;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
public class VersionUtilTest
{
    /**
     * Rigourous Test :-)
     */
    @Test
    public void testGetVersion()
    {
        VersionUtil versionUtil = new VersionUtil();
        String version = assertDoesNotThrow(() -> {
            return versionUtil.getVersion();
        });
        assertEquals("1.0-SNAPSHOT", version);
    }
}
