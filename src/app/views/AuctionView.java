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
import app.components.PhotosViewer;
import app.components.UserDetails;
import app.layouts.ScrollView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import models.Auction;
import static app.Partials.*;

public class AuctionView {

    private static AuctionView instance;

    private ScrollView auctionViewContainer;
    private BorderPane auctionCardContainer;
    private GridPane parentContainer;
    private GridPane auctionDetailsContainer;
    private Label itemName;
    private Label itemDescription;
    private TextFlow priceBlock;
    private Text priceHeadline;
    private Text currentPrice;
    private Label biddersNumber;
    private GridPane biddingBlock;
    private InputField bidField;
    private Button submitBid;
    private UserDetails userDetails;
    private PhotosViewer photosViewer;

    private AuctionView() {
        this.render();
    }

    private void render() {
        //Auction name
        itemName = new Label();
        itemName.getStyleClass().add("auction-item-name");

        //Item description
        itemDescription = new Label();
        itemDescription.setWrapText(true);
        itemDescription.setMaxWidth(500);

        //Price headline
        priceHeadline = new Text();
        priceHeadline.getStyleClass().add("price-headline");

        //Current bid
        currentPrice = new Text();
        currentPrice.getStyleClass().add("auction-bid");

        priceBlock = new TextFlow(priceHeadline, currentPrice);

        //Number of bidders
        biddersNumber = new Label();
        biddersNumber.getStyleClass().add("bidders-number");

        //Bidding field
        bidField = new InputField("Enter your bid", NUMBER, SHORT);

        submitBid = new Button("Bid");
        submitBid.getStyleClass().add("btn-primary");

        biddingBlock = new GridPane();
        biddingBlock.setHgap(10);

        GridPane.setConstraints(bidField.getInputField(), 0, 0);
        GridPane.setConstraints(submitBid, 1, 0);

        biddingBlock.getChildren().addAll(bidField.getInputField(), submitBid);

        //Seller details
        userDetails = new UserDetails(FIT_DATA);

        //Photo viewer
        photosViewer = new PhotosViewer(VIEW_MODE);

        //Auction Details container
        auctionDetailsContainer = new GridPane();
        auctionDetailsContainer.setVgap(10);

        GridPane.setConstraints(itemName, 0 ,0);
        GridPane.setConstraints(itemDescription, 0 , 1);
        GridPane.setConstraints(priceBlock, 0, 2);
        GridPane.setConstraints(userDetails.getUserDetails(), 0, 3);
        GridPane.setConstraints(biddingBlock, 0, 4);

        auctionDetailsContainer.getChildren().addAll(itemName,
                                                     itemDescription,
                                                     priceBlock,
                                                     biddingBlock,
                                                     userDetails.getUserDetails());

        //Parent container
        parentContainer = new GridPane();
        parentContainer.setAlignment(Pos.TOP_CENTER);
        parentContainer.setMaxWidth(CARD_WIDTH + 250);
        parentContainer.setHgap(70);
        parentContainer.getStyleClass().add("card");
        parentContainer.setPadding(new Insets(TOP_DOWN, RIGHT_LEFT, TOP_DOWN, RIGHT_LEFT));

        GridPane.setConstraints(auctionDetailsContainer, 0, 0);
        GridPane.setConstraints(photosViewer.getPhotos(), 1, 0);
        parentContainer.getChildren().addAll(auctionDetailsContainer, photosViewer.getPhotos());

        //Card container
        auctionCardContainer = new BorderPane();
        auctionCardContainer.setCenter(parentContainer);

        //Auction view scrollbar
        auctionViewContainer = new ScrollView(auctionCardContainer);
        auctionViewContainer.getScrollView().setPadding(new Insets(20));
    }

    public void fillAuctionData(Auction auction) {
        itemName.setText(auction.getItemAuction().getName());
        itemDescription.setText(auction.getItemAuction().getDescription());
        currentPrice.setText(String.valueOf(auction.getHighestPrice()));
        userDetails.setUserDetails(auction.getSeller().getName(), auction.getSeller().getPhoto(), auction.getUserID());
    }

    private void clearAuctionData() {
        // TODO
    }

    public ScrollPane getAuctionView() {
        return auctionViewContainer.getScrollView();
    }

    public static AuctionView getInstance() {
        if (instance == null) {
            instance = new AuctionView();
        }
        return instance;
    }
}
