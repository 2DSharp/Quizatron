package me.twodee.quizatron.Model.Mapper;

import me.twodee.quizatron.Component.CSVManager;
import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class StandardSetMapperTest
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
        StandardSetMapper standardSetMapper = new StandardSetMapper(csvManager, filePath);
        Question question = new Question();

        question.setIndex(0);
        standardSetMapper.fetch(question);

        assertThat(question.getTitle(), is("What is your name?"));
    }

    @Test
    public void totalQuestionsCheck() throws IOException
    {
        CSVManager csvManager = new CSVManager();
        StandardSetMapper standardSetMapper = new StandardSetMapper(csvManager, filePath);

        int result = standardSetMapper.getTotalRecords();

        assertThat(result, is(3));
    }
}