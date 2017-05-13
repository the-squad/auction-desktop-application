package app.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class EmptyState {

    private GridPane emptyStateContainer;
    private ImageView icon;
    private Label stateText;

    public EmptyState() {
        this.render();
    }

    private void render() {
        //Empty icon
        icon = new ImageView(new Image(getClass().getResourceAsStream("/assets/empty.png")));
        icon.setFitHeight(55);
        icon.setFitWidth(55);

        //Empty message
        stateText = new Label();
        stateText.setWrapText(true);
        stateText.setStyle("-fx-text-alignment: center");
        stateText.getStyleClass().add("headline");

        //Empty state container
        emptyStateContainer = new GridPane();
        emptyStateContainer.setMaxWidth(350);
        emptyStateContainer.setStyle("-fx-max-height: 150px");

        GridPane.setConstraints(icon, 0, 0);
        GridPane.setMargin(icon, new Insets(0, 0, 25, 0));

        GridPane.setConstraints(stateText, 0, 1);

        emptyStateContainer.getChildren().addAll(icon, stateText);

        icon.translateXProperty().bind(emptyStateContainer.widthProperty().subtract(icon.fitWidthProperty()).divide(2));
    }

    public void setEmptyMessage(String message) {
        stateText.setText(message);
    }

    public GridPane getEmptyState() {
        return emptyStateContainer;
    }
}
