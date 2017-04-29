/*
 * The MIT License
 *
 * Copyright 2017 Muhammad.
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

import app.Navigator;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import static app.Partials.FULL_PADDING;
import static app.Partials.PROFILE_PAGE;

public class SellerDetails {

    private final int paddingType;

    private GridPane sellerDetailsContainer;
    private Rectangle userPicture;
    private Label username;

    public SellerDetails(int paddingType) {
        this.paddingType = paddingType;
        this.render();
    }

    private void render() {
        //User picture
        userPicture = new Rectangle(30, 30,
                new ImagePattern((
                        new Image(getClass().getResourceAsStream("/assets/picture.jpg"),
                                30,
                                30,
                                true,
                                true))));
        userPicture.setArcHeight(4);
        userPicture.setArcWidth(4);
        userPicture.getStyleClass().add("user-picture");

        // FIXME change this to image view

        //User name
        username = new Label("Muhammad Tarek");
        username.getStyleClass().add("user-name");

        //User details container
        sellerDetailsContainer = new GridPane();
        sellerDetailsContainer.getStyleClass().add("user-container");
        if (paddingType == FULL_PADDING) {
            sellerDetailsContainer.getStyleClass().add("bottom-borders");
            sellerDetailsContainer.setPadding(new Insets(10, 15, 10, 15));
            sellerDetailsContainer.setMinWidth(250);
        } else {
            sellerDetailsContainer.getStyleClass().add("all-borders");
            sellerDetailsContainer.setPadding(new Insets(4, 15, 4, 4 ));
        }

        sellerDetailsContainer.setOnMouseClicked(me -> Navigator.viewPage(PROFILE_PAGE, username.getText()));

        GridPane.setConstraints(userPicture, 0, 0);
        GridPane.setMargin(userPicture, new Insets(0, 14, 0, 0));

        GridPane.setConstraints(username, 1, 0);

        sellerDetailsContainer.getChildren().addAll(userPicture, username);
    }

    public GridPane getSellerDetails() {
        return sellerDetailsContainer;
    }
}
