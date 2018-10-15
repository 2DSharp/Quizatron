package me.twodee.quizatron.Model.Mapper;

import com.google.gson.Gson;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;

/**
 * ConfigurationMapper-  GSON implementation, need to put it in an interface
 */
public class ConfigurationMapper {

    private Configuration configuration;
    private Gson gson;

    /**
     * CTor to get the mapper running
     * @param configuration
     * @param gson
     */
    @Inject
    public ConfigurationMapper(Configuration configuration, Gson gson) {

        this.configuration = configuration;
        this.gson = gson;
    }

    /**
     * Loads the configuration from a json file
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public Configuration loadConfiguration(Path file) throws FileNotFoundException {

        configuration = gson.fromJson(new FileReader(file.toString()),
                                                    Configuration.class);
        return configuration;
    }
}
