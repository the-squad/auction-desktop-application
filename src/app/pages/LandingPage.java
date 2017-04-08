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
package app.pages;

import app.Navigator;
import app.components.InputField;
import app.components.LoadingIndicator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.Objects;

import static app.Partials.*;

public class LandingPage extends GridPane {

    private static LandingPage instance;
    private static int LOGIN = 0;
    private static int SIGNUP = 1;

    private GridPane landingPageContainer;
    private GridPane landingBackground;
    private GridPane appInfoContainer;
    private ImageView appLogo;
    private Label appName;
    private Label welcomeText;

    private BorderPane formParentContainer;
    private GridPane formsContainer;
    private Label formDetails;
    private InputField nameField;
    private InputField emailField;
    private InputField passwordField;
    private InputField repeatPassword;
    private Button callToActionButton;
    private Button switchFormButton;

    private HomePage homePage;
    private LoadingIndicator loadingIndicator;

    private Timeline fadeAnimation;

    private LandingPage() {
        this.render();
    }

    private void render() {
        //App Logo
        appLogo = new ImageView(new Image(getClass().getResourceAsStream("/assets/app-logo.png")));
        appLogo.setFitHeight(100);
        appLogo.setFitWidth(100);

        //Welcome Text
        appName = new Label("Welcome to Ohio");
        appName.getStyleClass().add("app-name");

        //Welcome Text
        welcomeText = new Label("A place where you can sell and buy anything");
        welcomeText.getStyleClass().add("welcome-text");
        welcomeText.setMaxWidth(350);
        welcomeText.setWrapText(true);

        //App info container
        appInfoContainer = new GridPane();
        appInfoContainer.getStyleClass().add("auto-height");
        appInfoContainer.setMaxWidth(350);

        setConstraints(appLogo, 0, 0);
        setHalignment(appLogo, HPos.CENTER);
        setMargin(appLogo, new Insets(0, 0, 20, 0));

        setConstraints(appName, 0, 1);
        setHalignment(appName, HPos.CENTER);
        setMargin(appName, new Insets(0, 0, 1, 0));

        setConstraints(welcomeText, 0, 2);
        setHalignment(welcomeText, HPos.CENTER);
        setMargin(welcomeText, new Insets(0, 0, 0, 0));

        appInfoContainer.getChildren().addAll(appLogo, appName, welcomeText);

        landingBackground = new GridPane();
        landingBackground.getStyleClass().add("landing");

        ColumnConstraints centerInfoH = new ColumnConstraints();
        centerInfoH.setPercentWidth(100);

        RowConstraints centerInfoV = new RowConstraints();
        centerInfoV.setPercentHeight(100);

        landingBackground.getRowConstraints().add(centerInfoV);
        landingBackground.getColumnConstraints().add(centerInfoH);

        setConstraints(appInfoContainer, 0, 0);
        setHalignment(appInfoContainer, HPos.CENTER);
        setValignment(appInfoContainer, VPos.CENTER);
        landingBackground.getChildren().add(appInfoContainer);

        //Form headline text
        formDetails = new Label();
        formDetails.setText("Log into your account");
        formDetails.getStyleClass().add("form-headline");

        //User name field
        nameField = new InputField("Full Name", TEXT);
        emailField = new InputField("Email", EMAIL);
        passwordField = new InputField("Password", PASSWORD);
        repeatPassword = new InputField("Re-enter your password", PASSWORD);

        //Call to action button
        callToActionButton = new Button("Login");
        callToActionButton.getStyleClass().add("btn-primary");

        callToActionButton.setOnAction(e -> {
            if (Objects.equals(callToActionButton.getText(), "Login")) {
                this.login();
            } else {
                this.signUp();
            }
        });

        //Form switcher
        switchFormButton = new Button("Don't have an account?");
        switchFormButton.getStyleClass().add("btn-secondary");

        switchFormButton.setOnAction(e -> {
            switchForm((Objects.equals(callToActionButton.getText(), "Login")) ? SIGNUP : LOGIN);
        });

        //Forms container
        formsContainer = new GridPane();
        formsContainer.getStyleClass().add("auto-height");
        formsContainer.setVgap(5);
        formsContainer.setMaxWidth(300);

        setConstraints(formDetails, 0, 0);
        setMargin(formDetails, new Insets(0, 0, 25, 0));
        setHalignment(formDetails, HPos.CENTER);

        setConstraints(nameField.getInputField(), 0, 1);
        setConstraints(emailField.getInputField(), 0, 2);
        setConstraints(passwordField.getInputField(), 0, 3);
        setConstraints(repeatPassword.getInputField(), 0, 4);
        setMargin(repeatPassword.getInputField(), new Insets(0, 0, 15, 0));

        setConstraints(callToActionButton, 0, 5);
        setHalignment(callToActionButton, HPos.CENTER);
        setMargin(callToActionButton, new Insets(0, 0, 10, 0));

        setConstraints(switchFormButton, 0, 6);
        setHalignment(switchFormButton, HPos.CENTER);

        formsContainer.getChildren().addAll(formDetails,
                                            emailField.getInputField(),
                                            passwordField.getInputField(),
                                            callToActionButton,
                                            switchFormButton);

        //Form parent container
        formParentContainer = new BorderPane();
        formParentContainer.setCenter(formsContainer);
        BorderPane.setAlignment(formsContainer, Pos.CENTER);

        //Landing page container
        landingPageContainer = new GridPane();
        landingPageContainer.getStyleClass().add("landing-bg");
        landingPageContainer.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        ColumnConstraints appInfoColumn = new ColumnConstraints();
        appInfoColumn.setPercentWidth(50);

        ColumnConstraints formColumn = new ColumnConstraints();
        formColumn.setPercentWidth(50);
        formColumn.setHalignment(HPos.CENTER);

        RowConstraints fullHeightRow = new RowConstraints();
        fullHeightRow.setPercentHeight(100);
        fullHeightRow.setValignment(VPos.CENTER);

        landingPageContainer.getColumnConstraints().addAll(appInfoColumn, formColumn);
        landingPageContainer.getRowConstraints().add(fullHeightRow);

        setConstraints(landingBackground, 0, 0);
        setConstraints(formParentContainer, 1, 0);

        landingPageContainer.getChildren().addAll(landingBackground, formParentContainer);

        //Creating loading indicator
        loadingIndicator = new LoadingIndicator();
    }

    public GridPane getLandingPage() {
        return landingPageContainer;
    }

    private void switchForm(int formType) {
        fadeAnimation = new Timeline();

        //Opacity values
        KeyValue showForm = new KeyValue(formsContainer.opacityProperty(), 1);
        KeyValue hideForm = new KeyValue(formsContainer.opacityProperty(), 0);

        //Animation frames
        KeyFrame startHide = new KeyFrame(Duration.ZERO, showForm);
        KeyFrame finishHide = new KeyFrame(Duration.millis(100), hideForm);

        KeyFrame updateContent = new KeyFrame(Duration.millis(100), e -> {
            if (formType == LOGIN) {
                formsContainer.getChildren().remove(nameField.getInputField());
                formsContainer.getChildren().remove(repeatPassword.getInputField());
            } else {
                formsContainer.getChildren().removeAll(emailField.getInputField(),
                                                       passwordField.getInputField());
                formsContainer.getChildren().addAll(nameField.getInputField(),
                                                    emailField.getInputField(),
                                                    passwordField.getInputField(),
                                                    repeatPassword.getInputField());

                setConstraints(nameField.getInputField(), 0, 1);
                setConstraints(emailField.getInputField(), 0, 2);
                setConstraints(passwordField.getInputField(), 0, 3);
                setConstraints(repeatPassword.getInputField(), 0, 4);
                setConstraints(callToActionButton, 0, 5);
                setConstraints(switchFormButton, 0, 6);
            }

            formDetails.setText((formType == LOGIN) ? "Log into your account" : "Create a new account");
            callToActionButton.setText((formType == LOGIN) ? "Login" : "Sign up");
            switchFormButton.setText((formType == LOGIN) ? "Have an account?" : "Don't have an account?");
        });

        KeyFrame startShow = new KeyFrame(Duration.millis(200), showForm);

        fadeAnimation.getKeyFrames().addAll(startHide, finishHide, updateContent, startShow);
        fadeAnimation.play();
    }

    private void login() {
        //Getting values
        emailField.getValue();
        passwordField.getValue();

        loadingIndicator.setLoadingMessage("Logging in");
        this.goToHomePage();

        /*
         TODO
         - Call the login method
         - Check if the data is correct, then call goToHomePage and get user photo
         - If the data are incorrect show an error message to the field
         - If the email doesn't exist switch to sign up form and fill the email automatically
         - Set the user type
         */
    }

    private void signUp() {
        //Getting values
        nameField.getValue();
        emailField.getValue();
        passwordField.getValue();
        repeatPassword.getValue();

        loadingIndicator.setLoadingMessage("Signing up");
        this.goToHomePage();

        /*
         TODO
         - Check if all data are valid and the password matches, any invalid data shows an error message
         - Call the sign up method
         - Check if the data is correct, then call goToHomePage
         - If the email is already exist switch to the login form and fill the email automatically
         - Set the user type
         */

    }

    private void goToHomePage() {
        //Showing the loading indicator
        userType = BUYER;
        formParentContainer.setCenter(loadingIndicator.getLoadingIndicator());

        //Loading the home page
        Task<String> initializingHomePage = new Task<String>() {
            @Override
            protected String call() throws Exception {
                homePage = HomePage.getInstance();
                homePage.setUserPhoto();
                return null;
            }
        };

        //Creating a thread that triggered when the home page is rendered
        Thread switchToHomePage = new Thread(initializingHomePage);
        switchToHomePage.setDaemon(true);
        switchToHomePage.start();

        initializingHomePage.setOnSucceeded((WorkerStateEvent t) -> {
            //Switching to the home page
            Navigator.switchPage(LANDING_PAGE, HOME_PAGE);
            homePage.gainFocus();
        });
    }

    public static LandingPage getInstance() {
        if (instance == null) {
            instance = new LandingPage();
        }
        return instance;
    }
}
