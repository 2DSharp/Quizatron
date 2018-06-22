package me.twodee.quizatron.Model.Service.RoundService;

import me.twodee.quizatron.Model.Contract.IQuestionSetService;
import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Mapper.StandardQSetMapper;

import javax.inject.Inject;
import java.util.stream.Stream;

public class StandardQSet extends QuestionSetService implements IQuestionSetService<Question>
{
    private StandardQSetMapper standardQSetMapper;
    public enum Result {
        CORRECT, WRONG
    }
    Result result;
    @Inject
    public StandardQSet(StandardQSetMapper standardQSetMapper)
    {
        this.standardQSetMapper = standardQSetMapper;
    }

    @Override
    public Question fetch() throws NonExistentRecordException
    {
        return fetch(curr);
    }

    @Override
    public Question fetch(int index) throws NonExistentRecordException
    {
        Question question = new Question();
        curr = index;
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

    public Stream<Question> getStream()
    {
        return standardQSetMapper.getStream();
}

    public void setResult(Result result)
    {
        this.result = result;
    }

    public Result getResult()
    {
        return result;
    }
}
