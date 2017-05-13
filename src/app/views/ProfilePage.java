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

import app.Navigator;
import app.components.LoadingIndicator;
import app.layouts.GridView;
import app.layouts.ScrollView;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import models.ImageUtils;
import models.Seller;
import static app.Partials.*;

public class ProfilePage {

    private static ProfilePage instance;

    private ScrollView profilePageScrollbar;
    private BorderPane profilePageContainer;

    private BorderPane coverContainer;
    private GridPane coverGrid;
    private Image photo;
    private Rectangle photoViewer;
    private BorderPane photoBorder;

    private Label userName;

    private GridPane aboutUserContainer;

    private GridPane auctionContainer;
    private Label auctionsHeadline;
    private Label auctionsNumber;

    private GridPane followerContainer;
    private Label followersHeadline;
    private Label followersNumber;

    private Button callToAction;

    private GridView gridView;
    private LoadingIndicator loadingIndicator;

    private ProfilePage() {
        this.render();
    }

    private void render() {
        //User photo
        photoViewer = new Rectangle();
        photoViewer.setFill(Color.rgb(245,248,250));
        photoViewer.setWidth(150);
        photoViewer.setHeight(150);

        photoBorder = new BorderPane();
        photoBorder.setCenter(photoViewer);
        photoBorder.setPadding(new Insets(4));
        photoBorder.setMaxHeight(154);
        photoBorder.setMaxWidth(154);
        photoBorder.getStyleClass().add("user-photo");
        photoBorder.setTranslateY(60);

        //User name
        userName = new Label();
        userName.getStyleClass().add("profile-name");
        userName.setTranslateY(45);

        //Auctions number
        auctionsHeadline = new Label("Auctions".toUpperCase());
        auctionsHeadline.getStyleClass().add("profile-headline");

        auctionsNumber = new Label("-");
        auctionsNumber.getStyleClass().add("profile-number");

        auctionContainer = new GridPane();
        auctionContainer.setTranslateY(2);
        GridPane.setConstraints(auctionsHeadline, 0, 0);
        GridPane.setConstraints(auctionsNumber, 0, 1);
        auctionContainer.getChildren().addAll(auctionsHeadline, auctionsNumber);

        //Followers number
        followersHeadline = new Label("Followers".toUpperCase());
        followersHeadline.getStyleClass().add("profile-headline");

        followersNumber = new Label("-");
        followersNumber.getStyleClass().add("profile-number");

        followerContainer = new GridPane();
        followerContainer.setTranslateY(2);
        GridPane.setConstraints(followersHeadline, 0, 0);
        GridPane.setConstraints(followersNumber, 0, 1);
        followerContainer.getChildren().addAll(followersHeadline, followersNumber);

        //Call to action button
        callToAction = new Button("Follow");
        callToAction.setDisable(true);
        callToAction.getStyleClass().addAll("btn-primary", "btn-profile");

        //About user container
        aboutUserContainer = new GridPane();
        aboutUserContainer.getStyleClass().add("about");
        aboutUserContainer.setMinHeight(60);
        aboutUserContainer.setMaxHeight(60);
        aboutUserContainer.setPadding(new Insets(0, 50, 0, 50));
        aboutUserContainer.setAlignment(Pos.CENTER_LEFT);
        aboutUserContainer.setHgap(30);

        ColumnConstraints firstRow = new ColumnConstraints();
        firstRow.setPercentWidth(10);

        ColumnConstraints secondRow = new ColumnConstraints();
        secondRow.setPercentWidth(10);

        ColumnConstraints lastRow = new ColumnConstraints();
        lastRow.setPercentWidth(80);
        lastRow.setHalignment(HPos.RIGHT);

        aboutUserContainer.getColumnConstraints().addAll(firstRow, secondRow, lastRow);

        GridPane.setConstraints(auctionContainer, 0, 0);
        GridPane.setConstraints(followerContainer, 1, 0);
        GridPane.setConstraints(callToAction, 2, 0);

        aboutUserContainer.getChildren().addAll(auctionContainer, followerContainer, callToAction);

        //Cover Grid
        coverGrid = new GridPane();
        coverGrid.setAlignment(Pos.BOTTOM_LEFT);
        coverGrid.setPadding(new Insets(0, 0, 0 ,40));

        ColumnConstraints photoColumn = new ColumnConstraints();
        photoColumn.setPercentWidth(15);
        photoColumn.setHalignment(HPos.RIGHT);

        ColumnConstraints infoColumn = new ColumnConstraints();
        infoColumn.setPercentWidth(85);
        infoColumn.setHalignment(HPos.LEFT);

        coverGrid.getColumnConstraints().addAll(photoColumn, infoColumn);

        GridPane.setConstraints(photoBorder, 0, 0);
        GridPane.setConstraints(userName, 1, 0);
        GridPane.setMargin(userName, new Insets(0, 0, 0, 40));
        GridPane.setConstraints(aboutUserContainer, 1, 1);

        coverGrid.getChildren().addAll(photoBorder, userName, aboutUserContainer);

        //Cover container
        coverContainer = new BorderPane();
        coverContainer.setMinHeight(200);
        coverContainer.getStyleClass().add("cover");
        coverContainer.setCenter(coverGrid);

        //Grid view
        gridView = new GridView();

        //Loading indicator
        loadingIndicator = new LoadingIndicator();

        //Profile page container
        profilePageContainer = new BorderPane();
        profilePageContainer.setTop(coverContainer);

        //Scroll pane
        profilePageScrollbar = new ScrollView(profilePageContainer);
    }

    public void fillUserData(Seller seller) {
        this.clearUserData();
        loadingIndicator.setLoadingMessage("Getting " + seller.getName() + "'s Auctions");
        loadingIndicator.startRotating();
        profilePageContainer.setCenter(loadingIndicator.getLoadingIndicator());

        if (userType == BUYER) {
            if (seller.checkFollow(currentBuyer.getId()))
                callToAction.setText("Following");
            else
                callToAction.setText("Follow");
        } else {
            callToAction.setText("Edit Profile");
        }

        callToAction.setDisable(true);

        //Call to action
        callToAction.setOnAction(e -> {
            if (userType == BUYER) {
                if (callToAction.getText().equals("Follow")) {
                    currentBuyer.followSeller(seller.getId());
                    followersNumber.setText(String.valueOf(seller.getFollowersNumber()));
                    callToAction.setText("Following");
                } else {
                    currentBuyer.unFollowSeller(seller.getId());
                    followersNumber.setText(String.valueOf(seller.getFollowersNumber()));
                    callToAction.setText("Follow");
                }
            } else {
                Navigator.switchTab(ACCOUNT_SETTINGS);
            }
        });

        //Loading profile info
        Task<String> loadingProfileData = new Task<String>() {
            @Override
            protected String call() throws Exception {
                photo = ImageUtils.cropAndConvertImage(seller.getPhoto(), 150, 150);
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                photoViewer.setFill(new ImagePattern(photo));
                userName.setText(seller.getName());
                auctionsNumber.setText(String.valueOf(seller.getAuctions().size()));
                followersNumber.setText(String.valueOf(seller.getFollowersNumber()));
            }
        };
        new Thread(loadingProfileData).start();

        //Loading profile auctions
        Task<String> loadingProfileAuctions = new Task<String>() {
            @Override
            protected String call() throws Exception {
                gridView.loadAuctionCards(seller.getAuctions(), (userType == BUYER)? "This Seller Doesn't Have Auctions" : "You Don't Have Any Auctions", SELLER);
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                loadingIndicator.stopRotating();
                profilePageContainer.setCenter(gridView.getGridView());
                callToAction.setDisable(false);
            }
        };
        new Thread(loadingProfileAuctions).start();
    }

    private void clearUserData() {
        photoViewer.setFill(Color.rgb(245,248,250));
        userName.setText("User Name");
        auctionsNumber.setText("-");
        followersNumber.setText("-");
    }

    public ScrollPane getProfilePage() {
        return profilePageScrollbar.getScrollView();
    }

    public static ProfilePage getInstance() {
        if (instance == null) {
            instance = new ProfilePage();
        }
        return instance;
    }
}
