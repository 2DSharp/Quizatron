package me.twodee.quizatron.Model.Service;

import me.twodee.quizatron.Component.CSVManager;
import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Mapper.StandardSetMapper;
import me.twodee.quizatron.Model.Service.RoundService.StandardQSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StandardQSetTest
{
    private String file;

    @Before
    public void setUp() throws URISyntaxException
    {
        MockitoAnnotations.initMocks(this);
        file = this.getClass().getResource("/rounds/qset1.csv").toURI().getPath();
    }

    @Test
    public void getFirstQuestionTest() throws IOException, NonExistentRecordException
    {
        CSVManager csvManager = new CSVManager();
        StandardSetMapper standardSetMapper = new StandardSetMapper(csvManager, file);

        StandardQSet standardQSet = new StandardQSet(standardSetMapper);

        Question question = standardQSet.getQuestion();
        String result = question.getTitle();

        assertThat(result, is("What is your name?"));
    }

    @Test(expected = NonExistentRecordException.class)
    public void getNonExistentQuestionTest() throws IOException, NonExistentRecordException
    {
        CSVManager csvManager = new CSVManager();
        StandardSetMapper standardSetMapper = new StandardSetMapper(csvManager, file);

        StandardQSet  standardQSet = new StandardQSet(standardSetMapper);
        standardQSet.getQuestion(10);
    }

    @Test
    public void getQuestionOnIndex() throws IOException, NonExistentRecordException
    {
        CSVManager csvManager = new CSVManager();
        StandardSetMapper standardSetMapper = new StandardSetMapper(csvManager, file);

        StandardQSet  standardQSet = new StandardQSet(standardSetMapper);
        String result = standardQSet.getQuestion(2).getAnswer();

        assertThat(result, is("Good."));
    }

    @Test
    public void iterationTest() throws IOException, NonExistentRecordException
    {
        CSVManager csvManager = new CSVManager();
        StandardSetMapper standardSetMapper = new StandardSetMapper(csvManager, file);

        StandardQSet  standardQSet = new StandardQSet(standardSetMapper);

        int i = 1;
        while (!standardQSet.hasEnded()) {
            Question question = standardQSet.getQuestion();
            System.out.println(i++ + " " + question.getTitle() + " " + question.getAnswer());
            standardQSet.nextQuestion();
        }

        assertThat(i, is(4));
    }
}
