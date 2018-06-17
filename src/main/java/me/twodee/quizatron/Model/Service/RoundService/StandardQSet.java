package me.twodee.quizatron.Model.Service.RoundService;

import me.twodee.quizatron.Model.Contract.IQuestionSetService;
import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Mapper.StandardQSetMapper;

import javax.inject.Inject;

public class StandardQSet extends QuestionSetService implements IQuestionSetService<Question>
{
    private StandardQSetMapper standardQSetMapper;

    @Inject
    public StandardQSet(StandardQSetMapper standardQSetMapper)
    {
        this.standardQSetMapper = standardQSetMapper;
    }

    @Override
    public Question fetch() throws NonExistentRecordException
    {
        return fetch(curr + 1);
    }

    @Override
    public Question fetch(int index) throws NonExistentRecordException
    {
        Question question = new Question();
        curr = index - 1;
        question.setIndex(curr);
        standardQSetMapper.fetch(question);
        return question;
    }

    @Override
    public boolean hasNext()
    {
        return curr < standardQSetMapper.getTotalRecords() - 1;
    }

    public int getTotalQuestions()
    {
        return standardQSetMapper.getTotalRecords();
    }
}
