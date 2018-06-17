package me.twodee.quizatron.Model.Mapper;

import me.twodee.quizatron.Component.CSVManager;
import me.twodee.quizatron.Model.Contract.CSVReaderMapper;
import me.twodee.quizatron.Model.Contract.ISequenceMapper;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Entity.Sequence;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;

import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CSVSequenceMapper implements ISequenceMapper, CSVReaderMapper<Sequence>
{
    private Configuration configuration;
    private Iterator<CSVRecord> iterator;
    private List<Sequence> sequences;
    private CSVManager csvManager;

    public CSVSequenceMapper(CSVManager csvManager, Configuration configuration) throws IOException
    {
        this.configuration = configuration;
        this.csvManager = csvManager;
        // DO NOT LAZY LOAD THIS
        init();
    }
    /**
     * Loads the CSV file data into memory for faster access times during sequence load
     * Enables random access, unlike the regular CSV streaming would provide sequential one-time access.
     * Do not lazy load this: Loading while instantiation is affordable due to loading screen.
     * @throws IOException
     */
    private void init() throws IOException
    {
        iterator = csvManager.load(configuration.getSequence());
        sequences = new ArrayList<>();

        while (iterator.hasNext()) {
            CSVRecord record = iterator.next();
            Sequence sequence = loadSequenceFromRecord(record);
            sequences.add(sequence);
        }
    }

    /**
     * Creates a new sequence from a CSVRecord
     * @param record {@link CSVRecord}
     * @return Sequence based on the record data
     */
    private Sequence loadSequenceFromRecord(CSVRecord record)
    {
        int id = Integer.parseInt(record.get("ID"));
        String name = record.get("Name");
        String type = record.get("Type");
        String file = record.get("File");
        String design = record.get("Design");
        String intro = record.get("Intro");

        return new Sequence(id, name, type, file, design, intro);
    }

    @Override
    public void fetch(Sequence sequence) throws NonExistentRecordException
    {
        try {
            Sequence target = sequences.get(sequence.getIndex());
            sequence.copySequence(target);
        }
        catch (IndexOutOfBoundsException e){
            throw new NonExistentRecordException();
        }
    }

    @Override
    public int getTotalRecords()
    {
        return sequences.size();
    }
}
