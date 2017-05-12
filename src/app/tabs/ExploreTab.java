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
import app.components.CategoriesPanel;
import app.layouts.ScrollView;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import models.Category;

import static app.Partials.currentBuyer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExploreTab {

    private static ExploreTab instance;

    private ScrollView scrollView;
    private BorderPane exploreTabContainer;
    private CategoriesPanel tabs;
    private GridView gridView;
    private LoadingIndicator loadingIndicator;

    private String loadingMessage;
    private static Thread exploreThread = null;

    private ExploreTab() {
        this.render();
    }

    private void render() {
        //Categories tabs
        tabs = new CategoriesPanel(Category.getCategories());

        //Explore tab container
        exploreTabContainer = new BorderPane();

        exploreTabContainer.setTop(tabs.getCategoriesTabs());
        BorderPane.setMargin(tabs.getCategoriesTabs(), new Insets(20, 0, 0, 0));

        gridView = new GridView();

        //Loading indicator
        loadingIndicator = new LoadingIndicator();

        //Scroll pane
        scrollView = new ScrollView(exploreTabContainer);
    }

    public void loadCards(Category category) {
        loadingIndicator.startRotating();
        if (category.getName().equals("All"))
            loadingMessage = "Getting You All Auctions";
        else
            loadingMessage = "Getting You Auctions in " + category.getName();

        loadingIndicator.setLoadingMessage(loadingMessage);
        exploreTabContainer.setCenter(loadingIndicator.getLoadingIndicator());

        //Loading cards
        Task<String> loadingCards = new Task<String>() {
            @Override
            protected String call() throws Exception {
                gridView.loadAuctionCards(currentBuyer.exploreAuctions(category), "We Couldn't Find Auctions In " + category.getName());
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                loadingIndicator.stopRotating();
                exploreTabContainer.setCenter(gridView.getGridView());
            }
        };
        if (exploreThread==null || !exploreThread.isAlive()){
            exploreThread = new Thread(loadingCards);
            exploreThread.start();
        }
    }

    public void destroy() {
        instance = null;
    }

    public ScrollPane getExploreTab() {
        return scrollView.getScrollView();
    }

    public static ExploreTab getInstance() {
        if (instance == null) {
            instance = new ExploreTab();
        }
        return instance;
    }
}
