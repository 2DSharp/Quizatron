package me.twodee.quizatron.Model.Service;

import me.twodee.quizatron.Model.Contract.IMapperFactory;
import me.twodee.quizatron.Model.Contract.IMapper;
import me.twodee.quizatron.Model.Entity.Sequence;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Exception.SequenceNotSetException;
import me.twodee.quizatron.Model.Mapper.CSVSequenceMapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

@Singleton
public class SequenceService
{
    private QuizDataService quizDataService;
    private IMapper sequenceMapper;
    private Sequence sequence;
    private int curr;
    private IMapperFactory<CSVSequenceMapper> factory;

    @Inject
    public SequenceService(IMapperFactory<CSVSequenceMapper> factory, QuizDataService quizDataService)
    {
        this.factory = factory;
        this.quizDataService = quizDataService;
    }

    public void load()
    throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException,
           IllegalAccessException
    {
        String seqConfig = quizDataService.getInitialDirectory() + "/"
                + quizDataService.getConfiguration().getSequence();

        sequenceMapper = factory.create(CSVSequenceMapper.class, seqConfig);
        sequenceMapper.init();
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
