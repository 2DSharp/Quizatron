package me.twodee.quizatron.Model.Exception;

import java.io.IOException;

public class ProjectNotSetException extends IOException {

    public ProjectNotSetException() {
        super("The project directory has not been set.");
    }
}
