package me.twodee.quizatron.Factory;

import me.twodee.quizatron.Component.CSVManager;
import me.twodee.quizatron.Model.Mapper.StandardQSetMapper;
import me.twodee.quizatron.Model.Service.QuizDataService;
import me.twodee.quizatron.Model.Service.RoundService.StandardQSet;

import javax.inject.Inject;
import java.io.IOException;

public class StandardQSetFactory
{
    private final CSVManager csvManager;

    @Inject
    public StandardQSetFactory(CSVManager csvManager)
    {
        this.csvManager = csvManager;
    }
    public StandardQSet create(String file) throws IOException
    {
        StandardQSetMapper standardQSetMapper = new StandardQSetMapper(csvManager,  file);
        return new StandardQSet(standardQSetMapper);
    }
}
