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
package app.views;

import app.components.InputField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import models.ImageUtils;

import static app.Partials.*;

public class AccountSettings {

    private static AccountSettings instance;

    private ScrollPane accountSettingsPageContainer;
    private BorderPane cardParent;
    private GridPane cardContainer;
    private GridPane photoContainer;
    private Image userPhoto;
    private Rectangle photoClipper;
    private Button changePhotoButton;

    private GridPane formDataContainer;
    private InputField nameField;
    private InputField emailField;
    private InputField passwordField;
    private InputField repeatPasswordField;
    private InputField addressField;

    private GridPane buttonAndNotifierContainer;
    private Button updateAccountButton;
    private Label updateActionNotifier;
    private Label extraSpace;

    private AccountSettings() {
        this.render();
    }

    private void render() {
        //User photo
        userPhoto = ImageUtils.cropAndConvertImage(currentUser.getPhoto(), 200, 200);

        photoClipper = new Rectangle(200, 200);
        photoClipper.setFill(new ImagePattern(userPhoto));
        photoClipper.setArcHeight(16);
        photoClipper.setArcWidth(16);

        //Change photo button
        changePhotoButton = new Button("Change photo");
        changePhotoButton.getStyleClass().add("btn-secondary");
        changePhotoButton.setMinWidth(200);

        //Account data fields
        nameField = new InputField("Name", TEXT, LONG);
        nameField.setValue(currentUser.getName());

        emailField = new InputField("Email", EMAIL, LONG);
        emailField.setValue(currentUser.getEmail());

        passwordField = new InputField("Password", PASSWORD, LONG);
        passwordField.setValue(currentUser.getPassword());

        repeatPasswordField = new InputField("Repeat Password", PASSWORD, LONG);
        addressField = new InputField("Address", ADDRESS, LONG);
        addressField.setValue(currentUser.getAddress());

        updateAccountButton = new Button("Update");
        updateAccountButton.getStyleClass().add("btn-primary");

        updateAccountButton.setOnAction(e -> {
            updateActionNotifier.setVisible(true);
            //TODO
        });

        updateActionNotifier = new Label("Account updated successfully");
        updateActionNotifier.getStyleClass().add("text-notifier");
        updateActionNotifier.setVisible(false);

        //Button and notifier label container
        buttonAndNotifierContainer = new GridPane();

        GridPane.setConstraints(updateAccountButton, 0, 0);
        GridPane.setConstraints(updateActionNotifier, 1, 0);
        GridPane.setMargin(updateActionNotifier, new Insets(0,0,0,20));

        buttonAndNotifierContainer.getChildren().addAll(updateAccountButton, updateActionNotifier);

        //Photo container
        photoContainer = new GridPane();
        photoContainer.setAlignment(Pos.TOP_CENTER);

        GridPane.setConstraints(photoClipper, 0, 0);
        GridPane.setMargin(photoClipper, new Insets( 10, 0, 10, 0));

        GridPane.setConstraints(changePhotoButton, 0, 1);

        photoContainer.getChildren().addAll(photoClipper, changePhotoButton);

        //Data form container
        formDataContainer = new GridPane();
        formDataContainer.setVgap(5);

        GridPane.setConstraints(nameField.getInputField(), 1, 0);
        GridPane.setConstraints(emailField.getInputField(), 1, 1);
        GridPane.setConstraints(passwordField.getInputField(), 1, 2);
        GridPane.setConstraints(repeatPasswordField.getInputField(), 1, 3);
        GridPane.setConstraints(addressField.getInputField(), 1, 4);
        GridPane.setConstraints(buttonAndNotifierContainer, 1, 5);

        formDataContainer.getChildren().addAll(nameField.getInputField(),
                                                            emailField.getInputField(),
                                                            passwordField.getInputField(),
                                                            repeatPasswordField.getInputField(),
                                                            addressField.getInputField(),
                                                            buttonAndNotifierContainer);

        //Card Container
        cardContainer = new GridPane();
        cardContainer.setPadding(new Insets(TOP_DOWN, RIGHT_LEFT, TOP_DOWN, RIGHT_LEFT));
        cardContainer.setMaxWidth(CARD_WIDTH);
        cardContainer.getStyleClass().add("card");
        cardContainer.setHgap(50);
        cardContainer.setAlignment(Pos.CENTER);
        cardContainer.setTranslateY(20);

        GridPane.setConstraints(photoContainer, 0, 0);
        GridPane.setConstraints(formDataContainer, 1, 0);

        cardContainer.getChildren().addAll(photoContainer, formDataContainer);

        cardParent = new BorderPane();
        cardParent.setCenter(cardContainer);

        extraSpace = new Label(" ");
        extraSpace.setMinHeight(30);

        cardParent.setBottom(extraSpace);

        //Account settings page container
        accountSettingsPageContainer = new ScrollPane(cardParent);
        accountSettingsPageContainer.setFitToWidth(true);
        accountSettingsPageContainer.setFitToHeight(true);
        accountSettingsPageContainer.getStyleClass().add("scrollbar");
        accountSettingsPageContainer.toBack();

        //Making the scrollbar faster
        cardContainer.setOnScroll(event -> {
            double deltaY = event.getDeltaY() * SCROLLING_SPEED;
            double width = accountSettingsPageContainer.getContent().getBoundsInLocal().getWidth();
            double value = accountSettingsPageContainer.getVvalue();
            accountSettingsPageContainer.setVvalue(value + -deltaY/width); // deltaY/width to make the scrolling equally fast regardless of the actual width of the component
        });
    }

    public ScrollPane getAccountSettingsPage() {
        return accountSettingsPageContainer;
    }

    public static AccountSettings getInstance() {
        if (instance == null) {
            instance = new AccountSettings();
        }
        return instance;
    }
}
