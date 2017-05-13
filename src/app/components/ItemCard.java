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

package app.components;

import app.Navigator;
import app.views.ItemDetails;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import models.Category;
import models.ImageUtils;
import models.Item;
import models.Model;
import static app.Partials.*;

public class ItemCard extends Card {

    private final Item item;

    private Label itemName;
    private Label itemDescription;
    private Label itemCategory;

    private GridPane itemQuantityContainer;
    private Label itemQuantityHeadline;
    private Label itemQuantity;

    public ItemCard(Item item) {
        super();
        this.item = item;
        this.render();
    }

    private void render() {
        //Item photo
        photo = ImageUtils.cropAndConvertImage(item.getItemPhotos().get(0).getImage(), 250, 175);
        photoViewer.setFill(new ImagePattern(photo));

        //Item name
        itemName = new Label(item.getName());
        itemName.getStyleClass().add("item-name");

        itemName.setOnMouseClicked(e -> {
            ItemDetails.getInstance().fillData(item);
            Navigator.viewPage(ITEM_DETAILS, itemName.getText());
        });

        //Item Description
        itemDescription = new Label(item.getDescription());
        itemDescription.getStyleClass().add("item-description");
        itemDescription.setWrapText(true);
        itemDescription.setAlignment(Pos.TOP_LEFT);
        itemDescription.setMaxWidth(250);
        itemDescription.setMinHeight(45);
        itemDescription.setMaxHeight(45);

        //Item category
        itemCategory = new Label(Model.find(Category.class, item.getCategoryID()).getName());
        itemCategory.getStyleClass().add("item-category");

        //Item quantity headline
        itemQuantityHeadline = new Label("Quantity:");
        itemQuantityHeadline.getStyleClass().add("item-quantity-headline");

        //Item quantity
        itemQuantity = new Label(String.valueOf(item.getQuantity()));
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

    public BorderPane getCard() {
        return cardContainer;
    }
}
