package me.twodee.quizatron.Model.Service;

import me.twodee.quizatron.Model.Contract.ISequenceMapper;
import me.twodee.quizatron.Model.Entity.Sequence;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Entity.QuizData;
import me.twodee.quizatron.Model.Exception.SequenceNotSetException;

public class SequenceService
{
    private QuizData quizData;
    private final ISequenceMapper sequenceMapper;
    private Sequence sequence;
    private int curr;

    public SequenceService(QuizData quizData, ISequenceMapper sequenceMapper)
    {
        this.quizData = quizData;
        this.sequenceMapper = sequenceMapper;
    }
    public void fetchNext()
    {
        curr += 1;
    }

    public void fetchPrevious()
    {
        curr -= 1;
    }

    public Sequence fetchSequence() throws NonExistentRecordException
    {
        return fetchSequence(curr + 1);
    }

    public Sequence fetchSequence(int index) throws NonExistentRecordException
    {
        sequence = new Sequence();
        curr = index - 1;
        sequence.setIndex(curr);
        sequenceMapper.fetch(sequence);

        return sequence;
    }

    public void rememberCurrent() throws SequenceNotSetException
    {
        if (sequence == null) {
            throw new SequenceNotSetException();
        }
        quizData.setCurrentSequenceIndex(sequence.getIndex() + 1);
    }

    public void getSequenceHandler()
    {
    }
}
