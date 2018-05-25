package me.twodee.quizatron.Model.Entity;

import com.google.inject.Singleton;
import me.twodee.quizatron.Model.Contract.IState;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;

import java.nio.file.Path;

/**
 * This preserves the state of the program and all the metadata along about the quiz
 * There needs to be a single instance of this to be accessed elsewhere
 */
@Singleton
public class State implements IState {

    private transient Path configurationFile;
    private Configuration configuration;

    public void setConfigurationFile(Path location) {
        this.configurationFile = location;
    }

    @Override
    public Path getConfigurationFile() {
        return configurationFile;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
