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

import app.components.SellerDetails;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import static app.Partials.*;

public class AuctionView {

    private static AuctionView instance;

    private ScrollPane auctionViewContainer;
    private GridPane auctionDetailsContainer;
    private Label auctionName;
    private SellerDetails sellerDetails;
    private Label auctionDescription;
    private Label priceHeadline;
    private Label currentPrice;
    private Label biddersNumber;

    private AuctionView() {
        this.render();
    }

    private void render() {
        //Auction name
        auctionName = new Label("Moto 360");
        auctionName.getStyleClass().add("item-name");

        //Seller details
        sellerDetails = new SellerDetails(RIGHT_PADDING);

        //Auction Details container
        auctionDetailsContainer = new GridPane();
        auctionDetailsContainer.getStyleClass().add("card");
        auctionDetailsContainer.setPadding(new Insets(25));

        GridPane.setConstraints(auctionName, 0 ,0);
        GridPane.setConstraints(sellerDetails.getSellerDetails(), 0, 1);

        auctionDetailsContainer.getChildren().addAll(auctionName,
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
