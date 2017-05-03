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

import app.GridView;
import javafx.scene.control.ScrollPane;
import static app.Partials.SCROLLING_SPEED;

public class AuctionsTab {

    private static AuctionsTab instance;

    private ScrollPane auctionsPageContainer;
    private GridView gridView;

    private AuctionsTab() {
        super();
        this.render();
    }

    private void render() {
        gridView = new GridView();

        //Scroll pane
        auctionsPageContainer = new ScrollPane(gridView.getGridView());
        auctionsPageContainer.setFitToWidth(true);
        auctionsPageContainer.setFitToHeight(true);
        auctionsPageContainer.getStyleClass().add("scrollbar");
        auctionsPageContainer.toBack();

        //Making the scrollbar faster
        gridView.getGridView().setOnScroll(event -> {
            double deltaY = event.getDeltaY() * SCROLLING_SPEED;
            double width = auctionsPageContainer.getContent().getBoundsInLocal().getWidth();
            double value = auctionsPageContainer.getVvalue();
            auctionsPageContainer.setVvalue(value + -deltaY/width); // deltaY/width to make the scrolling equally fast regardless of the actual width of the component
        });
    }

    public void loadCards() {
        gridView.loadAuctionCards(16);
    }

    public ScrollPane getAuctionsTab() {
        return auctionsPageContainer;
    }

    public void destroy() {
        instance = null;
    }

    public static AuctionsTab getInstance() {
        if (instance == null) {
            instance = new AuctionsTab();
        }
        return instance;
    }
}
