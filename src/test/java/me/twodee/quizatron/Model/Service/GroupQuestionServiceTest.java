package me.twodee.quizatron.Model.Service;

import me.twodee.quizatron.Component.CSVManager;
import me.twodee.quizatron.Factory.StandardQSetFactory;
import me.twodee.quizatron.Model.Entity.Group;
import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Exception.UninitializedGroupException;
import me.twodee.quizatron.Model.Mapper.GroupSetMapper;
import me.twodee.quizatron.Model.Service.RoundService.GroupQSet;
import me.twodee.quizatron.Model.Service.RoundService.StandardQSet;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GroupQuestionServiceTest
{
    String file;

    @Before
    public void beforeEachTest() throws URISyntaxException
    {
        file = this.getClass().getResource("/rounds/groupset.csv").toURI().getPath();
    }

    @Test
    public void getFirstQuestionSet() throws NonExistentRecordException, IOException, UninitializedGroupException
    {
        CSVManager csvManager = new CSVManager();
        GroupSetMapper setMapper = new GroupSetMapper(csvManager, file);
        StandardQSetFactory standardQSetFactory = new StandardQSetFactory(csvManager);
        GroupQSet groupQSet = new GroupQSet(setMapper, standardQSetFactory);

        groupQSet.toStart();
        groupQSet.fetch();

        StandardQSet standardQSet = groupQSet.getService();
        standardQSet.toStart();
        Question question = standardQSet.fetch();

        assertThat(question.getTitle(), is("What is your name?"));
    }

    @Test
    public void getSecondQuestionSet() throws NonExistentRecordException, IOException, UninitializedGroupException
    {
        CSVManager csvManager = new CSVManager();
        GroupSetMapper setMapper = new GroupSetMapper(csvManager, file);
        StandardQSetFactory standardQSetFactory = new StandardQSetFactory(csvManager);
        GroupQSet groupQSet = new GroupQSet(setMapper, standardQSetFactory);

        groupQSet.toStart();
        groupQSet.next();
        groupQSet.fetch();

        StandardQSet standardQSet = groupQSet.getService();
        standardQSet.toStart();
        Question question = standardQSet.fetch();

        String result = question.getAnswer();
        assertThat(result, is("I am 18"));
    }

    @Test
    public void getQuestionSetByIndex() throws NonExistentRecordException, IOException
    {
        CSVManager csvManager = new CSVManager();
        GroupSetMapper setMapper = new GroupSetMapper(csvManager, file);
        StandardQSetFactory standardQSetFactory = new StandardQSetFactory(csvManager);
        GroupQSet groupQSet = new GroupQSet(setMapper, standardQSetFactory);

        Group group = groupQSet.fetch(2);

        String result = group.getFile();
        assertThat(result, is("qset2.csv"));
    }

    @Test (expected = NonExistentRecordException.class)
    public void invalidQSetRequest() throws NonExistentRecordException, IOException
    {
        CSVManager csvManager = new CSVManager();
        GroupSetMapper setMapper = new GroupSetMapper(csvManager, file);
        StandardQSetFactory standardQSetFactory = new StandardQSetFactory(csvManager);
        GroupQSet groupQSet = new GroupQSet(setMapper, standardQSetFactory);

        groupQSet.fetch(5);
    }

    @Test
    public void deepIterationTest() throws NonExistentRecordException, IOException, UninitializedGroupException
    {
        CSVManager csvManager = new CSVManager();
        GroupSetMapper setMapper = new GroupSetMapper(csvManager, file);
        StandardQSetFactory standardQSetFactory = new StandardQSetFactory(csvManager);

        GroupQSet groupQSet = new GroupQSet(setMapper, standardQSetFactory);

        int i = 0;
        while (groupQSet.hasNext()) {
            groupQSet.next();
            Group group = groupQSet.fetch();
            System.out.println(group.getFile());

            StandardQSet qSet = groupQSet.getService();
            while (qSet.hasNext()) {
                qSet.next();
                Question question = qSet.fetch();
                System.out.println(question.getIndex()+ " " + question.getTitle() + " " + question.getAnswer());
                i++;
            }
        }

        assertThat(i, is(5));
    }
}