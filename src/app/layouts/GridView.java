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

package app.layouts;

import app.components.AuctionCard;
import app.components.EmptyState;
import app.components.ItemCard;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import models.Auction;
import models.Item;

import java.util.ArrayList;

import static app.Partials.*;

public class GridView {

    private GridPane cardsContainer;
    private Label extraSpace;
    private EmptyState emptyState;

    private ArrayList<AuctionCard> auctionCards;
    private ArrayList<ItemCard> itemCards;

    public GridView() {
        this.render();
    }

    private void render() {
        //Creating empty array lists
        auctionCards = new ArrayList<>();
        itemCards = new ArrayList<>();

        //Cards container
        cardsContainer = new GridPane();
        cardsContainer.setVgap(20);
        cardsContainer.setHgap(20);

        cardsContainer.setTranslateY(20);

        //Creating empty state
        emptyState = new EmptyState();

        //To give extra space at the bottom of the scrollbar
        extraSpace = new Label("");
        extraSpace.setMinHeight(1);
    }

    public void loadAuctionCards(ArrayList<Auction> auctions, String emptyStateMessage) {
        cardsContainer.getChildren().clear();
        for (AuctionCard card : auctionCards)
            card = null;
        auctionCards.clear();

        if (auctions == null || auctions.size() == 0) {
            this.viewEmptyState(emptyStateMessage);
        } else {
            for (Auction auction : auctions)
                auctionCards.add(new AuctionCard(userType, auction));

            int counter = 0;
            for (AuctionCard auctionCard : auctionCards) {
                GridPane.setConstraints(auctionCard.getCard(), counter % 4, counter / 4);
                cardsContainer.getChildren().add(auctionCard.getCard());
                counter++;
            }
            this.addMoreSpace(auctions.size());
        }
    }

    public void loadItemCards(ArrayList<Item> items, String emptyStateMessage) {
        cardsContainer.getChildren().clear();
        itemCards.clear();

        if (items == null || items.size() == 0) {
            this.viewEmptyState(emptyStateMessage);
        } else {
            for (Item item : items)
                itemCards.add(new ItemCard(item));

            int counter = 0;
            for (ItemCard itemCard : itemCards) {
                GridPane.setConstraints(itemCard.getCard(), counter % 4, counter / 4);
                cardsContainer.getChildren().add(itemCard.getCard());
                counter++;
            }
            this.addMoreSpace(items.size());
        }
    }

    private void addMoreSpace(int size) {
        GridPane.setConstraints(extraSpace, 0 , size / 4 + 2);
        cardsContainer.setAlignment(Pos.TOP_CENTER);
        cardsContainer.getChildren().add(extraSpace);
    }

    private void viewEmptyState(String emptyMessage) {
        emptyState.setEmptyMessage(emptyMessage);
        GridPane.setConstraints(emptyState.getEmptyState(), 0, 0);
        cardsContainer.getChildren().add(emptyState.getEmptyState());
        cardsContainer.setAlignment(Pos.CENTER);
    }

    public GridPane getGridView() {
        return cardsContainer;
    }
}
