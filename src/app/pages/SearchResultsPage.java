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

package app.pages;

import app.GridView;
import app.components.Filter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import static app.Partials.*;

public class SearchResultsPage {

    private static SearchResultsPage instance;

    private ScrollPane searchResultsPageScrollbar;
    private BorderPane searchResultsPageContainer;
    private Filter filter;
    private GridView gridView;

    private SearchResultsPage() {
        this.render();
    }

    private void render() {
        //Filter
        filter = Filter.getInstance();

        //Grid view
        gridView = new GridView();
        //gridView.loadAuctionCards(25);

        //Search results container
        searchResultsPageContainer = new BorderPane();

        if (userType == BUYER) {
            BorderPane.setMargin(filter.getFilter(), new Insets(20, 0, 0, 0));
            BorderPane.setAlignment(filter.getFilter(), Pos.CENTER);
            searchResultsPageContainer.setTop(filter.getFilter());
        }
        searchResultsPageContainer.setCenter(gridView.getGridView());

        //Scroll pane
        searchResultsPageScrollbar = new ScrollPane(searchResultsPageContainer);
        searchResultsPageScrollbar.setFitToWidth(true);
        searchResultsPageScrollbar.setFitToHeight(true);
        searchResultsPageScrollbar.getStyleClass().add("scrollbar");
        searchResultsPageScrollbar.toBack();

        //Making the scrollbar faster
        searchResultsPageContainer.setOnScroll(event -> {
            double deltaY = event.getDeltaY() * SCROLLING_SPEED;
            double width = searchResultsPageScrollbar.getContent().getBoundsInLocal().getWidth();
            double value = searchResultsPageScrollbar.getVvalue();
            searchResultsPageScrollbar.setVvalue(value + -deltaY/width); // deltaY/width to make the scrolling equally fast regardless of the actual width of the component
        });
    }

    public ScrollPane getSearchResultsPage() {
        return searchResultsPageScrollbar;
    }

    public static SearchResultsPage getInstance() {
        if (instance == null) {
            instance = new SearchResultsPage();
        }
        return instance;
    }
}
