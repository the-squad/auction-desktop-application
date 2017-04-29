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
import javafx.scene.control.ScrollPane;
import static app.Partials.SCROLLING_SPEED;

public class FeedTab {

    private static FeedTab instance;

    private ScrollPane feedPageContainer;
    private GridView gridView;

    private FeedTab() {
        super();
        this.render();
    }

    private void render() {
        gridView = new GridView();

        //Scroll pane
        feedPageContainer = new ScrollPane(gridView.getGridView());
        feedPageContainer.setFitToWidth(true);
        feedPageContainer.setFitToHeight(true);
        feedPageContainer.getStyleClass().add("scrollbar");
        feedPageContainer.toBack();

        //Making the scrollbar faster
        gridView.getGridView().setOnScroll(event -> {
            double deltaY = event.getDeltaY() * SCROLLING_SPEED;
            double width = feedPageContainer.getContent().getBoundsInLocal().getWidth();
            double value = feedPageContainer.getVvalue();
            feedPageContainer.setVvalue(value + -deltaY/width); // deltaY/width to make the scrolling equally fast regardless of the actual width of the component
        });
    }

    public void loadCards() {
        gridView.loadAuctionCards(10);
    }

    public ScrollPane getFeedTab() {
        return feedPageContainer;
    }

    public static FeedTab getInstance() {
        if (instance == null) {
            instance = new FeedTab();
        }
        return instance;
    }
}
