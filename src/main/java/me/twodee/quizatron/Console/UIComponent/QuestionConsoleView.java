package me.twodee.quizatron.Console.UIComponent;

import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.binding.BooleanBinding;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import me.twodee.quizatron.Component.Presentation;
import me.twodee.quizatron.Component.UIComponent;
import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Service.QuizDataService;
import me.twodee.quizatron.Model.Service.RoundService.StandardQSet;
import me.twodee.quizatron.Presentation.View.QuestionDisplay;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    @FXML private JFXToggleButton showToggler;
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

    @FXML private BorderPane questionContainer;
    @FXML private HBox bottomHBox;
    @FXML private Button correctBtn;
    @FXML private Button wrongBtn;

    private int current;
    private QuizDataService quizDataService;
    private List<Button> buttons = new ArrayList<>();
    private QuestionDisplay questionDisplay;
    private boolean show;

    ImageView imageView;

    public QuestionConsoleView(StandardQSet standardQSet, QuizDataService quizDataService, Presentation presentation)
    throws IOException
    {
        this.presentation = presentation;
        this.quizDataService = quizDataService;
        this.standardQSet = standardQSet;
        this.fxmlLoader = initFXML("questionviewer.fxml");
        this.fxmlLoader.load();
    }

    public void initialize() throws NonExistentRecordException, MalformedURLException
    {
        this.getStylesheets().add(USER_AGENT_STYLESHEET);
        answerLbl.managedProperty().bind(answerLbl.visibleProperty());
        questionLbl.managedProperty().bind(questionLbl.visibleProperty());
        standardQSet.getStream()
                    .forEach(this::createButton);
        loadInitialQuestion();
    }

    private void loadInitialQuestion() throws NonExistentRecordException, MalformedURLException
    {
        standardQSet.toStart();
        Question question = standardQSet.fetch();
        displayQuestionData(question);
    }


    private void displayQuestionPresentation(Question question)
    {
        if (!showToggler.isSelected() || mediaDisplayToggleBtn.isSelected()) {
            return;
        }
        try {
            getQuestionDisplayControl();

            if (!question.getQuestionImage().isEmpty()) {
                questionDisplay.revealQuestion(question.getTitle(), quizDataService.constructURL(question.getQuestionImage()));
            }
            else {
                questionDisplay.revealQuestion(question.getTitle());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getQuestionDisplayControl() throws Exception
    {
        if (questionDisplay == null) {
            presentation.changeView("questionview");
            questionDisplay = presentation.getView();
        }
    }

    private void displayQuestionData(Question question) throws MalformedURLException
    {
        hardReset();
        if (!question.getQuestionImage().isEmpty()) {
            displayImage(question.getQuestionImage());
        }
        else {
            resetImageBox();
        }
        if (!question.getMedia().isEmpty()) {
            Media media = new Media(quizDataService.constructURL(question.getMedia()));
            mediaDisplayToggleBtn.setSelected(true);
            loadMedia(media);
        }
        else {
            reset();
        }
        questionLbl.setText(question.getTitle());
        answerLbl.setText(question.getAnswer());

        displayQuestionPresentation(question);
        setFocusOnBtn(question.getIndex());
        updateBtns();
    }

    private void updateBtns()
    {
        nextBtn.setDisable(!standardQSet.hasNext());
        prevBtn.setDisable(!standardQSet.hasPrevious());
    }
    private void resetImageBox()
    {
        imageView.setImage(null);
        imageView = null;
        questionContainer.setCenter(null);
    }
    private void displayImage(String file)
    {
        try {
            String url = quizDataService.constructURL(file);
            Image image = new Image(url);
            positionImage(image);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    private void positionImage(Image image)
    {
        if (imageView == null) {
            imageView = new ImageView(image);
            imageView.setFitHeight(300);
            imageView.setFitWidth(400);
            imageView.setPreserveRatio(true);
            questionContainer.setCenter(imageView);
        }
        else {
            imageView.setImage(image);
        }
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
        BooleanBinding toggleState = mediaDisplayToggleBtn.selectedProperty()
                                                          .and(mediaDisplayToggleBtn.disabledProperty().not());
        player.visibleProperty().bind(toggleState);
        player.managedProperty().bind(player.visibleProperty());
    }

    private void addPlayerToDisplay(Player player)
    {
        mediaDisplayToggleBtn.setDisable(false);
        //topBox.getChildren().add(player);
        questionContainer.setCenter(player);
    }


    @FXML
    private void toggleMediaDisplay(ActionEvent event) throws NonExistentRecordException, MalformedURLException
    {
        if (!mediaDisplayToggleBtn.isSelected()) {
            reset();
            questionDisplay = null;
            displayQuestionPresentation(standardQSet.fetch());
        }
        else {
            Media media = new Media(quizDataService.constructURL(standardQSet.fetch().getMedia()));
            loadMedia(media);
        }
    }

    @FXML
    private void getNextAction(ActionEvent event) throws NonExistentRecordException, MalformedURLException
    {
        if (playerLoaded) {
            reset();
        }
        standardQSet.next();
        Question question = standardQSet.fetch();
        displayQuestionData(question);
    }

    private void reset()
    {
        if (imageView != null) {
            questionContainer.setCenter(imageView);
        }
        mediaDisplayToggleBtn.setSelected(false);
        mediaDisplayToggleBtn.setDisable(true);
        playerLoaded = false;
        player = null;
    }

    @FXML
    private void getPreviousAction(ActionEvent event) throws NonExistentRecordException, MalformedURLException
    {
        if (playerLoaded) {
            reset();
        }
        standardQSet.previous();
        Question question = standardQSet.fetch();
        displayQuestionData(question);
    }

    @FXML
    private void setCorrectAction(ActionEvent event) throws NonExistentRecordException
    {
        if (showToggler.isSelected()) {
            disableAnswerBtns(true);
            questionDisplay.revealAnswer(standardQSet.fetch().getAnswer(), QuestionDisplay.Result.CORRECT);
        }
    }

    @FXML
    private void setWrongAction(ActionEvent event) throws NonExistentRecordException
    {
        if (showToggler.isSelected()) {
            disableAnswerBtns(true);
            questionDisplay.revealAnswer(standardQSet.fetch().getAnswer(), QuestionDisplay.Result.WRONG);
        }
    }

    private void disableAnswerBtns(boolean result)
    {
        correctBtn.setDisable(result);
        wrongBtn.setDisable(result);
    }

    private void hardReset()
    {
        disableAnswerBtns(false);
    }

    @FXML
    private void togglePresentationView(ActionEvent event) throws NonExistentRecordException
    {
        Question question = standardQSet.fetch();
        displayQuestionPresentation(question);
    }

    private void createButton(Question question)
    {
        Button button = new Button("" + (question.getId()));

        button.getStyleClass().add("selectorBtns");
        button.setOnAction(e -> selectQuestion(question.getIndex()));
        HBox.setMargin(button, new Insets(5));

        buttons.add(question.getIndex(), button);
        bottomHBox.getChildren().add(button);
    }

    private void selectQuestion(int index)
    {
        try {
            Question question = standardQSet.fetch(index);
            displayQuestionData(question);
            setFocusOnBtn(index);
        }
        catch (NonExistentRecordException e) {
            e.printStackTrace();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void setFocusOnBtn(int index)
    {
        buttons.get(current).pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        current = index;
        Button button = buttons.get(index);
        button.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
    }

}
