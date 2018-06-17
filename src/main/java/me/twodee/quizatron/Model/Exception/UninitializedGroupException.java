package me.twodee.quizatron.Model.Exception;

public class UninitializedGroupException extends Throwable
{
    public UninitializedGroupException()
    {
        super("No group has been set or initialized from the cursor. Group needs to be fetched first.");
    }
}
