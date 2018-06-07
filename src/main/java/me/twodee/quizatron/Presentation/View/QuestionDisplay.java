package me.twodee.quizatron.Presentation.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.twodee.quizatron.Presentation.IView;

import javax.swing.text.html.ImageView;

public class QuestionDisplay extends IView
{
    @FXML
    private Label titleLbl;
    @FXML
    private VBox leftBar;
    @FXML
    private ImageView logoView;

    public void setTitle(String title)
    {
        System.out.println(computeFontSize(title));
        titleLbl.setFont(Font.font(computeFontSize(title)));
        titleLbl.setText(title);
    }

    public void setLeftBar(Color color)
    {

    }
    private int computeFontSize(String text) 
    {
        int size = 24;
        int textLength = text.length();
        return size - foo(textLength, 2, 100, 200, 300, 350);
    }

    private int foo(int length, int factor, int... breakPoints)
    {
        int total = 0;

        for (int item : breakPoints) {
            if (length > item) {
                total += factor;
            }
        }
        return total;
    }
}