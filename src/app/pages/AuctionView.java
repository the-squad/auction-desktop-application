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

import app.components.InputField;
import app.components.SellerDetails;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import static app.Partials.*;

public class AuctionView {

    private static AuctionView instance;

    private ScrollPane auctionViewContainer;
    private GridPane auctionDetailsContainer;
    private Label itemName;
    private Label itemDescription;
    private TextFlow priceBlock;
    private Text priceHeadline;
    private Text currentPrice;
    private Label biddersNumber;
    private GridPane biddingBlock;
    private InputField bidField;
    private Button sumbitBid;
    private SellerDetails sellerDetails;

    private AuctionView() {
        this.render();
    }

    private void render() {
        //Auction name
        itemName = new Label("Moto 360");
        itemName.getStyleClass().add("auction-item-name");

        //Item description
        itemDescription = new Label("Motorola Moto 360 46mm (2nd gen) - user opinions and reviews. ... Motorola " +
                "00820NARTL 2nd Gen Smart Watch Smartwatch Moto 360 Cognac 46mm. ... Motorola Moto 360 2nd Gen Mens " +
                "46mm SmartWatch Black Stainless Steel Android.");
        itemDescription.getStyleClass().add("auction-item-description");
        itemDescription.setWrapText(true);
        itemDescription.setMaxWidth(500);

        //Price headline
        priceHeadline = new Text("Current Bid");
        priceHeadline.getStyleClass().add("price-headline");

        //Current bid
        currentPrice = new Text("1,000$");
        currentPrice.getStyleClass().add("auction-bid");

        priceBlock = new TextFlow(priceHeadline, currentPrice);

        //Number of bidders
        biddersNumber = new Label("15 bidder");
        biddersNumber.getStyleClass().add("bidders-number");

        //Bidding field
        bidField = new InputField("Enter your bid", NUMBER);

        sumbitBid = new Button("Bid");
        sumbitBid.getStyleClass().add("btn-primary");

        biddingBlock = new GridPane();
        biddingBlock.setHgap(25);

        GridPane.setConstraints(bidField.getInputField(), 0, 0);
        GridPane.setConstraints(sumbitBid, 1, 0);

        biddingBlock.getChildren().addAll(bidField.getInputField(), sumbitBid);

        //Seller details
        sellerDetails = new SellerDetails(RIGHT_PADDING);

        //Auction Details container
        auctionDetailsContainer = new GridPane();
        auctionDetailsContainer.getStyleClass().add("card");
        auctionDetailsContainer.setPadding(new Insets(35, 50, 35, 50));
        auctionDetailsContainer.setVgap(10);

        GridPane.setConstraints(itemName, 0 ,0);
        GridPane.setConstraints(itemDescription, 0 , 1);
        GridPane.setConstraints(priceBlock, 0, 2);
        GridPane.setConstraints(sellerDetails.getSellerDetails(), 0, 3);
        GridPane.setConstraints(biddingBlock, 0, 4);

        auctionDetailsContainer.getChildren().addAll(itemName,
                                                     itemDescription,
                                                     priceBlock,
                                                     biddingBlock,
                                                     sellerDetails.getSellerDetails());

        //Auction view scrollbar
        auctionViewContainer = new ScrollPane(auctionDetailsContainer);
        auctionViewContainer.setFitToWidth(true);
        auctionViewContainer.setFitToHeight(true);
        auctionViewContainer.getStyleClass().add("scrollbar");
        auctionViewContainer.toBack();
        auctionViewContainer.setPadding(new Insets(20, 50, 0, 50));

        //Making the scrollbar faster
        auctionDetailsContainer.setOnScroll(event -> {
            double deltaY = event.getDeltaY() * SCROLLING_SPEED;
            double width = auctionViewContainer.getContent().getBoundsInLocal().getWidth();
            double value = auctionViewContainer.getVvalue();
            auctionViewContainer.setVvalue(value + -deltaY/width); // deltaY/width to make the scrolling equally fast regardless of the actual width of the component
        });
    }

    public void fillAuctionData() {
        // TODO
    }

    private void clearAuctionData() {
        // TODO
    }

    public ScrollPane getAuctionView() {
        return auctionViewContainer;
    }

    public static AuctionView getInstance() {
        if (instance == null) {
            instance = new AuctionView();
        }
        return instance;
    }
}
