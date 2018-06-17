package me.twodee.quizatron.Model.Service;

import me.twodee.quizatron.Model.Service.RoundService.GroupQSet;
import me.twodee.quizatron.Model.Service.RoundService.StandardQSet;
import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;

public class GroupQuestionServiceTest
{
    @Before
    public void beforeEachTest() throws URISyntaxException
    {

    }

    @Test
    public void getQuestionSet()
    {
        GroupQSet groupQSet = new GroupQSet();
        StandardQSet standardQSet = groupQSet.getQSet();

    }
}
