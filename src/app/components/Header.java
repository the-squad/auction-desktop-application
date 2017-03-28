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

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author Muhammad
 */
public class Header extends BorderPane {

    private static Header instance;

    private BorderPane headerConatiner;
    private GridPane leftSideContainer;
    private ImageView logo;

    private BorderPane navigationTabsConatiner;
    private GridPane tabsContainer;
    private Label exploreTab;
    private Label feedTab;
    private Label inventoryTab;
    private Label auctionsTab;
    private Rectangle activeTabIndicator;

    private GridPane rightSideContainer;
    private SearchBar searchbar;

    private Label notificationsIcon;
    private ContextMenu notificationsCenter;

    private Rectangle profilePicture;
    private ContextMenu appMenuPanel;

    private Header() {
        this.render();
    }

    private void render() {
        //App Logo
        logo = new ImageView(new Image(getClass().getResourceAsStream("/assets/logo.png")));
        logo.setTranslateY(2);

        //Tabs
        exploreTab = new Label("Explore");
        exploreTab.getStyleClass().add("tab");
        exploreTab.getStyleClass().add("tab--active");
        exploreTab.impl_processCSS(true);

        exploreTab.setOnMouseClicked(e -> switchTab(exploreTab));

        feedTab = new Label("Feed");
        feedTab.getStyleClass().add("tab");

        feedTab.setOnMouseClicked(e -> switchTab(feedTab));

        inventoryTab = new Label("Inventory");
        inventoryTab.getStyleClass().add("tab");

        inventoryTab.setOnMouseClicked(e -> switchTab(inventoryTab));

        auctionsTab = new Label("My Auctions");
        auctionsTab.getStyleClass().add("tab");

        auctionsTab.setOnMouseClicked(e -> switchTab(auctionsTab));

        //Active indicator
        activeTabIndicator = new Rectangle(79, 2);
        activeTabIndicator.getStyleClass().add("active-indicator");
        activeTabIndicator.setArcWidth(3);
        activeTabIndicator.setArcHeight(3);

        //CategoriesPanel container
        tabsContainer = new GridPane();
        tabsContainer.setConstraints(exploreTab, 0, 0);
        tabsContainer.setConstraints(feedTab, 1, 0);
        tabsContainer.setConstraints(inventoryTab, 2, 0);
        tabsContainer.setConstraints(auctionsTab, 3, 0);

        tabsContainer.getChildren().addAll(exploreTab, feedTab, inventoryTab, auctionsTab);

        //CategoriesPanel container big parent
        navigationTabsConatiner = new BorderPane();
        navigationTabsConatiner.setTop(tabsContainer);
        navigationTabsConatiner.setBottom(activeTabIndicator);

        //Left part container
        leftSideContainer = new GridPane();
        leftSideContainer.setConstraints(logo, 0, 0);
        leftSideContainer.setMargin(logo, new Insets(0, 35, 0, 0));

        leftSideContainer.setConstraints(navigationTabsConatiner, 1, 0);

        leftSideContainer.getChildren().addAll(logo, navigationTabsConatiner);

        //Search bar
        searchbar = SearchBar.getInstance();
        searchbar.getSearchbar().setTranslateY(12);

        //Notifications icon
        notificationsIcon = new Label();
        notificationsIcon.getStyleClass().add("notification-icon");
        notificationsIcon.setTranslateY(13);

        notificationsIcon.setOnMouseClicked(e ->  { /*TODO*/ });

        //Notifications center
        notificationsCenter = new ContextMenu();
        //TODO

        //Profile picture
        profilePicture = new Rectangle(30, 30,
                new ImagePattern((
                        new Image(getClass().getResourceAsStream("/assets/picture.jpg"),
                                30,
                                30,
                                false,
                                true))));
        profilePicture.getStyleClass().add("picture");
        profilePicture.setArcHeight(50);
        profilePicture.setArcWidth(50);
        profilePicture.setTranslateY(13);

        profilePicture.setOnMouseClicked(e -> { /*TODO*/});

        //Menu
        appMenuPanel = new ContextMenu();
        //TODO

        //Right part container
        rightSideContainer = new GridPane();
        rightSideContainer.setConstraints(searchbar.getSearchbar(),0,0);
        rightSideContainer.setMargin(searchbar.getSearchbar(), new Insets(0, 50, 0, 0));

        rightSideContainer.setConstraints(notificationsIcon, 1, 0);
        rightSideContainer.setMargin(notificationsIcon, new Insets(0, 30, 0,0 ));

        rightSideContainer.setConstraints(profilePicture, 2, 0);

        rightSideContainer.getChildren().addAll(searchbar.getSearchbar(), notificationsIcon, profilePicture);


        //Header container
        headerConatiner = new BorderPane();
        headerConatiner.getStyleClass().add("header");
        headerConatiner.setPadding(new Insets(0, 50, 0, 50));
        headerConatiner.setLeft(leftSideContainer);
        headerConatiner.setRight(rightSideContainer);
    }

    public BorderPane getHeader() {
        return headerConatiner;
    }

    private void switchTab(Label label) {
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
        Label activeLabel = (Label) headerConatiner.lookup(".tab--active");
        activeLabel.getStyleClass().remove("tab--active");
        label.getStyleClass().add("tab--active");
    }

    public void setUserPhoto() {
        //TODO change the photo placeholder
    }

    public static Header getInstance() {
        if (instance == null) {
            instance = new Header();
        }
        return instance;
    }
}
