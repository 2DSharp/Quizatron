package me.twodee.quizatron.Model.Service;

import me.twodee.quizatron.Model.Mapper.CSVMapper;
import me.twodee.quizatron.Model.Entity.QuizData;

import java.io.IOException;

public class SequenceService
{
    private QuizData quizData;
    private final CSVMapper sequenceMapper;
    private boolean isLoaded = false;

    public SequenceService(QuizData quizData, CSVMapper sequenceMapper)
    {
        this.quizData = quizData;
        this.sequenceMapper = sequenceMapper;
    }

    public void load() throws IOException
    {
        sequenceMapper.load(quizData.getConfiguration().getSequence());
        isLoaded = true;
    }

    public boolean isLoaded()
    {
        return isLoaded;
    }
}
