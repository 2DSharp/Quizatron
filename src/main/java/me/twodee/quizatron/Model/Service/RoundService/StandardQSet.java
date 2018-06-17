package me.twodee.quizatron.Model.Service.RoundService;

import me.twodee.quizatron.Model.Contract.IQuestionSetService;
import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Exception.NoQuestionLeftException;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Mapper.StandardSetMapper;
import me.twodee.quizatron.Model.Service.QuizDataService;
import org.apache.commons.csv.CSVRecord;

import javax.inject.Inject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StandardQSet
{

    private Question question;
    private StandardSetMapper standardSetMapper;
    private Iterator<CSVRecord> set = null;
    private List setList;
    private int curr;

    @Inject
    public StandardQSet(StandardSetMapper standardSetMapper)
    {
        this.standardSetMapper = standardSetMapper;
    }

    public Question getQuestion() throws NonExistentRecordException
    {
       return getQuestion(curr + 1);
    }

    public void nextQuestion()
    {
        curr += 1;
    }

    public void prevQuestion()
    {
        curr -= 1;
    }

    public Question getQuestion(int index) throws NonExistentRecordException
    {
        Question question = new Question();
        curr = index - 1;
        question.setIndex(curr);
        standardSetMapper.fetch(question);
        return question;
    }

    public int getTotalQuestions()
    {
        return standardSetMapper.getTotalRecords();
    }

    public boolean hasNext()
    {
        return curr < standardSetMapper.getTotalRecords() - 1;
    }

    public boolean hasPrev()
    {
        return curr > 0;
    }

    public boolean hasEnded()
    {
        return curr > standardSetMapper.getTotalRecords() - 1;
    }
}
