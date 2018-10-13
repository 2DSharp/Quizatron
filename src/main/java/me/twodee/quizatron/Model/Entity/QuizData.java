package me.twodee.quizatron.Model.Entity;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.inject.Singleton;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;

import java.io.Serializable;

/**
 * This preserves the quiz data including the configuration.
 */
@Singleton
public class QuizData implements Serializable  {

    @SerializedName("configuration")
    @Expose
    private Configuration configuration;

    @SerializedName("directory")
    @Expose
    private String directory;

    private int currentSequence;

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }

    public int getCurrentSequenceIndex()
    {
        return currentSequence;
    }

    public void setCurrentSequenceIndex(int currentSequence)
    {
        this.currentSequence = currentSequence;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("configuration", configuration)
                .add("directory", directory)
                .add("currentSequence", currentSequence)
                .toString();
    }
}
