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

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Muhammad
 */
public class AuctionCard {

    private BorderPane auctionCardContainer;
    private Label auctionPictureView;
    private Rectangle imageClipper;

    private GridPane auctionDetailsContainer;
    private Label itemName;
    private Label currentBid;
    private Label auctionStatus;

    private GridPane userDetailsContainer;
    private Rectangle userPicture;
    private Label username;

    public AuctionCard() {
        this.render();
    }

    private void render() {
        //Item picture
        imageClipper = new Rectangle(250, 175);
        imageClipper.setArcWidth(5);
        imageClipper.setArcHeight(5);

        auctionPictureView = new Label();
        auctionPictureView.getStyleClass().add("item-image");
        auctionPictureView.setClip(imageClipper);

        //Item name
        itemName = new Label("Google Pixel");
        itemName.getStyleClass().add("item-name");

        //Current bid
        currentBid = new Label("$650");
        currentBid.getStyleClass().add("item-bid");

        //Auction status
        auctionStatus = new Label("1 hour to start".toUpperCase());
        auctionStatus.getStyleClass().add("auction-status");


        //User picture
        userPicture = new Rectangle(30, 30,
                new ImagePattern((
                        new Image(getClass().getResourceAsStream("/assets/picture.jpg"),
                                30,
                                30,
                                true,
                                true))));
        userPicture.setArcHeight(50);
        userPicture.setArcWidth(50);
        userPicture.getStyleClass().add("user-picture");

        //User name
        username = new Label("Muhammad Tarek");
        username.getStyleClass().add("user-name");

        //User details container
        userDetailsContainer = new GridPane();
        userDetailsContainer.getStyleClass().add("user-container");
        userDetailsContainer.setMinWidth(250);
        userDetailsContainer.setPadding(new Insets(10, 15, 10, 15));

        userDetailsContainer.setConstraints(userPicture, 0, 0);
        userDetailsContainer.setMargin(userPicture, new Insets(0, 14, 0, 0));

        userDetailsContainer.setConstraints(username, 1, 0);

        userDetailsContainer.getChildren().addAll(userPicture, username);

        //Auction details container
        auctionDetailsContainer = new GridPane();
        auctionDetailsContainer.setPadding(new Insets(10, 0, 0, 0));

        auctionDetailsContainer.setConstraints(itemName, 0, 0);
        auctionDetailsContainer.setMargin(itemName, new Insets(0, 15, 2, 15));

        auctionDetailsContainer.setConstraints(currentBid, 0, 1);
        auctionDetailsContainer.setMargin(currentBid, new Insets(1, 15, 4, 15));

        auctionDetailsContainer.setConstraints(auctionStatus, 0, 2);
        auctionDetailsContainer.setMargin(auctionStatus, new Insets(1, 15, 2, 15));

        auctionDetailsContainer.setConstraints(userDetailsContainer, 0, 3);

        auctionDetailsContainer.getChildren().addAll(itemName, currentBid, auctionStatus, userDetailsContainer);

        //Auction card container
        auctionCardContainer = new BorderPane();
        auctionCardContainer.getStyleClass().add("card");
        auctionCardContainer.setTop(auctionPictureView);
        auctionCardContainer.setBottom(auctionDetailsContainer);
    }

    public BorderPane getAuctionCard() {
        return auctionCardContainer;
    }

    public void setDetails() {
        //TODO use the auction object to fill in the data
    }
}
