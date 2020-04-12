package com.game.UI;

import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import org.jetbrains.annotations.NotNull;

/**
 * A small window that is used to display a hint to the screen
 */
public class HintWindow {

    private Text hintView;
    private TextFlow layout;

    public HintWindow() {
        initializeLayout();
        addTextView();

    }

    public TextFlow getLayout() {
        return layout;
    }

    private void addTextView() {
        hintView = new Text();
        hintView.setStyle("-fx-font: 20 arial; -fx-text-fill: #ffffff;");
        layout.getChildren().add(hintView);
    }

    /**
     * Initialize the text flow layout used to wrap a text element
     */
    private void initializeLayout() {
        layout = new TextFlow();
        layout.setStyle("-fx-background-color: brown;");
        double height = 150;
        layout.setMaxHeight(height);
        double width = 150;
        layout.setMaxWidth(width);
        layout.setTextAlignment(TextAlignment.CENTER);
        layout.setVisible(false);
    }


    /**
     * Public method used to display a hint window by providing
     * the coordinates and the hint message
     *
     * @param hintMessage
     * @param x
     * @param y
     */
    public void showHint(@NotNull String hintMessage, double x, double y) {
        hintView.setText(hintMessage);
        layout.setLayoutX(x);
        layout.setLayoutY(y);
        layout.setVisible(true);


    }

    /**
     * Public method used to make the hint window invisible
     */
    public void hide() {
        layout.setVisible(false);
    }

}
