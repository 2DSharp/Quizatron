package me.twodee.quizatron.Model.Service;

import com.google.gson.Gson;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Entity.QuizData;
import me.twodee.quizatron.Model.Mapper.ConfigurationMapper;
import me.twodee.quizatron.Model.Mapper.QuizDataMapper;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class QuizDataServiceTest {


    @Test
    public void loadConfigTest() throws FileNotFoundException, URISyntaxException {

        QuizData quizData = new QuizData();
        QuizDataMapper dataMapperMock = mock(QuizDataMapper.class);
        Configuration configuration = new Configuration();
        ConfigurationMapper configurationMapper = new ConfigurationMapper(configuration, new Gson());

        QuizDataService quizDataService = new QuizDataService(quizData, dataMapperMock, configurationMapper);

        Path file = Paths.get(this.getClass().getResource("/quizatron.json").toURI().getPath());
        Path workingDir = file.getParent();
        quizDataService.loadConfig(file);

        assertTrue(quizDataService.getConfiguration() instanceof Configuration);
        assertEquals("Inquizzitive 15", quizDataService.getConfiguration().getName());
        assertEquals(workingDir, quizDataService.getInitialDirectory());
    }

    @Test
    public void getConfiguration() {
    }

    @Test
    public void loadData() {
    }

    @Test
    public void saveData() {
    }

    @Test
    public void getInitialDirectory() {
    }

    @Test
    public void quizDataLoaded() {
    }
}