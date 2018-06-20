package me.twodee.quizatron.Model.Service;

import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Entity.QuizData;
import me.twodee.quizatron.Model.Exception.ProjectNotSetException;
import me.twodee.quizatron.Model.Mapper.ConfigurationMapper;
import me.twodee.quizatron.Model.Mapper.QuizDataMapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Singleton
public class QuizDataService
{
    QuizData quizData;
    QuizDataMapper quizDataMapper;
    ConfigurationMapper configurationMapper;


    @Inject
    QuizDataService(QuizData quizData, QuizDataMapper quizDataMapper, ConfigurationMapper configurationMapper)
    {
        this.quizData = quizData;
        this.quizDataMapper = quizDataMapper;
        this.configurationMapper = configurationMapper;
    }

    public void loadConfig(Path file) throws FileNotFoundException
    {
        Configuration configuration = configurationMapper.loadConfiguration(file);
        quizData.setConfiguration(configuration);
        quizData.setDirectory(file.getParent().toAbsolutePath().toString());
    }


    public Configuration getConfiguration()
    {
        return quizData.getConfiguration();
    }

    public void loadSavedData(Path file) throws IOException, ClassNotFoundException
    {
        quizData = quizDataMapper.load(file.toAbsolutePath().toString());
    }

    public  String constructURL(String relativePath) throws MalformedURLException
    {
        String path = getInitialDirectory() + "/" + relativePath;
        return Paths.get(path).toUri().toURL().toExternalForm();
    }
    public String saveData() throws IOException
    {
        // Autosave should be only one file
        // Manual save multiple files based on nanoTime
        String fileName = "Q_SAVE_DATA.2D";

        if (quizDataLoaded()) {
            Path savePath = Paths.get(quizData.getDirectory() + "/save/");
            setUpDirectory(savePath);
            String saveFile = quizData.getDirectory() + "/save/" + fileName;

            quizDataMapper.save(quizData, saveFile);
            return saveFile;
        }
        else {
            throw new ProjectNotSetException();
        }
    }

    private void setUpDirectory(Path savePath) throws IOException
    {
        if (!Files.exists(savePath)) {
            Files.createDirectories(savePath);
        }
    }
    public Path getInitialDirectory()
    {
        return Paths.get(quizData.getDirectory());
    }

    public boolean quizDataLoaded()
    {
        return quizData.getDirectory() != null;
    }

    public void setCurrentSequenceIndex(int i)
    {
        quizData.setCurrentSequenceIndex(i);
    }

    public int getCurrentSequenceIndex() {
        return quizData.getCurrentSequenceIndex();
    }
}
