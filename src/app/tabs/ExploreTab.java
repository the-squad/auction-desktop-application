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

import app.GridView;
import app.components.CategoriesPanel;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import static app.Partials.SCROLLING_SPEED;

public class ExploreTab {

    private static ExploreTab instance;

    private ScrollPane exploreTabScrollbar;
    private BorderPane exploreTabContainer;
    private CategoriesPanel tabs;
    private GridView gridView;

    private ExploreTab() {
        super();
        this.render();
    }

    private void render() {
        //Categories tabs
        tabs = new CategoriesPanel("All", "Tech", "Music", "Cars", "Boards", "Buildings", "Planes", "Boats", "T.Vs");

        //Explore tab container
        exploreTabContainer = new BorderPane();

        exploreTabContainer.setTop(tabs.getCategoriesTabs());
        BorderPane.setMargin(tabs.getCategoriesTabs(), new Insets(20, 0, 0, 0));

        gridView = new GridView();
        exploreTabContainer.setCenter(gridView.getGridView());

        //Scroll pane
        exploreTabScrollbar = new ScrollPane(exploreTabContainer);
        exploreTabScrollbar.setFitToWidth(true);
        exploreTabScrollbar.setFitToHeight(true);
        exploreTabScrollbar.getStyleClass().add("scrollbar");
        exploreTabScrollbar.toBack();

        //Making the scrollbar faster
        exploreTabContainer.setOnScroll(event -> {
            double deltaY = event.getDeltaY() * SCROLLING_SPEED;
            double width = exploreTabScrollbar.getContent().getBoundsInLocal().getWidth();
            double value = exploreTabScrollbar.getVvalue();
            exploreTabScrollbar.setVvalue(value + -deltaY/width); // deltaY/width to make the scrolling equally fast regardless of the actual width of the component
        });
    }

    public void loadCards() {
        gridView.loadAuctionCards(10);
    }

    public ScrollPane getExploreTab() {
        return exploreTabScrollbar;
    }

    public static ExploreTab getInstance() {
        if (instance == null) {
            instance = new ExploreTab();
        }
        return instance;
    }
}
