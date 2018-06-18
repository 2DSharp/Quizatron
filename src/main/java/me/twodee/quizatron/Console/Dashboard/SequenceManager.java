package me.twodee.quizatron.Console.Dashboard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import me.twodee.quizatron.Console.UIComponent.QuestionConsoleView;
import me.twodee.quizatron.Component.UIComponent;
import me.twodee.quizatron.Model.Entity.Sequence;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Service.QuizDataService;
import me.twodee.quizatron.Model.Service.SequenceService;

import java.io.IOException;

public class SequenceManager extends UIComponent
{
    @FXML
    private Label seqName;
    @FXML
    Label seqFile;
    @FXML
    Label seqNextName;

    private static final String USER_AGENT_STYLESHEET = QuestionConsoleView.class
            .getResource("/Stylesheets/sequence.css")
            .toExternalForm();

    private FXMLLoader fxmlLoader;
    private SequenceService sequenceService;
    private QuizDataService quizDataService;
    public SequenceManager(SequenceService sequenceService, QuizDataService quizDataService) throws IOException
    {
        this.quizDataService = quizDataService;
        this.sequenceService = sequenceService;
        fxmlLoader = initFXML("sequencer.fxml");
        fxmlLoader.load();
    }

    public void initialize()
    {
        this.getStylesheets().add(USER_AGENT_STYLESHEET);

    }

    public void loadService() throws IOException, NonExistentRecordException
    {
        sequenceService.load(quizDataService);

        sequenceService.getSequenceAsStream()
                       .map(e -> e.getName())
                       .forEach(System.out::println);

        Sequence sequence = sequenceService.fetchSequence(2);

        seqName.setText(sequence.getName());
        seqFile.setText(sequence.getFilePath());

    }
}
