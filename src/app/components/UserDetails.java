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

import app.Navigator;
import app.views.ProfilePage;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import models.ImageUtils;
import models.Seller;

import java.awt.image.BufferedImage;

import static app.Partials.FIT_CONTAINER;
import static app.Partials.PROFILE_PAGE;

public class UserDetails {

    private final int paddingType;
    private String userName = "User name";
    private Image photo = null;
    private int userId;

    private GridPane sellerDetailsContainer;
    private Rectangle photoViewer;
    private Label username;

    public UserDetails(int paddingType) {
        this.paddingType = paddingType;
        this.userName = "Seller Name";
        this.render();
    }

    public UserDetails(int paddingType, String userName, BufferedImage photo, int userId) {
        this.paddingType = paddingType;
        this.userName = userName;
        this.photo = ImageUtils.cropAndConvertImage(photo, 30, 30);
        this.userId = userId;
        this.render();
    }

    private void render() {
        //User picture
        photoViewer = new Rectangle(30, 30);
        if (photo != null)
            photoViewer.setFill(new ImagePattern(photo));
        else
            photoViewer.setFill(Color.rgb(245,248,250));
        photoViewer.setArcHeight(4);
        photoViewer.setArcWidth(4);
        photoViewer.getStyleClass().add("user-picture");

        //User name
        username = new Label(userName);
        username.getStyleClass().add("user-name");

        //User details container
        sellerDetailsContainer = new GridPane();
        sellerDetailsContainer.getStyleClass().add("user-container");
        if (paddingType == FIT_CONTAINER) {
            sellerDetailsContainer.getStyleClass().add("bottom-borders");
            sellerDetailsContainer.setPadding(new Insets(10, 15, 10, 15));
            sellerDetailsContainer.setMinWidth(250);
        } else {
            sellerDetailsContainer.getStyleClass().add("all-borders");
            sellerDetailsContainer.setPadding(new Insets(4, 15, 4, 4 ));
        }

        sellerDetailsContainer.setOnMouseClicked(me -> {
            Navigator.viewPage(PROFILE_PAGE, username.getText());
            ProfilePage.getInstance().fillUserData(Seller.getSellerData(userId));
        });

        GridPane.setConstraints(photoViewer, 0, 0);
        GridPane.setMargin(photoViewer, new Insets(0, 14, 0, 0));

        GridPane.setConstraints(username, 1, 0);

        sellerDetailsContainer.getChildren().addAll(photoViewer, username);
    }

    public void setUserDetails(String userName, BufferedImage photo, int userId) {
        username.setText(userName);
        photoViewer.setFill(new ImagePattern(ImageUtils.cropAndConvertImage(photo, 30, 30)));
        this.userId = userId;
    }

    public GridPane getUserDetails() {
        return sellerDetailsContainer;
    }
}
