package me.twodee.quizatron.Model.Service;

import me.twodee.quizatron.Component.CSVManager;
import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Mapper.StandardQSetMapper;
import me.twodee.quizatron.Model.Service.RoundService.StandardQSet;
import org.junit.Before;
import org.junit.Test;
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
        StandardQSetMapper standardQSetMapper = new StandardQSetMapper(csvManager, file);

        StandardQSet standardQSet = new StandardQSet(standardQSetMapper);
        standardQSet.toStart();
        Question question = standardQSet.fetch();
        String result = question.getTitle();

        assertThat(result, is("What is your name?"));
    }

    @Test(expected = NonExistentRecordException.class)
    public void getNonExistentQuestionTest() throws IOException, NonExistentRecordException
    {
        CSVManager csvManager = new CSVManager();
        StandardQSetMapper standardQSetMapper = new StandardQSetMapper(csvManager, file);

        StandardQSet  standardQSet = new StandardQSet(standardQSetMapper);
        standardQSet.fetch(10);
    }

    @Test
    public void getQuestionOnIndex() throws IOException, NonExistentRecordException
    {
        CSVManager csvManager = new CSVManager();
        StandardQSetMapper standardQSetMapper = new StandardQSetMapper(csvManager, file);
        StandardQSet  standardQSet = new StandardQSet(standardQSetMapper);

        String result = standardQSet.fetch(2).getAnswer();

        assertThat(result, is("Good."));
    }

    @Test
    public void iterationTest() throws IOException, NonExistentRecordException
    {
        CSVManager csvManager = new CSVManager();
        StandardQSetMapper standardQSetMapper = new StandardQSetMapper(csvManager, file);

        StandardQSet  standardQSet = new StandardQSet(standardQSetMapper);

        int i = 1;
        while (standardQSet.hasNext()) {
            standardQSet.next();
            Question question = standardQSet.fetch();
            System.out.println(i++ + " " + question.getTitle() + " " + question.getAnswer());
        }

        assertThat(i, is(4));
    }
}
