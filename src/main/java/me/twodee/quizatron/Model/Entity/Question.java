package me.twodee.quizatron.Model.Entity;

import me.twodee.quizatron.Model.Contract.IQuestion;

public class Question implements IQuestion {

    private String title;

    private String answer;
    private String questionImage;
    private String ansImage;
    private String media;
    private String id;
    private int index;
    public enum Result
    {
        CORRECT, WRONG;
    }
    Result result;

    public Question()
    {

    }
    public Question(String id, String title, String questionImage, String answer, String ansImage, String media)
    {
        this.id = id;
        this.title = title;
        this.questionImage = questionImage;
        this.answer = answer;
        this.ansImage = ansImage;
        this.media = media;
    }

    public void setQuestion(Question question)
    {
        this.index = question.index;
        this.id = question.id;
        this.title = question.title;
        this.questionImage = question.questionImage;
        this.ansImage = question.ansImage;
        this.answer = question.answer;
        this.media = question.media;
    }
    public void setIndex(int index)
    {
        this.index = index;
    }

    public int getIndex()
    {
        return index;
    }

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public String getAnswer()
    {
        return answer;
    }

    @Override
    public String getQuestionImage()
    {
        return questionImage;
    }

    @Override
    public String getAnsImage()
    {
        return ansImage;
    }

    @Override
    public String getMedia()
    {
        return media;
    }

    public String getId()
    {
        return id;
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
