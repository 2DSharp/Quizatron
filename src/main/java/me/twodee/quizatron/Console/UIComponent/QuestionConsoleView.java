package me.twodee.quizatron.Console.UIComponent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Exception.NoQuestionLeftException;
import me.twodee.quizatron.Model.Service.QuestionSetService;

import java.io.IOException;

public class QuestionConsoleView extends BorderPane {

    private QuestionSetService questionSetService;

    private FXMLLoader fxmlLoader;
    @FXML private Button nextBtn;
    @FXML private Button prevBtn;
    @FXML private Label questionLbl;
    @FXML private Label descriptionLbl;
    @FXML private Label answerLbl;

    private static final String USER_AGENT_STYLESHEET = QuestionConsoleView.class.getResource("/Stylesheets/question_viewer.css").toExternalForm();

    public QuestionConsoleView(QuestionSetService questionSetService) throws IOException {

        this.questionSetService = questionSetService;
        this.fxmlLoader = initFXML();
        this.fxmlLoader.load();

        loadInitialQuestion();

    }

    public void initialize() {
        this.getStylesheets().add(USER_AGENT_STYLESHEET);
    }

    private FXMLLoader initFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("questionviewer.fxml"));
        //fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        return fxmlLoader;
    }

    private void loadInitialQuestion() {
        Question question = questionSetService.getQuestion();
        displayQuestionData(question);
    }

    private void displayQuestionData(Question question) {
        questionLbl.setText(question.getTitle());
        descriptionLbl.setText(question.getDescription());
        answerLbl.setText(question.getAnswer());
        if (questionSetService.hasNext()) {
            nextBtn.setDisable(false);
        }
        else {
            nextBtn.setDisable(true);
        }
    }

    @FXML
    private void getNextAction(ActionEvent event) {
        try {
            Question question = questionSetService.nextQuestion();
            displayQuestionData(question);
        }
        catch (NoQuestionLeftException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void getPreviousAction(ActionEvent event) {

    }

    @FXML
    private void setCorrectAction(ActionEvent event) {

    }

    @FXML
    private void setWrongAction(ActionEvent event) {

    }
}
