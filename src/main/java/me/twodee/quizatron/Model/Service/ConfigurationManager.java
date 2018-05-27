package me.twodee.quizatron.Model.Service;

import com.google.gson.Gson;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;

public class ConfigurationManager {

    private Configuration configuration;
    private Gson gson;

    @Inject
    ConfigurationManager(Configuration configuration, Gson gson) {

        this.configuration = configuration;
        this.gson = gson;
    }

    public Configuration loadConfiguration(Path file) throws FileNotFoundException {

        configuration = gson.fromJson(new FileReader(file.toString()),
                                                    Configuration.class);
        return configuration;
    }
}
