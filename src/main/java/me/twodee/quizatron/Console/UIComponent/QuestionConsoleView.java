package me.twodee.quizatron.Console.UIComponent;

import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import me.twodee.quizatron.Component.Presentation;
import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Service.RoundService.StandardQSet;
import me.twodee.quizatron.Presentation.View.QuestionDisplay;
import java.io.IOException;
import java.util.List;

/**
 * Question control center for Quizatron
 *
 * @author Dedipyaman Das <2d@twodee.me>
 * @version 1.0.18.1
 * @since 1.0.18.1
 */
public class QuestionConsoleView extends UIComponent
{
    private static final String USER_AGENT_STYLESHEET = QuestionConsoleView.class.getResource("/Stylesheets/question_viewer.css").toExternalForm();
    private StandardQSet standardQSet;
    private FXMLLoader fxmlLoader;
    private Presentation presentation;
    private Player player;
    private boolean playerLoaded;

    @FXML
    private Button nextBtn;
    @FXML
    private Button prevBtn;
    @FXML
    private Label questionLbl;
    @FXML
    private Label descriptionLbl;
    @FXML
    private Label answerLbl;
    @FXML
    private JFXToggleButton mediaDisplayToggleBtn;
    @FXML
    private VBox topBox;
    private  List<Question> questionList;
    private int current;

    public QuestionConsoleView(StandardQSet standardQSet, Presentation presentation) throws IOException
    {
        this.presentation = presentation;
        this.standardQSet = standardQSet;
        this.fxmlLoader = initFXML("questionviewer.fxml");
        this.fxmlLoader.load();

        loadInitialQuestion();
    }

    public void initialize()
    {
        this.getStylesheets().add(USER_AGENT_STYLESHEET);
        answerLbl.managedProperty().bind(answerLbl.visibleProperty());
        questionLbl.managedProperty().bind(questionLbl.visibleProperty());
    }

    private void loadInitialQuestion()
    {
        current = 1;
        //questionList = standardQSet.toList();
        //Question question = standardQSet.getQuestion(current);
        //displayQuestionData(question);
    }

    private void displayQuestionPresentation(String question)
    {
        try {
            presentation.changeView("questionview");
            QuestionDisplay questionDisplay = presentation.getView();
            questionDisplay.revealQuestion(question);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayQuestionData(Question question)
    {
        if (question.getMedia() != null) {
            //loadMedia(question.getMedia());
        }
        else {
            displayQuestionPresentation(question.getTitle());
        }
        questionLbl.setText(question.getTitle());
        answerLbl.setText(question.getAnswer());

        Boolean isLastQuestion = questionList.size() <= current;
        Boolean isFirstQuestion = current <= 1;
        nextBtn.setDisable(isLastQuestion);
        prevBtn.setDisable(isFirstQuestion);
    }

    private void loadMedia(Media media)
    {
        playerLoaded = true;
        try {
            player = new Player(presentation);
            initPlayer(player, media);
            addPlayerToDisplay(player);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPlayer(Player player, Media media)
    {
        player.loadMedia(media);
        BooleanBinding toggleState = mediaDisplayToggleBtn.selectedProperty().not()
                                                          .and(mediaDisplayToggleBtn.disabledProperty().not());
        player.visibleProperty().bind(toggleState);
        player.managedProperty().bind(player.visibleProperty());
    }

    private void addPlayerToDisplay(Player player)
    {
        mediaDisplayToggleBtn.setDisable(false);
        topBox.getChildren().add(player);
        setQuestionDataVisibility(false);
    }

    private void setQuestionDataVisibility(boolean status)
    {
        answerLbl.setVisible(status);
        questionLbl.setVisible(status);
    }

    @FXML
    private void toggleMediaDisplay(ActionEvent event)
    {
        setQuestionDataVisibility(mediaDisplayToggleBtn.isSelected());
        if (!mediaDisplayToggleBtn.isSelected()) {
            reset();
            //loadMedia(standardQSet.getQuestion(current).getMedia());
        }
        //displayQuestionPresentation(standardQSet.getQuestion(current).getTitle());
    }

    @FXML
    private void getNextAction(ActionEvent event)
    {
        if (playerLoaded) {
            reset();
        }
        //Question question = standardQSet.getQuestion(++current);
        //displayQuestionData(question);
    }

    private void reset()
    {
        topBox.getChildren().remove(player);
        setQuestionDataVisibility(true);
        mediaDisplayToggleBtn.setSelected(false);
        mediaDisplayToggleBtn.setDisable(true);
        playerLoaded = false;
        player = null;
    }

    @FXML
    private void getPreviousAction(ActionEvent event)
    {
        if (playerLoaded) {
            reset();
        }
        //Question question = standardQSet.getQuestion(--current);
        //displayQuestionData(question);
    }

    @FXML
    private void setCorrectAction(ActionEvent event)
    {

    }

    @FXML
    private void setWrongAction(ActionEvent event)
    {

    }
}
