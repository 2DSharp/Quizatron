package me.twodee.quizatron.Model.Mapper;

import me.twodee.quizatron.Component.CSVManager;
import me.twodee.quizatron.Model.Contract.CSVReaderMapper;
import me.twodee.quizatron.Model.Contract.IMapper;
import me.twodee.quizatron.Model.Entity.Sequence;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;

import org.apache.commons.csv.CSVRecord;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class CSVSequenceMapper implements IMapper<Sequence>, CSVReaderMapper<Sequence>
{
    private Iterator<CSVRecord> iterator;
    private List<Sequence> sequences;
    private CSVManager csvManager;
    private String configuration;

    @Inject
    public CSVSequenceMapper(CSVManager csvManager, String config)
    {
        this.csvManager = csvManager;
        this.configuration = config;
    }
    /**
     * Loads the CSV file data into memory for faster access times during sequence load
     * Enables random access, unlike the regular CSV streaming would provide sequential one-time access.
     * Do not lazy load this: Loading while instantiation is affordable due to loading screen.
     * @throws IOException
     */
    public void init() throws IOException
    {
        iterator = csvManager.load(configuration);
        sequences = new ArrayList<>();

        int i = 0;
        while (iterator.hasNext()) {
            CSVRecord record = iterator.next();
            Sequence sequence = loadSequenceFromRecord(record);
            sequence.setIndex(i++);
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
        String roundType = record.get("RoundType");
        String file = record.get("File");
        String design = record.get("Design");
        String intro = record.get("Intro");
        String secImage = record.get("SecImage");

        return new Sequence(id, name, type, roundType, file, design, intro, secImage);
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

    @Override
    public Stream<Sequence> getStream()
    {
        return sequences.stream();
    }
}
