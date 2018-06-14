package me.twodee.quizatron.Model.Exception;

public class NonExistentRecordException extends Throwable
{
    public NonExistentRecordException()
    {
        super("The record doesn't exist.");
    }
}
