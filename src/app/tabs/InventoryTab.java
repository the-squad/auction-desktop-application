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
import javafx.animation.Timeline;
import javafx.scene.control.ScrollPane;
import static app.Partials.SCROLLING_SPEED;

public class InventoryTab {

    private static InventoryTab instance;

    private ScrollPane inventoryPageContainer;
    private GridView gridView;

    private InventoryTab() {
        super();
        this.render();
    }

    private void render() {
        gridView = new GridView();

        //Scroll pane
        inventoryPageContainer = new ScrollPane(gridView.getGridView());
        inventoryPageContainer.setFitToWidth(true);
        inventoryPageContainer.setFitToHeight(true);
        inventoryPageContainer.getStyleClass().add("scrollbar");
        inventoryPageContainer.toBack();

        //Making the scrollbar faster
        gridView.getGridView().setOnScroll(event -> {
            double deltaY = event.getDeltaY() * SCROLLING_SPEED;
            double width = inventoryPageContainer.getContent().getBoundsInLocal().getWidth();
            double value = inventoryPageContainer.getVvalue();
            inventoryPageContainer.setVvalue(value + -deltaY/width); // deltaY/width to make the scrolling equally fast regardless of the actual width of the component
        });
    }

    public void loadCards() {
        gridView.loadItemCards(8);
    }

    public ScrollPane getInventoryTab() {
        return inventoryPageContainer;
    }

    public static InventoryTab getInstance() {
        if (instance == null) {
            instance = new InventoryTab();
        }
        return instance;
    }
}
