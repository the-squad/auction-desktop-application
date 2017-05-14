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
import app.components.*;
import app.layouts.ScrollView;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import models.Auction;
import models.Buyer;
import models.Image;

import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import static app.Partials.*;

public class AuctionView {

    private static AuctionView instance;
    private Auction auction;

    private ScrollView auctionViewContainer;
    private BorderPane auctionCardContainer;
    private BorderPane parentContainer;
    private GridPane auctionDetailsContainer;
    private GridPane headlineContainer;
    private Label itemName;
    private SubscribeButton subscribeButton;
    private Label itemDescription;
    private TextFlow priceBlock;
    private Text priceHeadline;
    private Text currentPrice;
    private GridPane biddingBlock;
    private InputField bidField;
    private Button submitBid;
    private UserDetails userDetails;
    private Button editAuction;
    private PhotosViewer photosViewer;

    private EmptyState emptyState;
    private DecimalFormat decimalFormat;

    private static Thread loadingAuctionDataThread = null;

    private AuctionView() {
        this.render();
    }

    private void render() {
        //Auction name
        itemName = new Label();
        itemName.getStyleClass().add("auction-item-name");

        //Subscribe button
        if (userType == BUYER) {
            subscribeButton = new SubscribeButton();
        }

        //Item and subscribe container
        headlineContainer = new GridPane();
        headlineContainer.setMaxWidth(350);

        ColumnConstraints itemNameCol = new ColumnConstraints();
        itemNameCol.setPercentWidth(85);

        ColumnConstraints subscribeButtonCol = new ColumnConstraints();
        subscribeButtonCol.setPercentWidth(15);
        subscribeButtonCol.setHalignment(HPos.RIGHT);

        headlineContainer.getColumnConstraints().addAll(itemNameCol, subscribeButtonCol);
        GridPane.setConstraints(itemName, 0, 0);
        headlineContainer.getChildren().add(itemName);

        if (userType == BUYER) {
            GridPane.setConstraints(subscribeButton.getSubscribeButton(), 1, 0);
            headlineContainer.getChildren().add(subscribeButton.getSubscribeButton());
        }

        //Item description
        itemDescription = new Label();
        itemDescription.getStyleClass().add("auction-item-description");
        itemDescription.setWrapText(true);
        itemDescription.setAlignment(Pos.TOP_LEFT);
        itemDescription.setMaxWidth(300);
        itemDescription.setMinHeight(100);

        //Price headline
        priceHeadline = new Text("Highest Bid:\t");
        priceHeadline.getStyleClass().add("price-headline");

        //Current bid
        decimalFormat = new DecimalFormat("#");
        decimalFormat.setMaximumFractionDigits(2);

        currentPrice = new Text();
        currentPrice.getStyleClass().add("auction-bid");

        priceBlock = new TextFlow(priceHeadline, currentPrice);

        //Bidding field
        bidField = new InputField("Enter your bid", DECIMAL_NUMBER, 200);

        submitBid = new Button("Bid");
        submitBid.setDisable(true);
        submitBid.getStyleClass().addAll("btn-primary", "bid-btn");
        submitBid.setTranslateY(-1);
        
        submitBid.setOnAction(e -> {
            if (Double.parseDouble(bidField.getValue()) < auction.getHighestPrice())
                bidField.markAsDanger("Enter a higher number!");
            else if (Double.parseDouble(bidField.getValue()) < auction.getHighestPrice() + auction.getBiddingRate())
                bidField.markAsDanger("Bidding rate is " + auction.getBiddingRate());
            else {
                if (currentBuyer.bidOnAuction(this.auction, Double.parseDouble(bidField.getValue()))) {
                    currentPrice.setText(decimalFormat.format(auction.getHighestPrice()) + "$");
                    bidField.clear();
                } else {
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
        if (userType == BUYER) {
            userDetails = new UserDetails(FIT_DATA);
            userDetails.getUserDetails().setMaxWidth(200);
        } else {
            editAuction = new Button("Update Auction");
            editAuction.getStyleClass().add("btn-primary");
            editAuction.setTranslateY(20);

            editAuction.setOnAction(e -> {
                if (!Auction.checkAuctionStatus(auction.getId())) {
                    editAuction.setDisable(true);
                    editAuction.setText("Auction already started!");
                } else {
                    AuctionDetails.getInstance().fillAuctionData(auction);
                    Navigator.viewPage(AUCTION_DETAILS, "Update " + auction.getItem().getName() + "'s Auction");
                }
            });
        }

        //Photo viewer
        photosViewer = new PhotosViewer(VIEW_MODE);

        //Auction Details container
        auctionDetailsContainer = new GridPane();
        auctionDetailsContainer.setVgap(8);
        auctionDetailsContainer.setMaxWidth(350);

        GridPane.setConstraints(headlineContainer, 0 ,0);
        GridPane.setConstraints(itemDescription, 0 , 1);
        GridPane.setConstraints(priceBlock, 0, 3);

        if (userType == BUYER) {
            GridPane.setConstraints(userDetails.getUserDetails(), 0, 2);
            GridPane.setConstraints(biddingBlock, 0, 4);
        } else {
            GridPane.setConstraints(editAuction, 0, 4);
        }

        auctionDetailsContainer.getChildren().addAll(headlineContainer,
                                                     itemDescription,
                                                     priceBlock);

        if (userType == BUYER)
            auctionDetailsContainer.getChildren().addAll(biddingBlock,
                    userDetails.getUserDetails());
        else
            auctionDetailsContainer.getChildren().add(editAuction);

        //Parent container
        parentContainer = new BorderPane();
        parentContainer.setMaxWidth(CARD_WIDTH + 250);
        parentContainer.getStyleClass().add("card");
        parentContainer.setPadding(new Insets(TOP_DOWN, RIGHT_LEFT, TOP_DOWN, RIGHT_LEFT));
        parentContainer.setLeft(auctionDetailsContainer);

        BorderPane.setMargin(photosViewer.getPhotos(), new Insets(0, 0, 0, 30));
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
            if (userType == BUYER) {
                subscribeButton.setAuctionId(auction.getId());

                if (auction.getFollowers() != null) {
                    for (Buyer buyer : auction.getFollowers()) {
                        if (Objects.equals(buyer.getId(), currentBuyer.getId())) {
                            subscribeButton.markAsSubscribed();
                        }
                    }
                }
            }

            //Loading auction details
            Task<String> loadingAuctionData = new Task<String>() {
                String name;
                String description;
                int price;
                String sellerName;
                BufferedImage sellerImage;
                int sellerId;
                ArrayList<Image> auctionImages;

                @Override
                protected String call() throws Exception {
                    name = auction.getItem().getName();
                    description = auction.getItem().getDescription().replaceAll("\\n\\n+", "\n");
                    price = (int) auction.getHighestPrice();
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
                    currentPrice.setText(decimalFormat.format(price) + "$");
                    photosViewer.setPhotos(auctionImages);

                    if (userType == BUYER) {
                        userDetails.setUserDetails(sellerName, sellerImage, sellerId);
                        if (Auction.checkAuctionStatus(auction.getId()))
                            submitBid.setDisable(true);
                        else
                            submitBid.setDisable(false);
                    }
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
        bidField.clear();
        if (userType == SELLER) {
            editAuction.setDisable(false);
            editAuction.setText("Update Auction");
        }
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
