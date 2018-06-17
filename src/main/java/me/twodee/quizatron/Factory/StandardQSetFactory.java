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
    private final QuizDataService quizDataService;

    @Inject
    public StandardQSetFactory(CSVManager csvManager, QuizDataService quizDataService)
    {
        this.csvManager = csvManager;
        this.quizDataService = quizDataService;
    }
    public StandardQSet create(String file) throws IOException
    {
        String homedir = quizDataService.getInitialDirectory().toString();
        StandardQSetMapper standardQSetMapper = new StandardQSetMapper(csvManager, homedir + "/" + file);
        return new StandardQSet(standardQSetMapper);
    }
}
