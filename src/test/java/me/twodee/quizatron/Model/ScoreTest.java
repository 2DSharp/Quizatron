package me.twodee.quizatron.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreTest {

    Score score;
    @Before
    public void setUp() throws Exception {
        score = new Score(new Person());
    }

    @Test
    public void testGetScore() {
        Assert.assertEquals("Dedipyaman 10", score.getScore());
    }

}