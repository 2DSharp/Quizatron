package me.twodee.quizatron.Model.Entity;

import javafx.scene.image.Image;
import me.twodee.quizatron.Model.Contract.IQuestion;

public class Question implements IQuestion {

    private String title;
    private String description;
    private String answer;
    private String image;
    private String type;

    public Question(String title, String description, String answer, String image) {

        this.title = title;
        this.description = description;
        this.answer = answer;
        this.image = image;
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
        if (image.isEmpty()) {
            return new Image(image);
        }
        return null;
    }

}
