package me.twodee.quizatron.Console.Dashboard;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import me.twodee.quizatron.Presentation.Presentation;
import me.twodee.quizatron.Presentation.PresentationFactory;
import me.twodee.quizatron.Presentation.IView;
import me.twodee.quizatron.Model.Score;
import me.twodee.quizatron.Presentation.View.HomeView;

import javax.inject.Inject;

public class DashboardPresenter {

    private Score score;
    private PresentationFactory presentationFactory;
    private Presentation presentation;
    private IView view;

    @Inject
    public DashboardPresenter(Score score, PresentationFactory presentationFactory) throws Exception {
        this.score = score;
        this.presentationFactory = presentationFactory;
        this.presentation = this.presentationFactory.create();
    }

    @FXML
    public void startQuiz(MouseEvent event) {

        presentation.show();
        this.view = presentation.getView();
    }

    @FXML
    public void openMedia(MouseEvent event) {
        try {
            this.presentation = makeNewPresentation("secondView");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Presentation makeNewPresentation(String viewFxml) throws Exception {
        return this.presentationFactory.create(
                presentation.getStage(),
                presentation.getScene(),
                "secondview");

    }
    public void showScore() {

        this.score.setScore(100);
        HomeView view = (HomeView)this.view;
        view.updateScore(this.score.getScore());
    }
}
