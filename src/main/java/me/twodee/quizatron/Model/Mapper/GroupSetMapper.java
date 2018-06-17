package me.twodee.quizatron.Model.Mapper;

import me.twodee.quizatron.Component.CSVManager;
import me.twodee.quizatron.Model.Contract.CSVReaderMapper;
import me.twodee.quizatron.Model.Entity.Group;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import org.apache.commons.csv.CSVRecord;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GroupSetMapper implements CSVReaderMapper<Group>
{
    private CSVManager csvManager;
    private String file;
    private Iterator<CSVRecord> iterator;
    private List<Group> groups;

    @Inject
    public GroupSetMapper(CSVManager csvManager, String file) throws IOException
    {
        this.csvManager = csvManager;
        this.file = file;
        init();
    }

    public String getFile()
    {
        return file;
    }
    public void fetch(Group group) throws NonExistentRecordException
    {
        try {
            Group target = groups.get(group.getIndex());
            group.setGroup(target);
        }
        catch (IndexOutOfBoundsException e) {
            throw new NonExistentRecordException();
        }
    }

    private void init() throws IOException
    {
        iterator = csvManager.load(file);
        groups = new ArrayList<>();
        int index = 0;

        while (iterator.hasNext()) {
            CSVRecord record = iterator.next();
            Group group = loadGroupFromRecord(record);
            group.setIndex(index++);
            groups.add(group);
        }
    }

    private Group loadGroupFromRecord(CSVRecord csvRecord)
    {
        String fileName = csvRecord.get("File");
        return new Group(fileName);
    }

    public int getTotalRecords()
    {
        return groups.size();
    }
}
