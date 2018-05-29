package me.twodee.quizatron.Model.Contract;

import com.google.inject.ImplementedBy;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Entity.QuizData;

import java.io.Serializable;
import java.nio.file.Path;

@ImplementedBy(QuizData.class)
public interface IState extends Serializable {

    void setConfigurationFile(Path location);
    Path getConfigurationFile();

    void setConfiguration(Configuration configuration);
    Configuration getConfiguration();
}
