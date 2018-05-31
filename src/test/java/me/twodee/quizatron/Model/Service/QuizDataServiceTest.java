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

    QuizDataService quizDataService;

    @Before
    public void setUp() {

        QuizData quizData = new QuizData();
        QuizDataMapper quizDataMapper = new QuizDataMapper();
        Configuration configuration = new Configuration();
        ConfigurationMapper configurationMapper = new ConfigurationMapper(configuration, new Gson());

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

        assertEquals(Paths.get("/home/dedipyaman/IdeaProjects/Quizatron/target/test-classes"),
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
        assertEquals("/home/dedipyaman/IdeaProjects/Quizatron/target/test-classes/save/Q_SAVE_DATA.2D",
                     quizDataService.saveData());
    }

    @Test
    public void loadSavedDataConfigTest() throws IOException, ClassNotFoundException {

        Path file = Paths.get("/home/dedipyaman/IdeaProjects/Quizatron/target/test-classes/save/Q_SAVE_DATA.2D");
        quizDataService.loadSavedData(file);
        assertEquals("IQ 15", quizDataService.getConfiguration().getAlias());
    }

    @Test
    public void loadSavedDataDirTest() throws IOException, ClassNotFoundException {

        Path file = Paths.get("/home/dedipyaman/IdeaProjects/Quizatron/target/test-classes/save/Q_SAVE_DATA.2D");
        quizDataService.loadSavedData(file);
        assertEquals("/home/dedipyaman/IdeaProjects/Quizatron/target/test-classes",
                     quizDataService.getInitialDirectory().toAbsolutePath().toString());
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

        Path file = Paths.get("/home/dedipyaman/IdeaProjects/Quizatron/target/test-classes/save/Q_SAVE_DATA.2D");
        quizDataService.loadSavedData(file);
        assertTrue(quizDataService.quizDataLoaded());

    }
}