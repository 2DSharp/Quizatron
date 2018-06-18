package me.twodee.quizatron.Console.UIComponent;

import javafx.fxml.FXMLLoader;
import me.twodee.quizatron.Model.Entity.Sequence;

import java.io.IOException;

public class SequenceManager extends UIComponent
{
    private static final String USER_AGENT_STYLESHEET = QuestionConsoleView.class
            .getResource("/Stylesheets/sequence.css")
            .toExternalForm();

    private FXMLLoader fxmlLoader;

    public SequenceManager() throws IOException
    {
        fxmlLoader = initFXML("sequencer.fxml");
        fxmlLoader.load();
    }

    public void initialize()
    {
        this.getStylesheets().add(USER_AGENT_STYLESHEET);
    }
}
