package me.twodee.quizatron.Presentation.View;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaView ;
import me.twodee.quizatron.Presentation.IView;

import javax.inject.Inject;


public class MediaPresentationView extends IView {

    @FXML private BorderPane rootNode;
    @Inject
    public MediaPresentationView() {

    }

    public void embedMediaView(MediaView mediaView) {
        rootNode.setCenter(mediaView);
    }
}
