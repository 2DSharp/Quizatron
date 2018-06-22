package me.twodee.quizatron.Model.Entity;

import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Block implements Serializable {

    private String color;
    private double x;
    private double y;
    private double width;
    private double height;

    public Block(Rectangle rectangle) {

        this.x = rectangle.getX();
        this.y = rectangle.getY();
        this.width = rectangle.getWidth();
        this.height = rectangle.getHeight();
        this.color = rectangle.getFill().toString();
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public String getColor() {
        return color;
    }

    public double getHeight() {
        return height;
    }
}
