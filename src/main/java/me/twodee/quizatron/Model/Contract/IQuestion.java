package me.twodee.quizatron.Model.Contract;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

public interface IQuestion {

    String getTitle();
    String getAnswer();
    Media getMedia();
    Image getImage();
}
