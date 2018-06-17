package me.twodee.quizatron.Model.Entity;

import me.twodee.quizatron.Model.Contract.IQuestion;

public class Question implements IQuestion {

    private String title;

    private String answer;
    private String questionImage;
    private String ansImage;
    private String media;
    private int index;

    public Question()
    {

    }
    public Question(String title, String questionImage, String answer, String ansImage, String media)
    {

        this.title = title;
        this.questionImage = questionImage;
        this.answer = answer;
        this.ansImage = ansImage;
        this.media = media;
    }

    public void setQuestion(Question question)
    {
        this.index = question.index;
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
}
