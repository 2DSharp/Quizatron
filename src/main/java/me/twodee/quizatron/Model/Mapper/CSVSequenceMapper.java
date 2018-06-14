package me.twodee.quizatron.Model.Mapper;

import me.twodee.quizatron.Model.Contract.ISequenceMapper;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Entity.Sequence;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;

import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CSVSequenceMapper extends CSVMapper implements ISequenceMapper
{
    private Configuration configuration;
    private Iterator<CSVRecord> iterator;
    private List<Sequence> sequences;

    public CSVSequenceMapper(Configuration configuration) throws IOException
    {
        this.configuration = configuration;
        init();
    }

    private void init() throws IOException
    {
        iterator = load(configuration.getSequence());
        sequences = new ArrayList<>();

        while (iterator.hasNext()) {
            CSVRecord record = iterator.next();
            Sequence sequence = loadSequenceFromRecord(record);
            sequences.add(sequence);
        }
    }

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
