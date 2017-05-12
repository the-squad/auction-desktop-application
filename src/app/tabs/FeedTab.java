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

package app.tabs;

import app.components.LoadingIndicator;
import app.layouts.GridView;
import app.layouts.ScrollView;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import models.Auction;

import java.util.ArrayList;

import static app.Partials.*;

public class FeedTab {

    private static FeedTab instance;

    private ScrollView scrollView;
    private BorderPane centerPane;
    private GridView gridView;
    private LoadingIndicator loadingIndicator;

    private FeedTab() {
        this.render();
    }

    private void render() {
        //Grid view
        gridView = new GridView();

        //Border pane
        centerPane = new BorderPane();

        //Loading indicator
        loadingIndicator = new LoadingIndicator();
        loadingIndicator.setLoadingMessage("Getting your feed...");

        //Scroll pane
        scrollView = new ScrollView(centerPane);
    }

    public void loadCards(ArrayList<Auction> auctions) {
        loadingIndicator.startRotating();
        centerPane.setCenter(loadingIndicator.getLoadingIndicator());

        //Loading cards
        Task<String> loadingCards = new Task<String>() {
            @Override
            protected String call() throws Exception {
                gridView.loadAuctionCards(auctions, "There is no auctions from people you follow", userType);
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                loadingIndicator.stopRotating();
                centerPane.setCenter(gridView.getGridView());
            }
        };
        new Thread(loadingCards).start();
    }

    public void destroy() {
        instance = null;
    }

    public ScrollPane getFeedTab() {
        return scrollView.getScrollView();
    }

    public static FeedTab getInstance() {
        if (instance == null) {
            instance = new FeedTab();
        }
        return instance;
    }
}
