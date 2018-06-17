package me.twodee.quizatron.Model.Service;

import me.twodee.quizatron.Component.CSVManager;
import me.twodee.quizatron.Factory.StandardQSetFactory;
import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Mapper.GroupSetMapper;
import me.twodee.quizatron.Model.Service.RoundService.GroupQSet;
import me.twodee.quizatron.Model.Service.RoundService.StandardQSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class GroupQuestionServiceTest
{
    String file;
    @Mock
    QuizDataService quizDataService;

    @Before
    public void beforeEachTest() throws URISyntaxException
    {
        MockitoAnnotations.initMocks(this);
        file = this.getClass().getResource("/rounds/groupset.csv").toURI().getPath();

        String homeDir = this.getClass().getResource("/rounds/").toURI().getPath();
        when(quizDataService.getInitialDirectory()).thenReturn(Paths.get(homeDir));
    }

    @Test
    public void getQuestionSet() throws NonExistentRecordException, IOException
    {
        CSVManager csvManager = new CSVManager();
        GroupSetMapper setMapper = new GroupSetMapper(csvManager, file);
        StandardQSetFactory standardQSetFactory = new StandardQSetFactory(csvManager, quizDataService);

        GroupQSet groupQSet = new GroupQSet(setMapper, standardQSetFactory);
        StandardQSet standardQSet = groupQSet.getQSet();

        Question question = standardQSet.fetchQuestion();
        assertThat(question.getTitle(), is("What is your name?"));
    }
}
