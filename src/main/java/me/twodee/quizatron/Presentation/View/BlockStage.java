package me.twodee.quizatron.Presentation.View;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import me.twodee.quizatron.Model.Entity.Block;
import me.twodee.quizatron.Model.Entity.BlockSet;
import me.twodee.quizatron.Model.Entity.QuizData;
import me.twodee.quizatron.Presentation.IView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class BlockStage extends IView {

    @FXML
    AnchorPane root;
    List<StackPane> blocks = new ArrayList<>();
    List<Rectangle> blockBoxes = new ArrayList<>();

    private static final ObjectReader objectReader;

    static {
        ObjectMapper objectMapper = new ObjectMapper();
        objectReader = objectMapper.reader().forType(BlockSet.class);
    }

    public void loadImage(String fileName) {
        StackPane stackPane = new StackPane();
        Image image = new Image(fileName);
        ImageView imageView = new ImageView(image);
        Canvas canvas = new Canvas();

        imageView.setFitHeight(560);
        imageView.setPreserveRatio(true);
        double ratio = image.getHeight() / 560;

        canvas.setWidth(image.getWidth() / ratio);
        root.setMaxWidth(image.getWidth() / ratio);
        canvas.setHeight(560);
        stackPane.getChildren().addAll(imageView, canvas);

        root.getChildren().addAll(stackPane);
        root.setPrefWidth(image.getWidth() / ratio);

        root.setMaxHeight(560);
        root.setVisible(true);
    }

    public BlockSet loadBlocks(String file) throws IOException, ClassNotFoundException {

        BlockSet blockSet = readBlockset(file);
        loadInitialBlocks(blockSet);
        return blockSet;
    }

    public void loadInitialBlocks(BlockSet blockSet) {
        for (int i = 0; i < 5; i++) {
            Block block = blockSet.getBlock(i);
            Rectangle rectangle = new Rectangle();
            decorateRect(rectangle, block);
            blockBoxes.add(i, rectangle);
            StackPane stackPane = new StackPane();
            stackPane.setLayoutX(block.getX());
            stackPane.setLayoutY(block.getY());
            blocks.add(i, stackPane);
            Label text = new Label("" + (i + 1));
            text.setFont(new Font(34));
            text.setStyle("-fx-text-fill: white");
            stackPane.getChildren().addAll(rectangle, text);
            root.getChildren().add(stackPane);

        }
    }

    public void loadBlocks(List<StackPane> blockList, List<Rectangle> blockBoxes) {
        for (int i = 0; i < 5; i++) {

            if (blockList.get(i) != null) {
                Rectangle rectangle = blockBoxes.get(i);
                Label text = new Label("" + (i + 1));
                text.setFont(new Font(34));
                text.setStyle("-fx-text-fill: white");
//                blockList.get(i).getChildren().addAll(rectangle, text);
                root.getChildren().add(blockList.get(i));
            }
        }
    }

    private void decorateRect(Rectangle rectangle, Block block) {
        rectangle.setHeight(block.getHeight());
        rectangle.setWidth(block.getWidth());
        rectangle.setX(block.getX());
        rectangle.setY(block.getY());
        rectangle.setFill(Paint.valueOf(block.getColor()));
        rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeWidth(8);

    }

    public BlockSet readBlockset(String fileName) throws IOException {
        FileInputStream inputStream = new FileInputStream(fileName);
        BlockSet blockSet = objectReader.readValue(inputStream);
        inputStream.close();
        return blockSet;
    }

    public List<StackPane> getBlocks() {
        return blocks;
    }

    public List<Rectangle> getBlockBoxes() {
        return blockBoxes;
    }

    public void remove(int i) {
        root.getChildren().remove(blocks.get(i));
    }

    public Rectangle disable(Rectangle rectangle) {
        rectangle.setFill(Color.GRAY);
        return rectangle;
    }
}
