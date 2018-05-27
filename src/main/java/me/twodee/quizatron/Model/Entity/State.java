package me.twodee.quizatron.Model.Entity;

import com.google.inject.Singleton;
import me.twodee.quizatron.Model.Contract.IState;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This preserves the state of the program and all the metadata along about the quiz
 * There needs to be a single instance of this to be accessed elsewhere
 */
@Singleton
public class State implements IState {

    private String configurationFile;
    private Configuration configuration;
    private enum Status {
        PLAYING, PAUSED, STOPPED
    }
    private Status status;
    public void setConfigurationFile(Path location) {

        this.configurationFile = location.toAbsolutePath().toString();
    }

    @Override
    public Path getConfigurationFile() {

        return Paths.get(configurationFile);
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
