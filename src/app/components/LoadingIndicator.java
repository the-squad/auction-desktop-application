/*
 * The MIT License
 *
 * Copyright 2017 Contributors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package app.components;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;

public class LoadingIndicator {

    private GridPane loadingIndicatorContainer;
    private Label loadingMessage;
    private ImageView loadingIndicator;

    private RotateTransition rotatingAnimation;

    public LoadingIndicator() {
        this.render();
    }

    private void render() {
        //Loading message
        loadingMessage = new Label("Getting your data");
        loadingMessage.getStyleClass().add("headline");

        //Loading indicator
        loadingIndicator = new ImageView(new Image(getClass().getResourceAsStream("/assets/loading.png")));
        loadingIndicator.setFitHeight(55);
        loadingIndicator.setFitWidth(55);


        //Loading state container
        loadingIndicatorContainer = new GridPane();
        loadingIndicatorContainer.getStyleClass().add("loading");
        loadingIndicatorContainer.setMaxHeight(130);

        GridPane.setConstraints(loadingMessage, 0, 0);
        GridPane.setMargin(loadingMessage, new Insets(0, 0, 25, 0));

        GridPane.setConstraints(loadingIndicator, 0, 1);

        loadingIndicatorContainer.getChildren().addAll(loadingMessage, loadingIndicator);

        loadingMessage.translateXProperty().bind(loadingIndicatorContainer.widthProperty().subtract(loadingMessage.widthProperty()).divide(2));
        loadingIndicator.translateXProperty().bind(loadingIndicatorContainer.widthProperty().subtract(loadingIndicator.fitWidthProperty()).divide(2));
        this.rotateIndicator();
    }

    private void rotateIndicator() {
        rotatingAnimation = new RotateTransition(Duration.millis(600), loadingIndicator);

        rotatingAnimation.setByAngle(360);
        rotatingAnimation.setCycleCount(100);
        rotatingAnimation.setInterpolator(Interpolator.LINEAR);

        rotatingAnimation.play();
    }

    public GridPane getLoadingIndicator() {
        return loadingIndicatorContainer;
    }

    public void setLoadingMessage(String message) {
        loadingMessage.setText(message);
    }
}
