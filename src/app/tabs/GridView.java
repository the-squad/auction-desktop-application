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

package app.tabs;

import app.components.AuctionCard;
import app.components.LoadingIndicator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import static app.Partials.userType;

public class GridView {

    protected ScrollPane tabScrollbar;
    protected GridPane cardsContainer;
    protected LoadingIndicator loadingIndicator;

    public GridView() {
        this.render();
    }

    private void render() {
        //Cards container
        cardsContainer = new GridPane();
        cardsContainer.setVgap(20);
        cardsContainer.setHgap(20);
        cardsContainer.setAlignment(Pos.CENTER);

        //Loading bar
        loadingIndicator = new LoadingIndicator();

        //Scroll bar container
        tabScrollbar = new ScrollPane(cardsContainer);
        tabScrollbar.setFitToWidth(true);
        tabScrollbar.setFitToHeight(true);
        tabScrollbar.getStyleClass().add("scrollbar");
        tabScrollbar.toBack();

        //Making the scrollbar faster
        cardsContainer.setOnScroll(event -> {
            double deltaY = event.getDeltaY() * 3;
            double width = tabScrollbar.getContent().getBoundsInLocal().getWidth();
            double value = tabScrollbar.getVvalue();
            tabScrollbar.setVvalue(value + -deltaY/width); // deltaY/width to make the scrolling equally fast regardless of the actual width of the component
        });
    }

    public void loadCards(int cardsNumber) {
        AuctionCard cards[] = new AuctionCard[cardsNumber];

        for (int counter = 0; counter < cardsNumber; counter++)
            cards[counter] = new AuctionCard(userType);

        int counter = 0;
        for (int rowCounter = 0; rowCounter < (cards.length / 4) + 1; rowCounter++) {
            for (int columnCounter = 0; columnCounter < 4; columnCounter++) {
                if (counter >= cards.length) break;
                cardsContainer.setConstraints(cards[counter].getAuctionCard(), columnCounter, rowCounter);
                cardsContainer.getChildren().add(cards[counter].getAuctionCard());
                counter++;
            }
        }
    }

    public void clearCards() {
        cardsContainer.getChildren().removeAll();
    }
}
