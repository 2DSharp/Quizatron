package me.twodee.quizatron.Model.Service;

import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class SequenceServiceTest
{
    @Mock
    private QuizDataService quizData;
    @Mock
    private Configuration configuration;

    @Before
    public void beforeEachTest() throws URISyntaxException
    {
        MockitoAnnotations.initMocks(this);
        when(quizData.getConfiguration()).thenReturn(configuration);
        String sequenceFile = Paths.get(this.getClass().getResource("/sequence.csv").toURI()).toAbsolutePath().toString();
        when(configuration.getSequence()).thenReturn(sequenceFile);
    }
/*
    @Test
    public void nextSequenceTest() throws IOException, NonExistentRecordException
    {
        CSVManager csvManager = new CSVManager();
        IMapper sequenceMapper = new CSVSequenceMapper(csvManager);
        ((CSVSequenceMapper) sequenceMapper).init(configuration);
        SequenceService sequenceService = new SequenceService(quizData, sequenceMapper);

        sequenceService.fetchNext();
        Sequence newSequence = sequenceService.fetchSequence();

        assertThat(newSequence.getType(), is("reg"));
    }

    @Test
    public void currentSequenceTest() throws IOException, NonExistentRecordException
    {
        CSVManager csvManager = new CSVManager();
        IMapper sequenceMapper = new CSVSequenceMapper(csvManager);
        ((CSVSequenceMapper) sequenceMapper).init(configuration);
        SequenceService sequenceService = new SequenceService(quizData, sequenceMapper);

        Sequence newSequence = sequenceService.fetchSequence();

        assertThat(newSequence.getName(), is("Regular Round"));
    }

    @Test(expected = NonExistentRecordException.class)
    public void prevNonExistentSequenceTest() throws NonExistentRecordException, IOException
    {
        CSVManager csvManager = new CSVManager();
        IMapper sequenceMapper = new CSVSequenceMapper(csvManager);
        ((CSVSequenceMapper) sequenceMapper).init(configuration);
        SequenceService sequenceService = new SequenceService(quizData, sequenceMapper);

        sequenceService.fetchPrevious();
        sequenceService.fetchSequence();
    }

    @Test
    public void getSequenceByIndexTest() throws NonExistentRecordException, IOException
    {
        CSVManager csvManager = new CSVManager();
        IMapper sequenceMapper = new CSVSequenceMapper(csvManager);
        ((CSVSequenceMapper) sequenceMapper).init(configuration);
        SequenceService sequenceService = new SequenceService(quizData, sequenceMapper);

        Sequence result = sequenceService.fetchSequence(4);

        assertThat(result.getFilePath(), is("block.csv"));
    }

    /*
    @Test(expected = SequenceNotSetException.class)
    public void storeSequenceBeforeFetchTest() throws IOException, SequenceNotSetException
    {
        CSVManager csvManager = new CSVManager();
        IMapper sequenceMapper = new CSVSequenceMapper(csvManager);
        ((CSVSequenceMapper) sequenceMapper).init(configuration);
        QuizDataMapper quizDataMapper = new QuizDataMapper();
        QuizData quizData = new QuizData();
        ConfigurationMapper configurationMapper = new ConfigurationMapper();
        QuizDataService quizDataService = new QuizDataService(quizData, quizDataMapper, );
        SequenceService sequenceService = new SequenceService(quizData, sequenceMapper);

        sequenceService.rememberCurrent();
    }

    @Test
    public void storeSequenceTest() throws IOException, SequenceNotSetException, NonExistentRecordException
    {
        CSVManager csvManager = new CSVManager();
        IMapper sequenceMapper = new CSVSequenceMapper(csvManager);
        ((CSVSequenceMapper) sequenceMapper).init(configuration);
        QuizData quizData = new QuizData();
        SequenceService sequenceService = new SequenceService(quizData, sequenceMapper);

        sequenceService.fetchSequence(3);
        sequenceService.rememberCurrent();
        Sequence sequence = sequenceService.fetchSequence(quizData.getCurrentSequenceIndex());
        String result = sequence.getName();

        assertThat(result, is("Subject Round"));
    }

    public void getRoundServiceTest() throws IOException
    {
        CSVManager csvManager = new CSVManager();
        IMapper sequenceMapper = new CSVSequenceMapper(csvManager);
        ((CSVSequenceMapper) sequenceMapper).init(configuration);
        SequenceService sequenceService = new SequenceService(quizData, sequenceMapper);
        sequenceService.getSequenceHandler();
    }
*/
}