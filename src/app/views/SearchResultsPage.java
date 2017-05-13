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

import app.components.LoadingIndicator;
import app.layouts.GridView;
import app.components.Filter;
import app.layouts.ScrollView;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import models.Auction;

import java.util.ArrayList;

import static app.Partials.*;

public class SearchResultsPage {

    private static SearchResultsPage instance;

    private ScrollView searchResultsPageScrollbar;
    private BorderPane searchResultsPageContainer;
    private Filter filter;
    private GridView gridView;
    private LoadingIndicator loadingIndicator;

    private static Thread searchPageThread;

    private SearchResultsPage() {
        this.render();
    }

    private void render() {
        //Filter
        filter = Filter.getInstance();

        //Grid view
        gridView = new GridView();

        //Search results container
        searchResultsPageContainer = new BorderPane();

        if (userType == BUYER) {
            BorderPane.setMargin(filter.getFilter(), new Insets(20, 0, 0, 0));
            BorderPane.setAlignment(filter.getFilter(), Pos.CENTER);
            searchResultsPageContainer.setTop(filter.getFilter());
        }
        searchResultsPageContainer.setCenter(gridView.getGridView());

        //Scroll pane
        searchResultsPageScrollbar = new ScrollView(searchResultsPageContainer);

        //Loading indicator
        loadingIndicator = new LoadingIndicator();
        loadingIndicator.setLoadingMessage("Getting Auctions...");
    }

    public void loadCards(ArrayList<Auction> cards) {
        searchResultsPageContainer.setCenter(loadingIndicator.getLoadingIndicator());
        loadingIndicator.startRotating();

        Task<String> loadCards = new Task<String>() {
            @Override
            protected String call() throws Exception {
                gridView.loadAuctionCards(cards, "We Couldn't Find Matching Results", userType);
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                searchResultsPageContainer.setCenter(gridView.getGridView());
                loadingIndicator.stopRotating();
            }
        };

        if (searchPageThread == null || !searchPageThread.isAlive()) {
            searchPageThread = new Thread(loadCards);
            searchPageThread.start();
        }
    }

    public ScrollPane getSearchResultsPage() {
        return searchResultsPageScrollbar.getScrollView();
    }

    public static SearchResultsPage getInstance() {
        if (instance == null) {
            instance = new SearchResultsPage();
        }
        return instance;
    }
}
