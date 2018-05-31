package me.twodee.quizatron.Model.Service;

import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Exception.NoQuestionLeftException;
import me.twodee.quizatron.Model.Mapper.QuestionMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class QuestionSetServiceTest {

    QuestionSetService questionSetService;

    @Before
    public void setUp() {

        questionSetService = new QuestionSetService(new QuestionMapper());
    }
    @Test
    public void loadQSetTest() throws URISyntaxException, IOException {

        Path file = Paths.get(this.getClass().getResource("/rounds/qset1.csv").toURI().getPath());
        questionSetService.loadSet(file);
        assertTrue(questionSetService.isQSetLoaded());
    }

    @Test
    public void loadQSetAsListTest() throws URISyntaxException, IOException {

        Path file = Paths.get(this.getClass().getResource("/rounds/qset1.csv").toURI().getPath());
        questionSetService.loadSet(file);
        questionSetService.toList();
        Question question = questionSetService.getQuestion(3);
        assertEquals("Just an alt.", question.getDescription());
    }

    @Test
    public void nextLoadsQuestionTest() throws IOException, URISyntaxException, NoQuestionLeftException {

        Path file = Paths.get(this.getClass().getResource("/rounds/qset1.csv").toURI().getPath());
        questionSetService.loadSet(file);
        assertNotNull(questionSetService.nextQuestion());
    }

    @Test
    public void firstQuestionValueTest() throws IOException, URISyntaxException {

        Path file = Paths.get(this.getClass().getResource("/rounds/qset1.csv").toURI().getPath());
        questionSetService.loadSet(file);
        assertEquals("What is your name?", questionSetService.getQuestion().getTitle());
    }
    @Test (expected = NoQuestionLeftException.class)
    public void nextNonExistentTest() throws IOException, URISyntaxException, NoQuestionLeftException {

        Path file = Paths.get(this.getClass().getResource("/rounds/qset1.csv").toURI().getPath());
        questionSetService.loadSet(file);
        questionSetService.nextQuestion();
        questionSetService.nextQuestion();
        questionSetService.nextQuestion();

    }

    @Test
    public void nextValueTest() throws IOException, URISyntaxException, NoQuestionLeftException {

        Path file = Paths.get(this.getClass().getResource("/rounds/qset1.csv").toURI().getPath());
        questionSetService.loadSet(file);
        Question question = questionSetService.nextQuestion();
        assertEquals("Good.", question.getAnswer());

    }

}
