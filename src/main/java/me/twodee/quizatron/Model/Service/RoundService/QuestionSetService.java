package me.twodee.quizatron.Model.Service.RoundService;

public class QuestionSetService
{
    protected int curr = -1;

    public void toStart()
    {
        curr = 0;
    }

    public void next()
    {
        curr += 1;
    }

    public void previous()
    {
        curr -= 1;
    }

    public boolean hasPrevious()
    {
        return curr > 0;
    }
}
