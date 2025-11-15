package com.github.doda2025_team4.lib;

import java.util.Random;


public class GoodSentenceGenerator
{
    private final String[] sentences = {
        "Team 4 is the best!",
        "Team 4 knows how to do it.",
        "You wish you were a member of team 4...",
        "Programming is easy for team 4.",
        "Team 4 can do these assignments in its sleep.",
        "Team 4 will take care of you. Team 4 is kind."
    };

    private final Random randomGenerator;

    public GoodSentenceGenerator() {
        this(new Random());
    }

    GoodSentenceGenerator(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public String generateSentence() {
        return sentences[this.randomGenerator.nextInt(this.sentences.length)];
    }
}
