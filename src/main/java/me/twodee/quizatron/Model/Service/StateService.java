package me.twodee.quizatron.Model.Service;

import me.twodee.quizatron.Model.Contract.IState;
import me.twodee.quizatron.Model.Contract.IStateMapper;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Exception.ProjectNotSetException;

import javax.inject.Inject;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StateService {

    IState state;
    Path configFile;
    String projectDirectory;
    IStateMapper stateMapper;

    @Inject
    StateService(IState state, IStateMapper stateMapper) {
        this.state = state;
        this.stateMapper = stateMapper;
    }

    public void setConfigurationPath(Path configFile) throws MalformedURLException {
        this.configFile = configFile;
        this.projectDirectory = configFile
                .getParent()
                .toUri()
                .toURL()
                .getPath();
    }

    public void loadState() throws FileNotFoundException {
        stateMapper.populate(state, configFile);
    }

    public Configuration getConfiguration() {
        return state.getConfiguration();
    }
    public void loadState(IState state) {

    }
    public String saveState() throws IOException {

        // Autosave should be only one file
        // Manual save multiple files based on nanoTime
        String stateFileName = "Q_SAVE_" + System.nanoTime() + ".2D";

        if (projectDirectory == null) {
            throw new ProjectNotSetException();
        }
        else {

            Path savePath = Paths.get(projectDirectory + "save/");

            if (!Files.exists(savePath)) {
                Files.createDirectories(savePath);
            }
            String saveFile = projectDirectory + "save/" + stateFileName;
            stateMapper.save(state, saveFile);
            return saveFile;
        }
    }

    public Path getInitialDirectory() {
        return state.getConfigurationFile().getParent();
    }

    public boolean stateLoaded() {
        return state.getConfigurationFile() != null;
    }


}
