package me.twodee.quizatron.Model.Service;

import me.twodee.quizatron.Model.Contract.ISequenceMapper;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Entity.QuizData;
import me.twodee.quizatron.Model.Entity.Sequence;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Mapper.CSVSequenceMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class SequenceServiceTest
{
    @Mock
    private QuizData quizData;
    @Mock
    private Configuration configuration;

    //private SequenceMapper sequenceMapper;

    @Before
    public void beforeEachTest() throws URISyntaxException, MalformedURLException
    {
        MockitoAnnotations.initMocks(this);
        when(quizData.getConfiguration()).thenReturn(configuration);
        String sequenceFile = Paths.get(this.getClass().getResource("/sequence.csv").toURI())
                                   .toAbsolutePath()
                                   .toString();
        when(configuration.getSequence())
                .thenReturn(sequenceFile);

    }

    @Test
    public void nextSequenceTest() throws IOException, NonExistentRecordException
    {
        ISequenceMapper sequenceMapper = new CSVSequenceMapper(quizData.getConfiguration());
        Sequence sequence = new Sequence();
        SequenceService sequenceService = new SequenceService(quizData, sequence, sequenceMapper);

        sequenceService.getNext();
        Sequence newSequence = sequenceService.getSequence();
        assertThat(newSequence.getType(), is("reg"));
    }

    @Test
    public void currentSequenceTest() throws IOException, NonExistentRecordException
    {
        ISequenceMapper sequenceMapper = new CSVSequenceMapper(quizData.getConfiguration());
        Sequence sequence = new Sequence();
        SequenceService sequenceService = new SequenceService(quizData, sequence, sequenceMapper);

        Sequence newSequence = sequenceService.getSequence();
        assertThat(newSequence.getName(), is("Regular Round"));
    }

    @Test (expected = NonExistentRecordException.class)
    public void getPrevSequenceTest() throws NonExistentRecordException, IOException
    {
        ISequenceMapper sequenceMapper = new CSVSequenceMapper(quizData.getConfiguration());
        Sequence sequence = new Sequence();
        SequenceService sequenceService = new SequenceService(quizData, sequence, sequenceMapper);

        sequenceService.getPrevious();
    }

    @Test
    public void getSequenceByIndexTest() throws NonExistentRecordException, IOException
    {
        ISequenceMapper sequenceMapper = new CSVSequenceMapper(quizData.getConfiguration());
        Sequence sequence = new Sequence();
        SequenceService sequenceService = new SequenceService(quizData, sequence, sequenceMapper);

        Sequence result = sequenceService.getSequence(4);
        assertThat(result.getFilePath(), is("block.csv"));
    }
}
