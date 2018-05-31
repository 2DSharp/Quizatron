package me.twodee.quizatron.Model.Exception;

public class NoQuestionLeftException extends Exception {

    public NoQuestionLeftException() {
        super("No questions are left in this set.");
    }
}
