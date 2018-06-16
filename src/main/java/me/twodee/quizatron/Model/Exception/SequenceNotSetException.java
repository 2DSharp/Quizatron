package me.twodee.quizatron.Model.Exception;

public class SequenceNotSetException extends Throwable
{
    public SequenceNotSetException() {
        super("No sequence has been initialized or fetched.");
    }
}
