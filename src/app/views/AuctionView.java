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

import app.components.EmptyState;
import app.components.InputField;
import app.components.PhotosViewer;
import app.components.UserDetails;
import app.layouts.ScrollView;
import javafx.concurrent.Task;
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
import models.Image;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static app.Partials.*;

public class AuctionView {

    private static AuctionView instance;
    private Auction auction;

    private ScrollView auctionViewContainer;
    private BorderPane auctionCardContainer;
    private BorderPane parentContainer;
    private GridPane auctionDetailsContainer;
    private Label itemName;
    private Label itemDescription;
    private TextFlow priceBlock;
    private Text priceHeadline;
    private Text currentPrice;
    private GridPane biddingBlock;
    private InputField bidField;
    private Button submitBid;
    private UserDetails userDetails;
    private PhotosViewer photosViewer;

    private EmptyState emptyState;

    private static Thread loadingAuctionDataThread = null;

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
        itemDescription.setAlignment(Pos.TOP_LEFT);
        itemDescription.setMaxWidth(500);
        itemDescription.setMinHeight(100);

        //Price headline
        priceHeadline = new Text();
        priceHeadline.getStyleClass().add("price-headline");

        //Current bid
        currentPrice = new Text();
        currentPrice.getStyleClass().add("auction-bid");

        priceBlock = new TextFlow(priceHeadline, currentPrice);

        //Bidding field
        bidField = new InputField("Enter your bid", NUMBER, SHORT);

        submitBid = new Button("Bid");
        submitBid.setDisable(true);
        submitBid.getStyleClass().add("btn-primary");
        
        submitBid.setOnAction(e -> {
            if (Double.parseDouble(bidField.getValue()) < auction.getHighestPrice())
                bidField.markAsDanger("Enter a higher number!");
            else if ((Double.parseDouble(bidField.getValue()) + auction.getBiddingRate() ) < auction.getHighestPrice())
                bidField.markAsDanger("Bidding rate is " + auction.getBiddingRate());
            else {
                if (currentBuyer.bidOnAuction(this.auction, Double.parseDouble(bidField.getValue())))
                    currentPrice.setText(String.valueOf(auction.getHighestPrice()) + "$");
                else {
                    bidField.markAsDanger("Auction is finished!");
                    submitBid.setDisable(true);
                }
            }
        });

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
        auctionDetailsContainer.setMaxWidth(300);

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
        parentContainer = new BorderPane();
        parentContainer.setMaxWidth(CARD_WIDTH + 250);
        parentContainer.getStyleClass().add("card");
        parentContainer.setPadding(new Insets(TOP_DOWN, RIGHT_LEFT, TOP_DOWN, RIGHT_LEFT));
        parentContainer.setLeft(auctionDetailsContainer);
        parentContainer.setRight(photosViewer.getPhotos());

        //Card container
        auctionCardContainer = new BorderPane();
        auctionCardContainer.setCenter(parentContainer);

        //Auction view scrollbar
        auctionViewContainer = new ScrollView(auctionCardContainer);
        auctionViewContainer.getScrollView().setPadding(new Insets(20));

        //Empty state
        emptyState = new EmptyState();
        emptyState.setEmptyMessage("This Auction has been deleted or terminated");
    }

    public void fillAuctionData(Auction auction) {
        if (auction == null) {
            parentContainer.setCenter(emptyState.getEmptyState());
            parentContainer.setLeft(null);
            parentContainer.setRight(null);
        } else {
            if (parentContainer.getLeft() != null || parentContainer.getRight() != null) {
                parentContainer.setLeft(auctionDetailsContainer);
                parentContainer.setRight(photosViewer.getPhotos());
            }

            this.clearAuctionData();
            this.auction = auction;

            //Loading auction details
            Task<String> loadingAuctionData = new Task<String>() {
                String name;
                String description;
                String price;
                String sellerName;
                BufferedImage sellerImage;
                int sellerId;
                ArrayList<Image> auctionImages;

                @Override
                protected String call() throws Exception {
                    name = auction.getItem().getName();
                    description = auction.getItem().getDescription();
                    price = String.valueOf(auction.getHighestPrice());
                    sellerName = auction.getSeller().getName();
                    sellerImage = auction.getSeller().getPhoto();
                    sellerId = auction.getUserID();
                    auctionImages = auction.getItem().getItemPhotos();
                    return null;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    itemName.setText(name);
                    itemDescription.setText((description.length() == 0)? "There is no description..." : description);
                    currentPrice.setText(price + "$");
                    userDetails.setUserDetails(sellerName, sellerImage, sellerId);
                    photosViewer.setPhotos(auctionImages);
                    submitBid.setDisable(false);
                }
            };

            if (loadingAuctionDataThread == null || !loadingAuctionDataThread.isAlive()) {
                loadingAuctionDataThread = new Thread(loadingAuctionData);
                loadingAuctionDataThread.start();
            }
        }
    }

    private void clearAuctionData() {
        itemName.setText("Item");
        itemDescription.setText("Description");
        currentPrice.setText("Price" + "$");
        submitBid.setDisable(true);
        photosViewer.resetPhotoView(VIEW_MODE);
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
