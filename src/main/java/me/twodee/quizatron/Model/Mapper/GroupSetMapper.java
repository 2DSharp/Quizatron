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
import java.util.stream.Stream;

public class GroupSetMapper implements CSVReaderMapper<Group>
{
    private CSVManager csvManager;
    private String file;
    private Iterator<CSVRecord> iterator;
    private List<Group> groups;

    /**
     * Initialize the grouped question set mapper
     * @param csvManager
     * @param file
     * @throws IOException
     */
    @Inject
    public GroupSetMapper(CSVManager csvManager, String file) throws IOException
    {
        this.csvManager = csvManager;
        this.file = file;
        init();
    }
    /**
     * File getter
     * @return file
     */
    public String getFile()
    {
        return file;
    }

    /**
     * Fetch the group and store it in a target group
     * @param group
     * @throws NonExistentRecordException
     */
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
    /**
     * Get the csv mapper initialized
     * Store the records in memory.
     * @throws IOException
     */
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
    /**
     * Create a new group based on a csv record, value object store
     * @param csvRecord
     * @return group
     */
    private Group loadGroupFromRecord(CSVRecord csvRecord)
    {
        String fileName = csvRecord.get("File");
        String image = csvRecord.get("Image");
        String answer = csvRecord.get("Answer");
        String blockFile = csvRecord.get("BlockFile");
        return new Group(fileName, image, answer, blockFile);
    }
    /**
     * Gets total number of groups
     * @return
     */
    public int getTotalRecords()
    {
        return groups.size();
    }
    /**
     * For FP magic
     * @return
     */
    public Stream<Group> getStream()
    {
        return groups.stream();
    }
}
