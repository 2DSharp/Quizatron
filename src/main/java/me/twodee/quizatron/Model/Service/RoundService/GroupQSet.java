package me.twodee.quizatron.Model.Service.RoundService;

import me.twodee.quizatron.Factory.StandardQSetFactory;
import me.twodee.quizatron.Model.Entity.Group;
import me.twodee.quizatron.Model.Mapper.GroupSetMapper;

import javax.inject.Inject;
import java.io.IOException;

public class GroupQSet
{
    private GroupSetMapper groupSetMapper;
    private StandardQSetFactory factory;
    private int curr;

    @Inject
    public GroupQSet(GroupSetMapper groupSetMapper, StandardQSetFactory standardQSetFactory)
    {
        this.groupSetMapper = groupSetMapper;
        this.factory = standardQSetFactory;
    }
    public StandardQSet getQSet() throws IOException
    {
        Group group = new Group();
        group.setIndex(curr);
        groupSetMapper.fetch(group);
        return factory.create(group.getFile());
    }
}
