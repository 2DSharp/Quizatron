package me.twodee.quizatron.Model.Mapper;

import me.twodee.quizatron.Component.CSVManager;
import me.twodee.quizatron.Model.Contract.IMapper;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Entity.Sequence;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class SequenceMapperTest {
    @Mock
    private Configuration configuration;

    private IMapper sequenceMapper;

    @Before
    public void beforeEachTest() throws URISyntaxException, IOException {
        String sequenceFile = Paths.get(this.getClass().getResource("/rounds/sequences.csv").toURI())
                .toAbsolutePath()
                .toString();
        CSVManager csvManager = new CSVManager();
        sequenceMapper = new CSVSequenceMapper(csvManager, sequenceFile);
        sequenceMapper.init();
        MockitoAnnotations.initMocks(this);
        when(configuration.getSequence()).thenReturn(sequenceFile);
    }

    @Test(expected = NonExistentRecordException.class)
    public void fetchNonExistentIndexPositive() throws NonExistentRecordException {

        Sequence sequence = new Sequence();
        sequence.setIndex(10);
        sequenceMapper.fetch(sequence);
    }

    @Test
    public void fetchEmptySequenceTest() throws NonExistentRecordException {
        Sequence sequence = new Sequence();
        sequenceMapper.fetch(sequence);
        String result = sequence.getName();

        assertThat(result, is("Regular Round"));
    }

    @Test
    public void fetchFirstRecordTest() throws NonExistentRecordException {
        Sequence sequence = new Sequence();

        sequence.setIndex(0);
        sequenceMapper.fetch(sequence);

        assertThat(sequence.getName(), is("Regular Round"));
    }

    @Test
    public void fetchSecondRecordTest() throws NonExistentRecordException {
        Sequence sequence = new Sequence();

        sequence.setIndex(1);
        sequenceMapper.fetch(sequence);

        assertThat(sequence.getName(), is("Audio Visual Round"));
    }

    @Test
    public void totalRecordNumTest() {
        int result = sequenceMapper.getTotalRecords();
        assertThat(result, is(6));
    }

    @Test
    public void fetchFileTest() throws NonExistentRecordException {
        Sequence sequence = new Sequence();
        sequence.setIndex(5);
        sequenceMapper.fetch(sequence);
        assertThat(sequence.getFilePath(), is("rapidfire.csv"));
        assertThat(sequence.getIntro(), is("red.mp4"));
        assertThat(sequence.getType(), is("block"));
    }

    @Test
    public void streamTest() {
        Stream<Sequence> sequences = sequenceMapper.getStream();
        List<String> names = sequences.map(Sequence::getName)
                .collect(Collectors.toList());

        assertThat(names, hasItems("Regular Round",
                "Audio Visual Round",
                "Subject Round",
                "Who Am I?",
                "BlockStage Round",
                "Rapid Fire"));
    }
}
