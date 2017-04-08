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
import app.components.CategoriesPanel;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;

public class ExploreTab extends GridView {

    private static ExploreTab instance;

    private AuctionCard auctionCards[];

    private BorderPane exploreTabContainer;
    private CategoriesPanel tabs;

    private ExploreTab() {
        super();
        this.render();
    }

    private void render() {
        //Categories tabs
        tabs = new CategoriesPanel("All", "Tech", "Music", "Cars", "Boards", "Buildings", "Planes", "Boats", "T.Vs");

        //Explore tab container
        exploreTabContainer = new BorderPane();
        exploreTabContainer.setPadding(new Insets(15, 0, 0, 0));

        exploreTabContainer.setTop(tabs.getCategoriesTabs());
        exploreTabContainer.setMargin(tabs.getCategoriesTabs(), new Insets(0, 0, 15, 0));

        exploreTabContainer.setCenter(tabScrollbar);
        exploreTabContainer.setMargin(tabScrollbar, new Insets(0,0,15,0));
    }

    public BorderPane getExploreTab() {
        return exploreTabContainer;
    }

    public static ExploreTab getInstance() {
        if (instance == null) {
            instance = new ExploreTab();
        }
        return instance;
    }
}
