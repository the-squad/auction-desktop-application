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
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import models.ImageUtils;
import models.Seller;

import java.awt.image.BufferedImage;

import static app.Partials.*;

public class Header extends BorderPane {

    private static Header instance;

    private BorderPane headerContainer;
    private ImageView logo;

    private BorderPane navigationTabsContainer;
    private GridPane tabsContainer;
    private Label exploreTab;
    private Label feedTab;
    private Label inventoryTab;
    private Label auctionsTab;
    private Rectangle activeTabIndicator;

    private Label pageTitle;
    private Button backButton;

    private GridPane rightSideContainer;
    private Button searchButton;
    private Button notificationsButton;
    private UserDetails userInfo;
    private Button createButton;
    private Rectangle divider;
    private Button logOutButton;

    private Header() {
        this.render();
    }

    private void render() {
        //App Logo
        logo = new ImageView(new Image(getClass().getResourceAsStream("/assets/logo.png")));
        logo.setTranslateY(2);

        //Active indicator
        activeTabIndicator = new Rectangle((userType == BUYER) ? 79 : 93, 2);
        activeTabIndicator.getStyleClass().add("active-indicator");
        activeTabIndicator.setArcWidth(3);
        activeTabIndicator.setArcHeight(3);

        //CategoriesPanel container
        tabsContainer = new GridPane();

        //Tabs and page title
        pageTitle = new Label();
        pageTitle.getStyleClass().add("page-title");
        BorderPane.setAlignment(pageTitle, Pos.CENTER_LEFT);

        backButton = new Button();
        backButton.getStyleClass().addAll("icon-button", "back-icon");
        BorderPane.setAlignment(backButton, Pos.CENTER_LEFT);
        BorderPane.setMargin(backButton, new Insets(0, 3, 0, 0));

        backButton.setOnAction(e -> Navigator.hidePage());

        if (userType == BUYER) {
            //Explore tab
            exploreTab = new Label("Explore");
            exploreTab.getStyleClass().add("tab");
            exploreTab.getStyleClass().add("tab--active");

            exploreTab.setOnMouseClicked(e -> switchTab(exploreTab, EXPLORE_TAB));

            //Feed tab
            feedTab = new Label("Feed");
            feedTab.getStyleClass().add("tab");

            feedTab.setOnMouseClicked(e -> switchTab(feedTab, FEED_TAB));

            //Inventory tab
            inventoryTab = new Label("Inventory");
            inventoryTab.getStyleClass().add("tab");

            inventoryTab.setOnMouseClicked(e -> switchTab(inventoryTab, INVENTORY_TAB));

            GridPane.setConstraints(exploreTab, 0, 0);
            GridPane.setConstraints(feedTab, 1, 0);
            GridPane.setConstraints(inventoryTab, 2, 0);
            tabsContainer.getChildren().addAll(exploreTab, feedTab, inventoryTab);
        } else if (userType == SELLER) {
            //Inventory tab
            inventoryTab = new Label("Inventory");
            inventoryTab.getStyleClass().add("tab");
            inventoryTab.getStyleClass().add("tab--active");

            inventoryTab.setOnMouseClicked(e -> switchTab(inventoryTab, INVENTORY_TAB));

            //Auctions tab
            auctionsTab = new Label("My Auctions");
            auctionsTab.getStyleClass().add("tab");

            auctionsTab.setOnMouseClicked(e -> switchTab(auctionsTab, AUCTIONS_TAB));

            GridPane.setConstraints(inventoryTab, 0, 0);
            GridPane.setConstraints(auctionsTab, 1, 0);
            tabsContainer.getChildren().addAll(inventoryTab, auctionsTab);
        } else if (userType == ADMIN) {
            pageTitle.setText("Admin Console");
        }

        //CategoriesPanel container big parent
        navigationTabsContainer = new BorderPane();
        navigationTabsContainer.setMinWidth(350);
        navigationTabsContainer.setMaxWidth(350);
        if (userType == BUYER || userType == SELLER) {
            navigationTabsContainer.setTop(tabsContainer);
            navigationTabsContainer.setBottom(activeTabIndicator);
        } else {
            navigationTabsContainer.setCenter(pageTitle);
        }

        //Create button
        if (userType == SELLER) {
            createButton = new Button();
            createButton.getStyleClass().addAll("icon-button", "add-icon");

            createButton.setOnAction(e -> Navigator.viewPage(ADDITION_PAGE, ""));
        }

        //Search button
        if (userType != ADMIN) {
            searchButton = new Button();
            searchButton.getStyleClass().addAll("icon-button", "search-icon");

            searchButton.setOnAction(e -> {
                Navigator.viewPage(SEARCH_PAGE, "");
            });
        }

        //Notifications button
        notificationsButton = new Button();
        notificationsButton.getStyleClass().addAll("icon-button", "notification-icon");

        notificationsButton.setOnAction(e -> {
            Navigator.viewPage(NOTIFICATIONS_PAGE, "Notifications");
        });

        //Profile picture
        userInfo = new UserDetails(FIT_DATA, currentUser.getName(), currentUser.getPhoto(), currentUser.getId());

        userInfo.getUserDetails().setOnMouseClicked(e -> {
            if (userType == SELLER) {
                Navigator.viewPage(PROFILE_PAGE, currentUser.getName());
                ProfilePage.getInstance().fillUserData(currentSeller);
            } else {
                Navigator.viewPage(ACCOUNT_SETTINGS, currentUser.getName());
            }
        });

        //Divider
        divider = new Rectangle();
        divider.setHeight(30);
        divider.setWidth(1);
        divider.setStyle("-fx-fill: -fx-medium-gray-color");

        //Logout button
        logOutButton = new Button();
        logOutButton.getStyleClass().addAll("icon-button", "logout-icon");

        logOutButton.setOnAction(e -> {
            Navigator.switchPage(HOME_PAGE, LANDING_PAGE);
            Navigator.refreshView();
            instance = null;
        });

        //Right part container
        rightSideContainer = new GridPane();
        rightSideContainer.setMinWidth(350);
        rightSideContainer.setAlignment(Pos.CENTER_RIGHT);
        rightSideContainer.setHgap(15);

        if (userType == SELLER) {
            GridPane.setConstraints(createButton,0,0);
            rightSideContainer.getChildren().add(createButton);
        }

        if (userType != ADMIN) {
            GridPane.setConstraints(searchButton,1,0);
            rightSideContainer.getChildren().add(searchButton);
        }

        GridPane.setConstraints(notificationsButton, 2, 0);

        GridPane.setConstraints(userInfo.getUserDetails(), 3, 0);
        GridPane.setMargin(userInfo.getUserDetails(), new Insets(0, 0,0 ,5));

        GridPane.setConstraints(divider, 4, 0);
        GridPane.setMargin(divider, new Insets(0, 5, 0, 5));

        GridPane.setConstraints(logOutButton, 5, 0);

        rightSideContainer.getChildren().addAll(notificationsButton, userInfo.getUserDetails(), divider, logOutButton);

        //Header container
        headerContainer = new BorderPane();
        headerContainer.getStyleClass().add("header");
        headerContainer.setPadding(new Insets(0, 50, 0, 50));
        headerContainer.setLeft(navigationTabsContainer);
        headerContainer.setCenter(logo);
        headerContainer.setRight(rightSideContainer);
    }

    public BorderPane getHeader() {
        return headerContainer;
    }

    private void switchTab(Label label, int tabId) {
        //Changing indicator width and position
        Bounds labelProps = label.localToParent(label.getBoundsInLocal());

        Timeline animateIndicator = new Timeline();

        //Position values
        KeyValue currentPosition = new KeyValue(activeTabIndicator.translateXProperty(), activeTabIndicator.getTranslateX());
        KeyValue futurePosition = new KeyValue(activeTabIndicator.translateXProperty(), labelProps.getMinX());

        KeyFrame positionAnimate = new KeyFrame(Duration.ZERO, currentPosition);
        KeyFrame positionFinish = new KeyFrame(Duration.millis(50), futurePosition);

        //Width values
        KeyValue currentWidth = new KeyValue(activeTabIndicator.widthProperty(), activeTabIndicator.getWidth());
        KeyValue futureWidth = new KeyValue(activeTabIndicator.widthProperty(), labelProps.getWidth());

        KeyFrame widthAnimate = new KeyFrame(Duration.ZERO, currentWidth);
        KeyFrame widthFinish = new KeyFrame(Duration.millis(50), futureWidth);

        animateIndicator.getKeyFrames().addAll(positionAnimate, positionFinish, widthAnimate, widthFinish);
        animateIndicator.play();

        //Highlighting the active tab
        Label activeLabel = (Label) headerContainer.lookup(".tab--active");
        activeLabel.getStyleClass().remove("tab--active");
        label.getStyleClass().add("tab--active");

        //Switching the tabs
        Navigator.switchTab(tabId);
    }

    public void setPageTitle(String title) {
        pageTitle.setText(title);
    }

    public void showPageTitle() {
        navigationTabsContainer.setTop(null);
        navigationTabsContainer.setBottom(null);
        navigationTabsContainer.setLeft(backButton);
        navigationTabsContainer.setCenter(pageTitle);
    }

    public void hidePageTitle() {
        navigationTabsContainer.setLeft(null);
        navigationTabsContainer.setCenter(null);
        navigationTabsContainer.setTop(tabsContainer);
        navigationTabsContainer.setBottom(activeTabIndicator);
    }

    public void updateUserBlock() {
        userInfo.setUserDetails(currentUser.getName(), currentUser.getPhoto(), currentUser.getId());
    }

    public void destroy() {
        instance = null;
    }

    public static Header getInstance() {
        if (instance == null) {
            instance = new Header();
        }
        return instance;
    }
}
