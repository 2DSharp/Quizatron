package me.twodee.quizatron.Factory;

import me.twodee.quizatron.Component.CSVManager;
import me.twodee.quizatron.Model.Mapper.GroupSetMapper;
import me.twodee.quizatron.Model.Mapper.StandardQSetMapper;
import me.twodee.quizatron.Model.Service.RoundService.GroupQSet;
import me.twodee.quizatron.Model.Service.RoundService.StandardQSet;

import javax.inject.Inject;
import java.io.IOException;

public class GroupQSetFactory
{
    private final CSVManager csvManager;
    private StandardQSetFactory standardQSetFactory;
    @Inject
    public GroupQSetFactory(CSVManager csvManager, StandardQSetFactory standardQSetFactory)
    {
        this.csvManager = csvManager;
        this.standardQSetFactory = standardQSetFactory;
    }
    public GroupQSet create(String file) throws IOException
    {
        GroupSetMapper groupSetMapper = new GroupSetMapper(csvManager, file);
        return new GroupQSet(groupSetMapper, standardQSetFactory);
    }
}
