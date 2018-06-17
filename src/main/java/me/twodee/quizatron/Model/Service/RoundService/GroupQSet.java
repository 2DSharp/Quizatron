package me.twodee.quizatron.Model.Service.RoundService;

import me.twodee.quizatron.Factory.StandardQSetFactory;
import me.twodee.quizatron.Model.Contract.IQuestionSetService;
import me.twodee.quizatron.Model.Entity.Group;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Exception.UninitializedGroupException;
import me.twodee.quizatron.Model.Mapper.GroupSetMapper;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Paths;

public class GroupQSet extends QuestionSetService implements IQuestionSetService<Group>
{
    private GroupSetMapper groupSetMapper;
    private StandardQSetFactory factory;
    private Group group;

    @Inject
    public GroupQSet(GroupSetMapper groupSetMapper, StandardQSetFactory standardQSetFactory)
    {
        this.groupSetMapper = groupSetMapper;
        this.factory = standardQSetFactory;
    }
    public Group fetch() throws NonExistentRecordException
    {
        return fetch(curr + 1);
    }

    public Group fetch(int index) throws NonExistentRecordException
    {
        group = new Group();
        curr = index - 1;
        group.setIndex(curr);
        groupSetMapper.fetch(group);
        return group;
    }

    public StandardQSet getService() throws IOException, UninitializedGroupException
    {
        if (group != null) {
            String dir = getRelativeDir();
            return factory.create(dir + group.getFile());
        }
        throw new UninitializedGroupException();
    }

    public boolean hasNext()
    {
        return curr < groupSetMapper.getTotalRecords() - 1;
    }

    private String getRelativeDir()
    {
        String dir = Paths.get(groupSetMapper.getFile()).getParent().toAbsolutePath().toString();
        dir = dir + "/";
        return dir;
    }
}
