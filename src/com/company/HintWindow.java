package com.company;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import org.jetbrains.annotations.NotNull;

public class HintWindow {

    private Text hintView;
    private TextFlow layout;

    public HintWindow(@NotNull Pane root) {
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


    public void showHint(@NotNull String hintMessage, double x, double y) {
        hintView.setText(hintMessage);
        layout.setLayoutX(x);
        layout.setLayoutY(y);
        layout.setVisible(true);


    }

    public void hide() {
        layout.setVisible(false);
    }

}
