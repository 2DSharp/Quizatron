package me.twodee.quizatron.Model.Service.RoundService;

import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Mapper.StandardQSetMapper;

import javax.inject.Inject;

public class StandardQSet
{
    private StandardQSetMapper standardQSetMapper;
    private int curr;

    @Inject
    public StandardQSet(StandardQSetMapper standardQSetMapper)
    {
        this.standardQSetMapper = standardQSetMapper;
    }

    public Question fetchQuestion() throws NonExistentRecordException
    {
       return fetchQuestion(curr + 1);
    }

    public void nextQuestion()
    {
        curr += 1;
    }

    public void prevQuestion()
    {
        curr -= 1;
    }

    public Question fetchQuestion(int index) throws NonExistentRecordException
    {
        Question question = new Question();
        curr = index - 1;
        question.setIndex(curr);
        standardQSetMapper.fetch(question);
        return question;
    }

    public int getTotalQuestions()
    {
        return standardQSetMapper.getTotalRecords();
    }

    public boolean hasNext()
    {
        return curr < standardQSetMapper.getTotalRecords() - 1;
    }

    public boolean hasPrev()
    {
        return curr > 0;
    }

    public boolean hasEnded()
    {
        return curr > standardQSetMapper.getTotalRecords() - 1;
    }
}
