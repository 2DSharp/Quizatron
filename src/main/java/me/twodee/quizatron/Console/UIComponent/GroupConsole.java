package me.twodee.quizatron.Console.UIComponent;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import me.twodee.quizatron.Component.Presentation;
import me.twodee.quizatron.Component.UIComponent;
import me.twodee.quizatron.Factory.StandardQSetFactory;
import me.twodee.quizatron.Model.Entity.Group;
import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Exception.UninitializedGroupException;
import me.twodee.quizatron.Model.Service.QuizDataService;
import me.twodee.quizatron.Model.Service.RoundService.GroupQSet;
import me.twodee.quizatron.Model.Service.RoundService.StandardQSet;
import me.twodee.quizatron.Presentation.View.QuestionDisplay;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupConsole extends UIComponent
{
    private static final String USER_AGENT_STYLESHEET = QuestionConsole.class.getResource("/Stylesheets/groupconsole.css").toExternalForm();
    private GroupQSet groupSet;
    private FXMLLoader fxmlLoader;
    private StandardQSetFactory factory;
    private QuizDataService quizDataService;
    private Presentation presentation;
    private StandardQSet standardQSet;

    List<Button> buttons = new ArrayList<>();
    @FXML private Button showBlockBtn;
    @FXML private FlowPane switchContainer;
    @FXML private Button nextBtn;
    @FXML private Button backBtn;

    @Inject
    public GroupConsole(QuizDataService quizDataService,
                        GroupQSet groupQSet,
                        Presentation presentation) throws IOException
    {
        this.quizDataService = quizDataService;
        this.groupSet = groupQSet;
        this.presentation = presentation;

        fxmlLoader = initFXML("groupconsole.fxml");
        fxmlLoader.load();
    }

    public void initialize()
    {
        this.getStylesheets().add(USER_AGENT_STYLESHEET);
        groupSet.getStream().forEach(this::createSwitch);
        groupSet.toStart();
    }

    private void createSwitch(Group group)
    {
        Button button = new Button("" + (group.getIndex()));

        button.getStyleClass().add("selectorBtns");
        button.setOnAction(e -> selectBlockSet(group.getIndex()));
        FlowPane.setMargin(button, new Insets(0, 5, 0, 5));

        buttons.add(group.getIndex(), button);
        switchContainer.getChildren().add(button);
    }

    private void selectBlockSet(int index)
    {
        try {
            Group group = groupSet.fetch(index);
            getBlockData(group);
        }
        catch (NonExistentRecordException | IOException e) {
            e.printStackTrace();
        }
        catch (UninitializedGroupException e) {
            e.printStackTrace();
        }
    }

    private void getBlockData(Group group) throws IOException, UninitializedGroupException
    {
        standardQSet = groupSet.getService();
        QuestionConsole questionConsole = new QuestionConsole(standardQSet, quizDataService, presentation, false);
        this.setTop(questionConsole);
    }
    @FXML
    private void showBlockAction(ActionEvent event)
    {
        //standardQSet.fetch().getResult();
        // Show the blocks
    }

    @FXML
    private void correctAction(ActionEvent event) throws IOException, NonExistentRecordException
    {
        revealAnswer(QuestionDisplay.Result.CORRECT);
    }

    @FXML
    private void wrongAction(ActionEvent event) throws IOException, NonExistentRecordException
    {
        revealAnswer(QuestionDisplay.Result.WRONG);
    }

    private void revealAnswer(QuestionDisplay.Result result) throws NonExistentRecordException, IOException
    {
        presentation.changeView("questionview");
        QuestionDisplay questionDisplay = presentation.getView();
        questionDisplay.revealAnswer(groupSet.fetch().getAnswer(), result);
    }
    @FXML
    private void goForwardAction(ActionEvent event)
    throws NonExistentRecordException, IOException, UninitializedGroupException
    {
        groupSet.next();
        getBlockData(groupSet.fetch());
        updateBtns();
    }

    @FXML
    private void goBackwardAction(ActionEvent event)
    throws NonExistentRecordException, IOException, UninitializedGroupException
    {
        groupSet.previous();
        getBlockData(groupSet.fetch());
        updateBtns();
    }

    private void updateBtns()
    {
        nextBtn.setDisable(!groupSet.hasNext());
        backBtn.setDisable(!groupSet.hasPrevious());
    }

}
