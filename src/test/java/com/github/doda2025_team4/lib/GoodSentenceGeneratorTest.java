package com.github.doda2025_team4.lib;

import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple GoodSentenceGenerator.
 */
public class GoodSentenceGeneratorTest
{
    /**
     * Test getVersion does not throw and returns correct version.
     */
    @Test
    void testGenerateSentence_RandomBoundCorrect()
    {
        final int[] capturedBound = new int[1];
        
        Random mockRandom = new Random() {
            @Override
            public int nextInt(int bound) {
                capturedBound[0] = bound;
                return 0;
            }
        };
        
        GoodSentenceGenerator goodSentenceGenerator = new GoodSentenceGenerator(mockRandom);
        goodSentenceGenerator.generateSentence();
        
        assertEquals(6, capturedBound[0]);
    }

    @Test
    void testGenerateSentence_ReturnsExpectedSentence() {
        Random mockRandom = new Random() {
            @Override
            public int nextInt(int bound) {
                return 3;
            }
        };
        GoodSentenceGenerator goodSentenceGenerator = new GoodSentenceGenerator(mockRandom);
        
        assertEquals("Programming is easy for team 4.", goodSentenceGenerator.generateSentence());
    }
}
