package me.twodee.quizatron.Model.Contract;

import javafx.scene.image.Image;

public interface IQuestion {

    String getTitle();
    String getDescription();
    String getAnswer();
    Image getImage();
}
