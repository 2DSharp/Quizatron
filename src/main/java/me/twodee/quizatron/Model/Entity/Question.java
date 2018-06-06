package me.twodee.quizatron.Model.Entity;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import me.twodee.quizatron.Model.Contract.IQuestion;

public class Question implements IQuestion {

    private String title;
    private String description;
    private String answer;
    private String image;
    private String media;
    private String type;
    private String homeDir;

    public Question(String homeDir, String title, String description, String answer, String image, String media) {

        this.homeDir = homeDir;
        this.title = title;
        this.description = description;
        this.answer = answer;
        this.image = image;
        this.media = media;
    }
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public Image getImage() {
        if (!image.isEmpty()) {
            return new Image(homeDir + image);
        }
        return null;
    }

    public Media getMedia() {

        if (media.isEmpty()) {
            return null;
        }
        return new Media(homeDir + media);
    }
}
