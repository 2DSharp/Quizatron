package me.twodee.quizatron.Model.Mapper;

import me.twodee.quizatron.Model.Contract.ISequenceMapper;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Entity.Sequence;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
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

public class SequenceMapperTest
{
    @Mock
    private Configuration configuration;

    @Before
    public void beforeEachTest() throws URISyntaxException, MalformedURLException
    {
        String sequenceFile = Paths.get(this.getClass().getResource("/sequence.csv").toURI())
                            .toAbsolutePath()
                            .toString();

        MockitoAnnotations.initMocks(this);
        when(configuration.getSequence()).thenReturn(sequenceFile);
    }

    @Test (expected = NonExistentRecordException.class)
    public void fetchNonExistentIndexPositive() throws NonExistentRecordException, IOException
    {
        ISequenceMapper sequenceMapper = new CSVSequenceMapper(configuration);

        Sequence sequence = new Sequence();
        sequence.setIndex(10);
        sequenceMapper.fetch(sequence);
    }

    @Test
    public void fetchEmptySequenceTest() throws IOException, NonExistentRecordException
    {
        ISequenceMapper sequenceMapper = new CSVSequenceMapper(configuration);
        Sequence sequence = new Sequence();
        sequenceMapper.fetch(sequence);
        String result = sequence.getName();

        assertThat(result, is("Regular Round"));
    }

    @Test
    public void fetchFirstRecordTest() throws IOException, NonExistentRecordException
    {
        ISequenceMapper sequenceMapper = new CSVSequenceMapper(configuration);

        Sequence sequence = new Sequence();
        sequence.setIndex(0);
        sequenceMapper.fetch(sequence);

        assertThat(sequence.getName(), is("Regular Round"));
    }

    @Test
    public void fetchSecondRecordTest() throws IOException, NonExistentRecordException
    {
        ISequenceMapper sequenceMapper = new CSVSequenceMapper(configuration);

        Sequence sequence = new Sequence();
        sequence.setIndex(1);
        sequenceMapper.fetch(sequence);

        assertThat(sequence.getName(), is("Audio Visual Round"));
    }

    @Test
    public void totalRecordNumTest() throws IOException
    {
        ISequenceMapper sequenceMapper = new CSVSequenceMapper(configuration);
        int result = sequenceMapper.getTotalRecords();

        assertThat(result, is(6));
    }

    @Test
    public void fetchTypeTest() throws IOException, NonExistentRecordException
    {
        ISequenceMapper sequenceMapper = new CSVSequenceMapper(configuration);

        Sequence sequence = new Sequence();
        sequence.setIndex(3);
        sequenceMapper.fetch(sequence);

        assertThat(sequence.getType(), is("block"));
    }

    @Test
    public void fetchFileTest() throws IOException, NonExistentRecordException
    {
        ISequenceMapper sequenceMapper = new CSVSequenceMapper(configuration);

        Sequence sequence = new Sequence();
        sequence.setIndex(5);
        sequenceMapper.fetch(sequence);

        assertThat(sequence.getFilePath(), is("rapidfire.csv"));
    }

    @Test
    public void fetchIntroTest() throws IOException, NonExistentRecordException
    {
        ISequenceMapper sequenceMapper = new CSVSequenceMapper(configuration);

        Sequence sequence = new Sequence();
        sequence.setIndex(3);
        sequenceMapper.fetch(sequence);

        assertThat(sequence.getIntro(), is("yellow.mp4"));
    }
}
