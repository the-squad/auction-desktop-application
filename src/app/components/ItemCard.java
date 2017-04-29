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
package app.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ItemCard extends Card {

    private Label itemName;
    private Label itemDescription;
    private Label itemCategory;

    private GridPane itemQuantityContainer;
    private Label itemQuantityHeadline;
    private Label itemQuantity;

    public ItemCard() {
        super();
        this.render();
    }

    private void render() {
        //Item name
        itemName = new Label("Moto 360");
        itemName.getStyleClass().add("item-name");

        //Item Description
        itemDescription = new Label("Smart watch from Motorolla powered by Android Wear");
        itemDescription.getStyleClass().add("item-description");
        itemDescription.setWrapText(true);
        itemDescription.setMaxWidth(250);
        itemDescription.setMaxHeight(45);

        //Item category
        itemCategory = new Label("Tech");
        itemCategory.getStyleClass().add("item-category");

        //Item quantity headline
        itemQuantityHeadline = new Label("Quantity:");
        itemQuantityHeadline.getStyleClass().add("item-quantity-headline");

        //Item quantity
        itemQuantity = new Label("10");
        itemQuantity.getStyleClass().add("item-quantity");

        //Item quantity container
        itemQuantityContainer = new GridPane();
        GridPane.setConstraints(itemQuantityHeadline, 0, 0);
        GridPane.setConstraints(itemQuantity, 1, 0);
        itemQuantityContainer.getChildren().addAll(itemQuantityHeadline, itemQuantity);

        //Card container
        cardDetails = new GridPane();
        cardDetails.setPadding(new Insets(10, 15, 10, 15));
        cardDetails.setVgap(3);

        GridPane.setConstraints(itemName, 0, 0);
        GridPane.setConstraints(itemDescription, 0, 1);
        GridPane.setConstraints(itemCategory, 0, 2);
        GridPane.setConstraints(itemQuantityContainer, 0, 3);

        cardDetails.getChildren().addAll(itemName, itemDescription, itemCategory, itemQuantityContainer);
        cardContainer.setBottom(cardDetails);
    }

    public BorderPane getItemCard() {
        return cardContainer;
    }

    public void setDetails() {
        //TODO use the item object to fill in the data
    }
}
