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
import app.components.ItemCard;
import javafx.scene.control .ScrollPane;
import javafx.scene.layout.GridPane;

public class InventoryTab extends GridView {

    private static InventoryTab instance;

    private ScrollPane inventoryPageContainer;

    private InventoryTab() {
        super();
        this.render();
    }

    private void render() {
        //TODO
    }

    public void loadCards(ItemCard cards[]) {
        int counter = 0;
        for (int rowCounter = 0; rowCounter < (cards.length / 4) + 1; rowCounter++) {
            for (int columnCounter = 0; columnCounter < 4; columnCounter++) {
                if (counter >= cards.length) break;
                cardsContainer.setConstraints(cards[counter].getItemCard(), columnCounter, rowCounter);
                cardsContainer.getChildren().add(cards[counter].getItemCard());
                counter++;
            }
        }
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
