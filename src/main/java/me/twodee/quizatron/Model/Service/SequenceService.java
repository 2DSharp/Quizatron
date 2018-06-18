package me.twodee.quizatron.Model.Service;

import me.twodee.quizatron.Model.Contract.ISequenceMapper;
import me.twodee.quizatron.Model.Entity.Sequence;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Entity.QuizData;
import me.twodee.quizatron.Model.Exception.SequenceNotSetException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.stream.Stream;

public class SequenceService
{
    private QuizDataService quizDataService;
    private final ISequenceMapper sequenceMapper;
    private Sequence sequence;
    private int curr;

    @Inject
    public SequenceService(ISequenceMapper sequenceMapper)
    {
        this.sequenceMapper = sequenceMapper;
    }

    public void load(QuizDataService quizDataService) throws IOException
    {
        sequenceMapper.init(quizDataService.getInitialDirectory() + "/"
                                    + quizDataService.getConfiguration().getSequence());
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
        quizDataService.setCurrentSequenceIndex(sequence.getIndex() + 1);
    }

    public Stream<Sequence> getSequenceAsStream()
    {
        return sequenceMapper.getStream();
    }
    public void getSequenceHandler()
    {
    }

}
