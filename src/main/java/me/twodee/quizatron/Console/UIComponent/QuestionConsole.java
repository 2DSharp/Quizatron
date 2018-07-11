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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import me.twodee.quizatron.Presentation.Presentation;
import me.twodee.quizatron.Component.UIComponent;
import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Service.QuizDataService;
import me.twodee.quizatron.Model.Service.RoundService.StandardQSet;
import me.twodee.quizatron.Presentation.View.QuestionDisplay;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Question control center for Quizatron
 *
 * @author Dedipyaman Das <2d@twodee.me>
 * @version 1.0.18.1
 * @since 1.0.18.1
 */
public class QuestionConsole extends UIComponent
{
    private static final String USER_AGENT_STYLESHEET = QuestionConsole.class.getResource("/Stylesheets/question_viewer.css").toExternalForm();
    ImageView imageView;
    private StandardQSet standardQSet;
    private FXMLLoader fxmlLoader;
    private Presentation presentation;
    private Player player;
    private boolean playerLoaded;
    private boolean showPreview = true;
    @FXML
    private JFXToggleButton showToggler;
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
    @FXML
    private BorderPane questionContainer;
    @FXML
    private HBox bottomHBox;
    @FXML
    private Button correctBtn;
    @FXML
    private Button wrongBtn;
    @FXML private FlowPane switchContainer;

    private int current;
    private QuizDataService quizDataService;
    private List<Button> buttons = new ArrayList<>();
    private QuestionDisplay questionDisplay;
    private String mediaName;
    private String secImage;

    public QuestionConsole(StandardQSet standardQSet, QuizDataService quizDataService, Presentation presentation,boolean showPreview)
    throws IOException
    {
        this.presentation = presentation;
        this.quizDataService = quizDataService;
        this.standardQSet = standardQSet;
        setShowPreview(showPreview);
        this.fxmlLoader = initFXML("questionviewer.fxml");
        this.fxmlLoader.load();
    }

    public void setSecondaryImage(String image)
    {
        secImage = image;
    }
    public void initialize() throws NonExistentRecordException, IOException
    {
        this.getStylesheets().add(USER_AGENT_STYLESHEET);
        answerLbl.managedProperty().bind(answerLbl.visibleProperty());
        questionLbl.managedProperty().bind(questionLbl.visibleProperty());
        standardQSet.getStream().forEach(this::createButton);
        loadInitialQuestion();
    }

    public void setShowPreview(boolean status)
    {
        showPreview = status;
    }
    private void loadInitialQuestion() throws NonExistentRecordException, IOException
    {
        standardQSet.toStart();
        Question question = standardQSet.fetch();
        displayQuestionData(question);
    }

    private void displayQuestionData(Question question) throws IOException
    {
        disableAnswerBtns(false);

        displayImage(question.getQuestionImage());
        displayMedia(question.getMedia());
        displayQnA(question);

        setFocusOnIndicator(question.getIndex());
        updateNavBtns();

        displayQuestionPresentation(question);
    }

    private void displayImage(String file) throws MalformedURLException
    {
        if (showPreview ) {
            if (file.isEmpty()) {
                resetImageBox();
                return;
            }
            String url = quizDataService.constructURL(file);
            Image image = new Image(url);
            addImageToDisplay(image);
        }
    }

    private void resetImageBox()
    {
        if (imageView != null) {
            imageView.setImage(null);
            imageView = null;
            questionContainer.setCenter(null);
        }
    }

    private void addImageToDisplay(Image image)
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

    private void displayMedia(String file) throws IOException
    {
        if (file.isEmpty()) {
            resetMediaBox();
            return;
        }
        Media media = new Media(quizDataService.constructURL(file));
        mediaDisplayToggleBtn.setDisable(false);
        mediaDisplayToggleBtn.setSelected(true);
        mediaName = file;
        loadMedia(media, file);
    }

    private void resetMediaBox()
    {
        questionContainer.setCenter(imageView);
        mediaDisplayToggleBtn.setSelected(false);
        playerLoaded = false;
        player = null;
    }

    private void loadMedia(Media media, String file) throws IOException
    {
        player = new Player(presentation);
        String extension = player.getExtension(file);
        initPlayer(player, media, extension);
        addPlayerToDisplay(player);
        playerLoaded = true;
    }

    private void initPlayer(Player player, Media media, String extension)
    {
        player.loadMedia(media, extension);
        BooleanBinding toggleState = mediaDisplayToggleBtn.selectedProperty().and(mediaDisplayToggleBtn.disabledProperty().not());
        player.visibleProperty().bind(toggleState);
        player.managedProperty().bind(player.visibleProperty());
    }

    private void addPlayerToDisplay(Player player)
    {
        mediaDisplayToggleBtn.setDisable(false);
        questionContainer.setCenter(player);
    }

    private void displayQnA(Question question)
    {
        questionLbl.setText(question.getTitle());
        answerLbl.setText(question.getAnswer());
    }

    private void displayQuestionPresentation(Question question) throws IOException
    {
        if (!showToggler.isSelected() || mediaDisplayToggleBtn.isSelected()) {
            return;
        }
        getDisplay();
        revealQuestion(question);
    }

    private void getDisplay() throws IOException
    {
        if (questionDisplay == null || presentation.getView() != questionDisplay) {
            presentation.changeView("questionview");
            questionDisplay = presentation.getView();
        }
    }

    private void revealQuestion(Question question) throws MalformedURLException
    {
        if (secImage != null) {
            questionDisplay.displaySecondaryImg(secImage);
        }
        if (!question.getQuestionImage().isEmpty()) {
            questionDisplay.revealQuestion(question.getTitle(), quizDataService.constructURL(question.getQuestionImage()));
        }
        else {
            questionDisplay.revealQuestion(question.getTitle());
        }
    }

    private void updateNavBtns()
    {
        nextBtn.setDisable(!standardQSet.hasNext());
        prevBtn.setDisable(!standardQSet.hasPrevious());
    }

    @FXML
    private void toggleMediaDisplay(ActionEvent event) throws NonExistentRecordException, IOException
    {
        if (mediaDisplayToggleBtn.isSelected()) {
            Media media = new Media(quizDataService.constructURL(standardQSet.fetch().getMedia()));
            loadMedia(media, mediaName);
        }
        else {
            resetMediaBox();
            questionDisplay = null;
            displayQuestionPresentation(standardQSet.fetch());
        }
    }

    @FXML
    private void getNextAction(ActionEvent event) throws NonExistentRecordException, IOException
    {
        standardQSet.next();
        displayCurrentQuestion();
    }

    @FXML
    private void getPreviousAction(ActionEvent event) throws NonExistentRecordException, IOException
    {
        standardQSet.previous();
        displayCurrentQuestion();
    }

    private void displayCurrentQuestion() throws NonExistentRecordException, IOException
    {
        mediaDisplayToggleBtn.setDisable(true);
        resetMediaBox();
        Question question = standardQSet.fetch();
        displayQuestionData(question);
    }
    @FXML
    private void setCorrectAction(ActionEvent event) throws NonExistentRecordException, MalformedURLException
    {
        playMusicFX(StandardQSet.Result.CORRECT);
        standardQSet.setResult(StandardQSet.Result.CORRECT);
        revealAnswer(QuestionDisplay.Result.CORRECT);
    }

    @FXML
    private void setWrongAction(ActionEvent event) throws NonExistentRecordException, MalformedURLException
    {
        playMusicFX(StandardQSet.Result.WRONG);
        standardQSet.setResult(StandardQSet.Result.WRONG);
        revealAnswer(QuestionDisplay.Result.WRONG);
    }

    private void playMusicFX(StandardQSet.Result result) throws MalformedURLException
    {
        String url;

        switch (result) {
            case CORRECT:
                url = "/sounds/correct.mp3";
                break;
            default:
                url = "/sounds/wrong.mp3";
                break;
        }

        Media media = new Media(quizDataService.constructURL( url));
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }


    private void revealAnswer(QuestionDisplay.Result result) throws NonExistentRecordException, MalformedURLException
    {
        if (showToggler.isSelected()) {
            disableAnswerBtns(true);
            Question question = standardQSet.fetch();
            if (question.getAnsImage().isEmpty()) {
                questionDisplay.revealAnswer(question.getAnswer(), result);
            }
            else {
                questionDisplay.revealAnswer(question.getAnswer(), quizDataService.constructURL(question.getAnsImage()), result);
            }
        }
    }

    private void disableAnswerBtns(boolean result)
    {
        correctBtn.setDisable(result);
        wrongBtn.setDisable(result);
    }

    @FXML
    private void togglePresentationView(ActionEvent event) throws NonExistentRecordException, IOException
    {
        Question question = standardQSet.fetch();
        displayQuestionPresentation(question);
    }

    private void createButton(Question question)
    {
        Button button = new Button("" + (question.getId()));

        button.getStyleClass().add("selectorBtns");
        button.setOnAction(e -> selectQuestion(question.getIndex()));
        FlowPane.setMargin(button, new Insets(5));

        buttons.add(question.getIndex(), button);
        switchContainer.getChildren().add(button);
    }

    private void selectQuestion(int index)
    {
        try {
            Question question = standardQSet.fetch(index);
            displayQuestionData(question);
        }
        catch (NonExistentRecordException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setFocusOnIndicator(int index)
    {
        buttons.get(current).pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        current = index;
        Button button = buttons.get(index);
        button.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
    }

}
