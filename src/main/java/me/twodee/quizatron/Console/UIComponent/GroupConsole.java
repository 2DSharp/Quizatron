package me.twodee.quizatron.Console.UIComponent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import me.twodee.quizatron.Component.Presentation;
import me.twodee.quizatron.Component.UIComponent;
import me.twodee.quizatron.Factory.StandardQSetFactory;
import me.twodee.quizatron.Model.Entity.Block;
import me.twodee.quizatron.Model.Entity.BlockSet;
import me.twodee.quizatron.Model.Entity.Group;
import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import me.twodee.quizatron.Model.Exception.UninitializedGroupException;
import me.twodee.quizatron.Model.Service.QuizDataService;
import me.twodee.quizatron.Model.Service.RoundService.GroupQSet;
import me.twodee.quizatron.Model.Service.RoundService.StandardQSet;
import me.twodee.quizatron.Presentation.View.BlockStage;
import me.twodee.quizatron.Presentation.View.QuestionDisplay;

import javax.inject.Inject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private BlockSet blockSet;
    List<Button> buttons = new ArrayList<>();
    private  QuestionConsole questionConsole;
    List<StackPane> blockList;
    List<Rectangle> blockBoxes;
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
        questionConsole = new QuestionConsole(standardQSet, quizDataService, presentation, false);
        this.setTop(questionConsole);
    }
    @FXML
    private void showBlockAction(ActionEvent event)
    throws NonExistentRecordException, IOException, ClassNotFoundException, URISyntaxException
    {
        presentation.changeView("block");
        BlockStage blockStage = presentation.getView();

        Group group = groupSet.fetch();
        blockStage.loadImage(quizDataService.constructURL(group.getImage()));

        if (blockList == null) {
            String file = getBlockFile(quizDataService.constructURL(group.getBlockFile()));
            blockStage.loadBlocks(file);
            blockList = blockStage.getBlocks();
            blockBoxes = blockStage.getBlockBoxes();
        }
        else {
            updateBlockSet(blockStage);
            blockStage.loadBlocks(blockList, blockBoxes);
        }
    }

    private void updateBlockSet(BlockStage blockStage) throws NonExistentRecordException
    {
        int index = standardQSet.fetch().getIndex();
        switch (standardQSet.getResult()) {
            case CORRECT:
                blockList.set(index, null);
                //blockStage.remove();
                //blockSet.setBlock(standardQSet.fetch().getIndex(), null);
                break;
            case WRONG:
                Rectangle rectangle = blockStage.disable(blockStage.disable(blockBoxes.get(index)));
                blockList.get(index).getChildren().clear();
                blockList.get(index).getChildren().add(rectangle);
                break;
        }
    }

    private String getBlockFile(String location) throws MalformedURLException, URISyntaxException
    {
        URL url = new URL(location);
        Path path = Paths.get(url.toURI());
        return path.toAbsolutePath().toString();
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
        questionDisplay.revealAnswer(groupSet.fetch().getAnswer(),
                                     quizDataService.constructURL(groupSet.fetch().getImage()),
                                     result);
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
