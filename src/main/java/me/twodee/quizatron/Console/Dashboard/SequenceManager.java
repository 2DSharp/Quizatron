package me.twodee.quizatron.Console.Dashboard;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import me.twodee.quizatron.Component.Presentation;
import me.twodee.quizatron.Console.UIComponent.Card;
import me.twodee.quizatron.Console.UIComponent.Player;
import me.twodee.quizatron.Console.UIComponent.QuestionConsoleView;
import me.twodee.quizatron.Component.UIComponent;
import me.twodee.quizatron.Factory.StandardQSetFactory;
import me.twodee.quizatron.Model.Entity.Sequence;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Service.QuizDataService;
import me.twodee.quizatron.Model.Service.RoundService.StandardQSet;
import me.twodee.quizatron.Model.Service.SequenceService;
import me.twodee.quizatron.Presentation.View.HomeView;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class SequenceManager extends UIComponent
{
    @FXML private Label seqName;
    @FXML private Label seqFile;
    @FXML private Label seqNextName;
    @FXML private FlowPane cardsContainer;
    @FXML private ScrollPane scrollPane;
    @FXML private Button prevSeqBtn;
    @FXML private Button nextSeqBtn;
    @FXML private Button stepForwardBtn;
    @FXML private Button stepBackwardBtn;
    @FXML private Button playPauseBtn;

    Sequence sequence;

    private static final String USER_AGENT_STYLESHEET = QuestionConsoleView.class
            .getResource("/Stylesheets/sequence.css")
            .toExternalForm();

    private FXMLLoader fxmlLoader;
    private SequenceService sequenceService;
    private QuizDataService quizDataService;
    private List<Card> cards = new ArrayList<>();
    private int current;
    private int steps;
    private int currStep;

    private StandardQSetFactory standardQSetFactory;
    private Presentation presentation;

    public SequenceManager(SequenceService sequenceService,
                           QuizDataService quizDataService,
                           StandardQSetFactory standardQSetFactory,
                           Presentation presentation) throws IOException
    {
        this.quizDataService = quizDataService;
        this.sequenceService = sequenceService;
        this.standardQSetFactory = standardQSetFactory;
        this.presentation = presentation;
        fxmlLoader = initFXML("sequencer.fxml");
        fxmlLoader.load();
    }

    public void initialize() throws IOException, NonExistentRecordException
    {
        this.getStylesheets().add(USER_AGENT_STYLESHEET);
        displayServiceData();
    }

    private void displayServiceData() throws IOException, NonExistentRecordException
    {
        try {
            sequenceService.load();
            sequenceService.getSequenceAsStream()
                           .forEach(this::displayCard);
            displayCurrentSequence();
        }
        catch (NullPointerException e) {
            displayErrorMessage();
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void displayCard(Sequence sequence)
    {
        try {
            Card card = new Card(sequence.getName(), sequence.getFilePath(), sequence.getType());

            cards.add(sequence.getIndex(), card);
            card.setOnMouseClicked(e -> selectCard(sequence.getIndex()));
            card.setPrefWidth(cardsContainer.getPrefWidth());

            cardsContainer.getChildren().add(card);
            FlowPane.setMargin(card, new Insets(7));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void focusCard(int index)
    {
        cards.get(current).pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        current = index;
        Card card = cards.get(index);
        card.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);

        scrollPane.setVvalue(getScrollFactor(card));
    }

    private double getScrollFactor(Card card)
    {
        double yFactor = card.getLayoutY();
        double bottom = card.getHeight() + card.getLayoutY();

        if (bottom > scrollPane.getHeight()) {
            yFactor = bottom;
        }
        return yFactor / cardsContainer.getHeight();
    }

    private void displayCurrentSequence() throws NonExistentRecordException, IOException
    {
        pause();
        sequence = sequenceService.fetchSequence();
        currStep = 0;
        displaySeqMetaData(sequence);
        focusCard(sequence.getIndex());
        updateNavBtns();
        runSteps(sequence.getType());
    }

    private void displaySeqMetaData(Sequence sequence)
    {
        seqName.setText(sequence.getName());
        seqFile.setText(sequence.getFilePath());
    }
    private void selectCard(int index)
    {
        if (current != index) {
            showSeqByID(index);
            focusCard(index);
        }
    }

    private void showSeqByID(int id)
    {
        try {
            sequenceService.fetchSequence(id);
            displayCurrentSequence();
        }
        catch (NonExistentRecordException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void stepForward(ActionEvent event)
    {
        currStep++;
        System.out.println(currStep);;
        runSteps(sequence.getType());
    }

    @FXML
    private void stepBackward(ActionEvent event)
    {
        currStep--;
        runSteps(sequence.getType());
    }
    private void runSteps(String type)
    {
        stepBackwardBtn.setDisable(false);

        if (currStep < 1) {
            stepBackwardBtn.setDisable(true);
        }

        switch (type) {
            case "round":
                displayRound(sequence);
                break;
            case "video":
                displayVideo(sequence.getIntro());
                stepForwardBtn.setDisable(true);
                break;
        }
    }

    private void displayRound(Sequence sequence)
    {
        switch (currStep)
        {
            case 0:
                displayVideo(sequence.getIntro());
                break;
            case 1:
                displayQuestionView();
                break;
        }
    }

    private void displayVideo(String intro)
    {
        Media media = null;
        try {
             media = new Media(quizDataService.constructURL(intro));
        }
        catch (MalformedURLException e) {

        }

        try {
            Player player = new Player(presentation);
            if (media != null) {
                player.loadMedia(media);
            }
            this.setCenter(player);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayQuestionView()
    {
        try {
            StandardQSet standardQSet = standardQSetFactory.create(quizDataService.getInitialDirectory() + "/" + sequence.getFilePath());
            QuestionConsoleView questionConsoleView = new QuestionConsoleView(standardQSet, quizDataService, presentation);
            this.setCenter(questionConsoleView);
        }

        catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void showNextSeq(ActionEvent event) throws NonExistentRecordException, IOException
    {
        sequenceService.fetchNext();
        displayCurrentSequence();
    }

    @FXML
    private void showPrevSeq(ActionEvent event) throws NonExistentRecordException, IOException
    {
        sequenceService.fetchPrevious();
        displayCurrentSequence();
    }
    private void updateNavBtns()
    {
        if (sequence != null) {
            playPauseBtn.setDisable(false);
            nextSeqBtn.setDisable(!sequenceService.hasNext());
            prevSeqBtn.setDisable(!sequenceService.hasPrev());
            stepBackwardBtn.setDisable(false);
            stepForwardBtn.setDisable(false);
        }
    }

    private void displayErrorMessage()
    {
        Label defaultText = new Label("No configuration file has been set. Select a json configuration file " +
                                              " or open a save file from the left panel to start a quiz.");
        defaultText.setWrapText(true);
        defaultText.setAlignment(Pos.CENTER);
        defaultText.setTextAlignment(TextAlignment.CENTER);
        defaultText.setFont(Font.font("Open Sans"));
        defaultText.setStyle("-fx-font-size: 20; -fx-padding: 20px");
        this.setCenter(defaultText);
    }

    private void pause() throws IOException
    {
        presentation.changeView("home");
        HomeView homeView = presentation.getView();
        homeView.decorate(quizDataService);
    }

    @FXML
    private void pause(ActionEvent event) throws IOException
    {
        pause();
    }
}
