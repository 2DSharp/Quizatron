package me.twodee.quizatron.Model.Service;

import com.google.gson.Gson;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Entity.QuizData;
import me.twodee.quizatron.Model.Exception.ProjectNotSetException;
import me.twodee.quizatron.Model.Mapper.ConfigurationMapper;
import me.twodee.quizatron.Model.Mapper.QuizDataMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class QuizDataServiceTest {

    private static final String TEST_DIR = "target/test-classes";
    private static final Path TEST_FILE = Paths.get(TEST_DIR + "/save/Q_SAVE_DATA.2D");
    private QuizDataService quizDataService;

    @Before
    public void setUp() {

        Gson gson = new Gson();
        QuizData quizData = new QuizData();
        QuizDataMapper quizDataMapper = new QuizDataMapper(gson);
        Configuration configuration = new Configuration();
        ConfigurationMapper configurationMapper = new ConfigurationMapper(configuration, gson);

        quizDataService = new QuizDataService(quizData, quizDataMapper, configurationMapper);
    }

    @Test
    public void unloadedConfigTest() {

        assertNull(quizDataService.getConfiguration());
    }
    @Test
    public void loadConfigTest() throws FileNotFoundException, URISyntaxException {

        Path file = Paths.get(this.getClass().getResource("/quizatron.json").toURI().getPath());
        quizDataService.loadConfig(file);
        assertNotNull(quizDataService.getConfiguration());
    }

    @Test
    public void getInitialDirectoryTest() throws FileNotFoundException, URISyntaxException {

        Path file = Paths.get(this.getClass().getResource("/quizatron.json").toURI().getPath());
        quizDataService.loadConfig(file);

        assertEquals(Paths.get(TEST_DIR).toAbsolutePath(),
                     quizDataService.getInitialDirectory());
    }

    @Test
    public void getConfigurationTest() throws URISyntaxException, FileNotFoundException {

        Path file = Paths.get(this.getClass().getResource("/quizatron.json").toURI().getPath());
        quizDataService.loadConfig(file);

        assertEquals("Inquizzitive 15", quizDataService.getConfiguration().getName());
    }

    @Test(expected = ProjectNotSetException.class)
    public void saveDataBeforeQuizLoadTest() throws IOException {

        quizDataService.saveData();
    }

    @Test
    public void saveDataTest() throws URISyntaxException, IOException {

        Path file = Paths.get(this.getClass().getResource("/quizatron.json").toURI().getPath());
        quizDataService.loadConfig(file);
        assertEquals(TEST_FILE.toAbsolutePath().toString(),
                     quizDataService.saveData());
    }

    @Test
    public void loadSavedDataConfigTest() throws IOException, ClassNotFoundException {

        quizDataService.loadSavedData(TEST_FILE);
        assertEquals("IQ 15", quizDataService.getConfiguration().getAlias());
    }

    @Test
    public void loadSavedDataDirTest() throws IOException, ClassNotFoundException {

        quizDataService.loadSavedData(TEST_FILE);
        assertEquals(Paths.get(TEST_DIR).toAbsolutePath(),
                     quizDataService.getInitialDirectory().toAbsolutePath());
    }

    @Test
    public void quizDataLoadedBeforeTest() {

        assertFalse(quizDataService.quizDataLoaded());
    }

    @Test
    public void quizDataLoadedAfterConfigLoadTest() throws FileNotFoundException, URISyntaxException {

        Path file = Paths.get(this.getClass().getResource("/quizatron.json").toURI().getPath());
        quizDataService.loadConfig(file);
        assertTrue(quizDataService.quizDataLoaded());
    }

    @Test
    public void quizDataLoadedAfterSavedDataLoadTest() throws IOException, ClassNotFoundException {

        QuizData data = quizDataService.loadSavedData(TEST_FILE);
        assertTrue(quizDataService.quizDataLoaded());
        assertEquals("Inquizzitive 15", data.getConfiguration().getName());
        assertEquals("media/logo.png", data.getConfiguration().getAppearance().getLogo());
        assertEquals(Paths.get(TEST_DIR).toAbsolutePath().toString(), data.getDirectory());
        assertEquals(0, data.getCurrentSequenceIndex());
    }
}