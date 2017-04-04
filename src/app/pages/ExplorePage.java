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

import app.components.AuctionCard;
import app.components.CategoriesPanel;
import com.sun.jndi.cosnaming.CNCtx;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Muhammad
 */
public class ExplorePage {

    private static ExplorePage instance;

    private BorderPane exploreTabContainer;
    private ScrollPane exploreTabScrollbar;
    private CategoriesPanel tabs;

    private GridPane cardsContainer;
    private AuctionCard auctionCard[];

    private ExplorePage() {
        this.render();
    }

    private void render() {
        //Categories tabs
        tabs = new CategoriesPanel("All", "Tech", "Music", "Cars", "Boards", "Buildings", "Planes", "Boats", "T.Vs");

        //Auctions cards view
        cardsContainer = new GridPane();
        cardsContainer.setHgap(35);
        cardsContainer.setVgap(20);

        //Explore tab container
        exploreTabContainer = new BorderPane();
        Platform.runLater( () -> exploreTabContainer.requestFocus() );
        exploreTabContainer.setPadding(new Insets(20, 0, 0, 0));

        exploreTabContainer.setTop(tabs.getCategoriesTabs());
        exploreTabContainer.setMargin(tabs.getCategoriesTabs(), new Insets(0, 0, 25, 0));

        exploreTabContainer.setCenter(cardsContainer);
        exploreTabContainer.setAlignment(cardsContainer, Pos.CENTER);

        //Scroll bar container
        exploreTabScrollbar = new ScrollPane(exploreTabContainer);
        exploreTabScrollbar.setFitToWidth(true);
        exploreTabScrollbar.getStyleClass().add("scrollbar");
        exploreTabScrollbar.toBack();

        //Making the scrollbar faster
        exploreTabContainer.setOnScroll(event -> {
            double deltaY = event.getDeltaY() * 3;
            double width = exploreTabScrollbar.getContent().getBoundsInLocal().getWidth();
            double value = exploreTabScrollbar.getVvalue();
            exploreTabScrollbar.setVvalue(value + -deltaY/width); // deltaY/width to make the scrolling equally fast regardless of the actual width of the component
        });
    }

    public void loadExploreCards(int cardsNumber) {
        cardsContainer.setMaxWidth(exploreTabContainer.getWidth());
        //Auction card
        auctionCard = new AuctionCard[cardsNumber];

        for (int counter = 0; counter < cardsNumber; counter++) {
            auctionCard[counter] = new AuctionCard();
        }

        int counter = 0;
        for (int rowCounter = 0; rowCounter < (cardsNumber / 4) + 1; rowCounter++) {
            for (int coloumnCounter = 0; coloumnCounter < 4; coloumnCounter++) {
                if (counter >= cardsNumber) break;
                cardsContainer.setConstraints(auctionCard[counter].getAuctionCard(), coloumnCounter, rowCounter);
                cardsContainer.getChildren().add(auctionCard[counter].getAuctionCard());
                counter++;
            }
        }
    }

    public ScrollPane getExploreTab() {
        return exploreTabScrollbar;
    }

    public static ExplorePage getInstance() {
        if (instance == null) {
            instance = new ExplorePage();
        }
        return instance;
    }
}
