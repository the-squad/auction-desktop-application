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

import app.components.Header;
import app.components.InputField;
import app.layouts.ScrollView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import models.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static app.Partials.*;

public class AccountSettings {

    private static AccountSettings instance;

    private ScrollView accountSettingsPageContainer;
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
    private InputField phoneField;

    private GridPane buttonAndNotifierContainer;
    private Button updateAccountButton;
    private Label updateActionNotifier;
    private Label extraSpace;

    private FileChooser fileChooser;
    private BufferedImage uploadedImage;
    private Timeline hideSuccessMessage;

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

        changePhotoButton.setOnAction(e -> {
            File userPhoto = fileChooser.showOpenDialog(null);
            if (userPhoto != null) {
                try {
                    uploadedImage = ImageIO.read(userPhoto);
                    photoClipper.setFill(new ImagePattern(ImageUtils.cropAndConvertImage(uploadedImage, 200, 200)));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //Account data fields
        nameField = new InputField("Name", TEXT, LONG);
        nameField.setValue(currentUser.getName());

        emailField = new InputField("Email", EMAIL, LONG);
        emailField.setValue(currentUser.getEmail());

        passwordField = new InputField("Password", PASSWORD, LONG);
        passwordField.setValue(currentUser.getPassword());

        repeatPasswordField = new InputField("Repeat Password", PASSWORD, LONG);

        addressField = new InputField("Address", TEXT, LONG);
        addressField.setValue(currentUser.getAddress());

        phoneField = new InputField("Phone Number", NUMBER, LONG);
        phoneField.setValue(currentUser.getPhone());

        updateAccountButton = new Button("Update");
        updateAccountButton.getStyleClass().add("btn-primary");

        updateAccountButton.setOnAction(e -> {
            for (Node inputField : formDataContainer.getChildren()) {
                if (inputField.getStyleClass().contains("input-field--danger")) {
                    return;
                }
            }

            if (uploadedImage != null)
                currentUser.setPhoto(uploadedImage);

            currentUser.setName(nameField.getValue())
                    .setEmail(emailField.getValue())
                    .setPassword(passwordField.getValue())
                    .setAddress(addressField.getValue())
                    .setPhone(phoneField.getValue());
            currentUser.save();

            Header.getInstance().updateUserBlock();
            updateActionNotifier.setVisible(true);
            hideSuccessMessage.play();
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
        GridPane.setConstraints(phoneField.getInputField(), 1, 5);
        GridPane.setConstraints(buttonAndNotifierContainer, 1, 6);

        formDataContainer.getChildren().addAll(nameField.getInputField(),
                                                            emailField.getInputField(),
                                                            passwordField.getInputField(),
                                                            repeatPasswordField.getInputField(),
                                                            addressField.getInputField(),
                                                            phoneField.getInputField(),
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

        //File choose
        fileChooser = new FileChooser();
        fileChooser.setTitle("Upload new photo!");

        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter(
                        "Photos", "*.png", "*.jpg", "*.jpeg", "*.gif");

        fileChooser.getExtensionFilters().add(fileExtensions);

        //Account settings page container
        accountSettingsPageContainer = new ScrollView(cardParent);

        //Hide success message
        //Resting form
        hideSuccessMessage = new Timeline(new KeyFrame(Duration.millis(10000), a -> {
            updateActionNotifier.setVisible(false);
        }));
    }

    public ScrollPane getAccountSettingsPage() {
        return accountSettingsPageContainer.getScrollView();
    }

    public static AccountSettings getInstance() {
        if (instance == null) {
            instance = new AccountSettings();
        }
        return instance;
    }
}
