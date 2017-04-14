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
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.Objects;

import static app.Partials.*;

public class LandingPage extends GridPane {

    private static LandingPage instance;
    private static int LOGIN = 0;
    private static int SIGNUP = 1;
    private static int USER_TYPE = 2;

    private GridPane landingPageContainer;
    private GridPane landingBackground;
    private GridPane appInfoContainer;
    private ImageView appLogo;
    private Label appName;
    private Label welcomeText;

    private BorderPane formParentContainer;

    private GridPane logInform;
    private GridPane signUpForm;
    private GridPane userTypeForm;

    private Label formDetails;
    private InputField nameField;
    private InputField emailField;
    private InputField passwordField;
    private InputField repeatPassword;
    private Button callToActionButton;
    private Button switchFormButton;

    private GridPane sellerTypeContainer;
    private Label sellerHeadline;
    private Label sellerDescription;

    private GridPane buyerTypeContainer;
    private Label buyerHeadline;
    private Label buyerDescription;

    private InputField userTypeField;

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
        welcomeText.setWrapText(true);
        welcomeText.setMaxWidth(300);

        //App info container
        appInfoContainer = new GridPane();
        appInfoContainer.getStyleClass().add("auto-height");
        appInfoContainer.setMaxWidth(350);
        appInfoContainer.setPadding(new Insets(0, 0, 90, 55));

        setConstraints(appLogo, 0, 0);
        setHalignment(appLogo, HPos.LEFT);
        setMargin(appLogo, new Insets(0, 0, 10, 0));

        setConstraints(appName, 0, 1);
        setHalignment(appName, HPos.LEFT);
        setMargin(appName, new Insets(0, 0, 2, 0));

        setConstraints(welcomeText, 0, 2);
        setHalignment(welcomeText, HPos.LEFT);
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
        setHalignment(appInfoContainer, HPos.LEFT);
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

        //User types options
        sellerHeadline = new Label("Seller");
        sellerHeadline.getStyleClass().add("select-headline");

        sellerDescription = new Label("Save items into inventory, create auctions whenever you want" +
                " see bidders and earn money");
        sellerDescription.getStyleClass().add("select-description");
        sellerDescription.setMaxWidth(300);
        sellerDescription.setWrapText(true);

        sellerTypeContainer = new GridPane();
        sellerTypeContainer.setPadding(new Insets(20));
        sellerTypeContainer.setMinWidth(300);
        sellerTypeContainer.setMinHeight(130);
        sellerTypeContainer.setVgap(2);
        sellerTypeContainer.getStyleClass().add("select-container");

        GridPane.setConstraints(sellerHeadline, 0, 0);
        GridPane.setConstraints(sellerDescription, 0, 1);

        sellerTypeContainer.getChildren().addAll(sellerHeadline, sellerDescription);
        sellerTypeContainer.setOnMouseClicked(e -> this.selectUserType(SELLER));

        buyerHeadline = new Label("Buyer");
        buyerHeadline.getStyleClass().add("select-headline");

        buyerDescription = new Label("Search for current auction, bid for stuff you love" +
                " and get notified when you win the auction");
        buyerDescription.getStyleClass().add("select-description");
        buyerDescription.setMaxWidth(300);
        buyerDescription.setWrapText(true);

        buyerTypeContainer = new GridPane();
        buyerTypeContainer.setPadding(new Insets(20));
        buyerTypeContainer.setMinWidth(300);
        buyerTypeContainer.setMinHeight(130);
        buyerTypeContainer.setVgap(2);
        buyerTypeContainer.getStyleClass().add("select-container");

        userTypeField = new InputField(" ", TEXT);

        GridPane.setConstraints(buyerHeadline, 0, 0);
        GridPane.setConstraints(buyerDescription, 0, 1);

        buyerTypeContainer.getChildren().addAll(buyerHeadline, buyerDescription);
        buyerTypeContainer.setOnMouseClicked(e -> this.selectUserType(BUYER));

        //Call to action button
        callToActionButton = new Button("Login");
        callToActionButton.getStyleClass().add("btn-primary");

        callToActionButton.setOnAction(e -> {
            if (Objects.equals(callToActionButton.getText(), "Login")) {
                this.login();
            } else if (Objects.equals(callToActionButton.getText(), "Next")) {
                this.switchForm(USER_TYPE);
            } else {
                this.signUp();
            }
        });

        //Form switcher
        switchFormButton = new Button("Don't have an account?");
        switchFormButton.getStyleClass().add("btn-secondary");

        switchFormButton.setOnAction(e -> {
            if (Objects.equals(callToActionButton.getText(), "Login")) {
                this.switchForm(SIGNUP);
            } else if (Objects.equals(callToActionButton.getText(), "Next")) {
                this.switchForm(LOGIN);
            } else {
                this.switchForm(SIGNUP);
            }

        });

        //Shared elements
        setConstraints(formDetails, 0, 0);
        setMargin(formDetails, new Insets(0, 0, 25, 0));
        setHalignment(formDetails, HPos.CENTER);

        setConstraints(callToActionButton, 0, 5);
        setHalignment(callToActionButton, HPos.CENTER);
        setMargin(callToActionButton, new Insets(0, 0, 10, 0));

        setConstraints(switchFormButton, 0, 6);
        setHalignment(switchFormButton, HPos.CENTER);

        //Log in form
        logInform = new GridPane();
        logInform.getStyleClass().add("auto-height");
        logInform.setVgap(5);
        logInform.setMaxWidth(300);

        setConstraints(emailField.getInputField(), 0, 1);
        setConstraints(passwordField.getInputField(), 0, 2);

        logInform.getChildren().addAll(formDetails,
                                            emailField.getInputField(),
                                            passwordField.getInputField(),
                                            callToActionButton,
                                            switchFormButton);

        //Sign up form
        signUpForm = new GridPane();
        signUpForm.getStyleClass().add("auto-height");
        signUpForm.setVgap(5);
        signUpForm.setMaxWidth(300);

        setConstraints(nameField.getInputField(), 0, 1);
        setConstraints(emailField.getInputField(), 0, 2);
        setConstraints(passwordField.getInputField(), 0, 3);
        setConstraints(repeatPassword.getInputField(), 0, 4);

        //User type form
        userTypeForm = new GridPane();
        userTypeForm.getStyleClass().add("auto-height");
        userTypeForm.setVgap(15);
        userTypeForm.setMaxWidth(300);

        setConstraints(sellerTypeContainer, 0, 1);
        setConstraints(buyerTypeContainer, 0, 2);

        //Form parent container
        formParentContainer = new BorderPane();
        formParentContainer.setCenter(logInform);
        BorderPane.setAlignment(logInform, Pos.CENTER);

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
        KeyValue showForm = new KeyValue(formParentContainer.opacityProperty(), 1);
        KeyValue hideForm = new KeyValue(formParentContainer.opacityProperty(), 0);

        //Animation frames
        KeyFrame startHide = new KeyFrame(Duration.ZERO, showForm);
        KeyFrame finishHide = new KeyFrame(Duration.millis(100), hideForm);

        KeyFrame updateContent = new KeyFrame(Duration.millis(100), e -> {
            if (formType == LOGIN) {
                logInform.getChildren().removeAll(formDetails,
                                                  emailField.getInputField(),
                                                  passwordField.getInputField(),
                                                  callToActionButton,
                                                  switchFormButton);
                logInform.getChildren().addAll(formDetails,
                                                emailField.getInputField(),
                                                passwordField.getInputField(),
                                                callToActionButton,
                                                switchFormButton);

                formParentContainer.setCenter(logInform);
                formDetails.setText("Login to your account");
                callToActionButton.setText("Login");
                switchFormButton.setText("Don't have an account?");
            } else if (formType == SIGNUP) {
                signUpForm.getChildren().removeAll(formDetails,
                        nameField.getInputField(),
                        emailField.getInputField(),
                        passwordField.getInputField(),
                        repeatPassword.getInputField(),
                        callToActionButton,
                        switchFormButton);

                signUpForm.getChildren().addAll(formDetails,
                                                nameField.getInputField(),
                                                emailField.getInputField(),
                                                passwordField.getInputField(),
                                                repeatPassword.getInputField(),
                                                callToActionButton,
                                                switchFormButton);

                formParentContainer.setCenter(signUpForm);
                formDetails.setText("Create a new account");
                callToActionButton.setText("Next");
                switchFormButton.setText("Have an account?");
            } else {
                userTypeForm.getChildren().removeAll(formDetails,
                                                  sellerTypeContainer,
                                                  buyerTypeContainer,
                                                  callToActionButton,
                                                  switchFormButton);

                userTypeForm.getChildren().addAll(formDetails,
                                                  sellerTypeContainer,
                                                  buyerTypeContainer,
                                                  callToActionButton,
                                                  switchFormButton);

                formParentContainer.setCenter(userTypeForm);
                formDetails.setText("Finish up");
                callToActionButton.setText("Sign up");
                switchFormButton.setText("Back");
            }
        });

        KeyFrame startShow = new KeyFrame(Duration.millis(200), showForm);

        fadeAnimation.getKeyFrames().addAll(startHide, finishHide, updateContent, startShow);
        fadeAnimation.play();
    }

    private void selectUserType(int selectedType) {
        if (selectedType == SELLER) {
            buyerTypeContainer.getStyleClass().remove("select-container--active");
            sellerTypeContainer.getStyleClass().add("select-container--active");
            userTypeField.setValue("Seller");
        } else {
            sellerTypeContainer.getStyleClass().remove("select-container--active");
            buyerTypeContainer.getStyleClass().add("select-container--active");
            userTypeField.setValue("Buyer");
        }
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
            switchForm(LOGIN);
            formParentContainer.setCenter(logInform);
        });
    }

    public static LandingPage getInstance() {
        if (instance == null) {
            instance = new LandingPage();
        }
        return instance;
    }
}
