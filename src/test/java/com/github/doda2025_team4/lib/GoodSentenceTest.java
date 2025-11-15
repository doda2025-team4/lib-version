package com.github.doda2025_team4.lib;

import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple GoodSentence.
 */
public class GoodSentenceTest
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
        
        GoodSentence goodSentence = new GoodSentence(mockRandom);
        goodSentence.generateSentence();
        
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
        GoodSentence goodSentence = new GoodSentence(mockRandom);
        
        assertEquals("Programming is easy for team 4.", goodSentence.generateSentence());
    }
}
