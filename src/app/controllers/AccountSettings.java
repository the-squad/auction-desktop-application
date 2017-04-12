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
package app.controllers;

import app.Navigator;
import app.components.InputField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import static app.Partials.*;

public class AccountSettings {

    private static AccountSettings instance;

    private BorderPane accountSettingsPageContainer;
    private GridPane photoContainer;
    private Image userPhoto;
    private ImageView userPhotoView;
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

    private Button closeButton;

    private AccountSettings() {
        this.render();
    }

    private void render() {
        //User photo
        userPhoto = new Image(getClass().getResourceAsStream("/assets/picture.jpg"));

        photoClipper = new Rectangle(250, 250);
        photoClipper.setArcHeight(16);
        photoClipper.setArcWidth(16);

        userPhotoView = new ImageView(userPhoto);
        userPhotoView.setFitWidth(250);
        userPhotoView.setFitHeight(250);
        userPhotoView.setClip(photoClipper);

        //Change photo button
        changePhotoButton = new Button("Change photo");
        changePhotoButton.getStyleClass().add("btn-secondary");
        changePhotoButton.setMinWidth(250);

        //Account data fields
        nameField = new InputField("Name", TEXT);
        emailField = new InputField("Email", EMAIL);
        passwordField = new InputField("Password", PASSWORD);
        repeatPasswordField = new InputField("Repeat Password", PASSWORD);
        addressField = new InputField("Address", ADDRESS);

        updateAccountButton = new Button("Update Account");
        updateAccountButton.getStyleClass().add("btn-primary");

        updateAccountButton.setOnAction(e -> {
            updateActionNotifier.setVisible(true);
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

        //Close button
        closeButton = new Button();
        closeButton.getStyleClass().addAll("btn-outline-gray", "btn-close");

        closeButton.setOnAction(e -> {
            Navigator.hidePage();
            updateActionNotifier.setVisible(false);
        });

        //Photo container
        photoContainer = new GridPane();
        photoContainer.setAlignment(Pos.TOP_CENTER);

        GridPane.setConstraints(userPhotoView, 0, 0);
        GridPane.setMargin(userPhotoView, new Insets( 10, 0, 10, 40));

        GridPane.setConstraints(changePhotoButton, 0, 1);
        GridPane.setMargin(changePhotoButton, new Insets(0, 0, 0, 40));

        photoContainer.getChildren().addAll(userPhotoView, changePhotoButton);

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

        //Account settings page container
        accountSettingsPageContainer = new BorderPane();
        accountSettingsPageContainer.setTranslateY(40);
        accountSettingsPageContainer.setPadding(new Insets(20,75,0,50));
        accountSettingsPageContainer.getStyleClass().add("account-settings");

        accountSettingsPageContainer.setLeft(photoContainer);

        accountSettingsPageContainer.setCenter(formDataContainer);
        BorderPane.setAlignment(formDataContainer, Pos.CENTER_LEFT);
        BorderPane.setMargin(formDataContainer, new Insets(0, 0, 0, 100));

        accountSettingsPageContainer.setRight(closeButton);
    }

    public void fillAccountData() {
        /*
         TODO
         nameField.setValue();
         emailField.setValue();
         passwordField.setValue();
         repeatPasswordField.setValue();
         addressField.setValue();
         */
    }

    public BorderPane getAccountSettingsPage() {
        return accountSettingsPageContainer;
    }

    public static AccountSettings getInstance() {
        if (instance == null) {
            instance = new AccountSettings();
        }
        return instance;
    }
}
