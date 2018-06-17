package me.twodee.quizatron.Model.Mapper;

import me.twodee.quizatron.Component.CSVManager;
import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StandardQSetMapperTest
{
    String filePath;

    @Before
    public void beforeEachTest() throws URISyntaxException
    {
        filePath = Paths.get(this.getClass().getResource("/rounds/qset1.csv").toURI())
                                   .toAbsolutePath()
                                   .toString();

    }

    @Test
    public void fetchRefCheck() throws IOException, NonExistentRecordException
    {
        CSVManager csvManager = new CSVManager();
        StandardQSetMapper standardQSetMapper = new StandardQSetMapper(csvManager, filePath);
        Question question = new Question();

        question.setIndex(0);
        standardQSetMapper.fetch(question);

        assertThat(question.getTitle(), is("What is your name?"));
    }

    @Test
    public void totalQuestionsCheck() throws IOException
    {
        CSVManager csvManager = new CSVManager();
        StandardQSetMapper standardQSetMapper = new StandardQSetMapper(csvManager, filePath);

        int result = standardQSetMapper.getTotalRecords();

        assertThat(result, is(3));
    }
}