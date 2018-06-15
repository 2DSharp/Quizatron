package me.twodee.quizatron.Model.Service;

import me.twodee.quizatron.Model.Contract.ISequenceMapper;
import me.twodee.quizatron.Model.Entity.Sequence;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Mapper.CSVMapper;
import me.twodee.quizatron.Model.Entity.QuizData;

import java.io.IOException;

public class SequenceService
{
    private QuizData quizData;
    private final ISequenceMapper sequenceMapper;
    private Sequence sequence;

    private int curr;

    public SequenceService(QuizData quizData, Sequence sequence, ISequenceMapper sequenceMapper)
    {
        this.sequence = sequence;
        this.quizData = quizData;
        this.sequenceMapper = sequenceMapper;
    }


    public void getNext() throws NonExistentRecordException
    {
        curr += 1;
        setAndFetch();
    }
    public void getPrevious() throws NonExistentRecordException
    {
        curr -= 1;
        setAndFetch();
    }

    private void setAndFetch() throws NonExistentRecordException
    {
        sequence.setIndex(curr);
        sequenceMapper.fetch(sequence);
    }
    public Sequence getSequence() throws NonExistentRecordException
    {
        if (sequence.getName() == null) {
            sequenceMapper.fetch(sequence);
        }
        return sequence;
    }

    public Sequence getSequence(int index) throws NonExistentRecordException
    {
        sequence.setIndex(index - 1);
        sequenceMapper.fetch(sequence);
        return  sequence;
    }
}
