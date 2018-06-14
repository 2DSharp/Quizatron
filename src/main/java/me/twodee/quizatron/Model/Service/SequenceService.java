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

    public void getNextSequence() throws NonExistentRecordException
    {
        curr += 1;
        sequence.setIndex(curr);
        sequenceMapper.fetch(sequence);
    }
}
