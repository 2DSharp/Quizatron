package me.twodee.quizatron.Model.Mapper;

import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SequenceMapperTest
{
    @Mock
    private Configuration configuration;
/*
    @Before
    public void beforeEachTest() throws URISyntaxException
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
        CSVManager csvManager = new CSVManager();
        IMapper sequenceMapper = new CSVSequenceMapper(csvManager);
        ((CSVSequenceMapper) sequenceMapper).init(configuration);
        Sequence sequence = new Sequence();
        sequence.setIndex(10);
        sequenceMapper.fetch(sequence);
    }

    @Test
    public void fetchEmptySequenceTest() throws IOException, NonExistentRecordException
    {
        CSVManager csvManager = new CSVManager();
        IMapper sequenceMapper = new CSVSequenceMapper(csvManager);
        ((CSVSequenceMapper) sequenceMapper).init(configuration);
        Sequence sequence = new Sequence();
        sequenceMapper.fetch(sequence);
        String result = sequence.getName();

        assertThat(result, is("Regular Round"));
    }

    @Test
    public void fetchFirstRecordTest() throws IOException, NonExistentRecordException
    {
        CSVManager csvManager = new CSVManager();
        IMapper sequenceMapper = new CSVSequenceMapper(csvManager);
        ((CSVSequenceMapper) sequenceMapper).init(configuration);
        Sequence sequence = new Sequence();

        sequence.setIndex(0);
        sequenceMapper.fetch(sequence);

        assertThat(sequence.getName(), is("Regular Round"));
    }

    @Test
    public void fetchSecondRecordTest() throws IOException, NonExistentRecordException
    {
        CSVManager csvManager = new CSVManager();
        IMapper sequenceMapper = new CSVSequenceMapper(csvManager);
        ((CSVSequenceMapper) sequenceMapper).init(configuration);
        Sequence sequence = new Sequence();

        sequence.setIndex(1);
        sequenceMapper.fetch(sequence);

        assertThat(sequence.getName(), is("Audio Visual Round"));
    }

    @Test
    public void totalRecordNumTest() throws IOException
    {
        CSVManager csvManager = new CSVManager();
        IMapper sequenceMapper = new CSVSequenceMapper(csvManager);
        ((CSVSequenceMapper) sequenceMapper).init(configuration);

        int result = sequenceMapper.getTotalRecords();

        assertThat(result, is(6));
    }

    @Test
    public void fetchTypeTest() throws IOException, NonExistentRecordException
    {
        CSVManager csvManager = new CSVManager();
        IMapper sequenceMapper = new CSVSequenceMapper(csvManager);
        ((CSVSequenceMapper) sequenceMapper).init(configuration);
        Sequence sequence = new Sequence();

        sequence.setIndex(3);
        sequenceMapper.fetch(sequence);

        assertThat(sequence.getType(), is("block"));
    }

    @Test
    public void fetchFileTest() throws IOException, NonExistentRecordException
    {
        CSVManager csvManager = new CSVManager();
        IMapper sequenceMapper = new CSVSequenceMapper(csvManager);
        ((CSVSequenceMapper) sequenceMapper).init(configuration);
        Sequence sequence = new Sequence();

        sequence.setIndex(5);
        sequenceMapper.fetch(sequence);

        assertThat(sequence.getFilePath(), is("rapidfire.csv"));
    }

    @Test
    public void fetchIntroTest() throws IOException, NonExistentRecordException
    {
        CSVManager csvManager = new CSVManager();
        IMapper sequenceMapper = new CSVSequenceMapper(csvManager);
        ((CSVSequenceMapper) sequenceMapper).init(configuration);
        Sequence sequence = new Sequence();

        sequence.setIndex(3);
        sequenceMapper.fetch(sequence);

        assertThat(sequence.getIntro(), is("yellow.mp4"));
    }

    @Test
    public void streamTest() throws IOException
    {
        CSVManager csvManager = new CSVManager();
        IMapper sequenceMapper = new CSVSequenceMapper(csvManager);
        ((CSVSequenceMapper) sequenceMapper).init(configuration);

        Stream<Sequence> sequences = sequenceMapper.getStream();

         List<String> names = sequences.map(Sequence::getName)
                                       .collect(Collectors.toList());

         assertThat(names, hasItems("Regular Round",
                             "Audio Visual Round",
                             "Subject Round",
                             "Who Am I?",
                             "Block Round",
                             "Rapid Fire"));
    }
*/

}
