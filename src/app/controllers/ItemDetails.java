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
package app.controllers;

import app.Navigator;
import app.components.DropdownField;
import app.components.InputField;
import app.components.ParagraphField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import static app.Partials.*;

public class ItemDetails {

    private static ItemDetails instance;

    private BorderPane itemDetailsContainer;
    private GridPane itemDetailsForm;
    private InputField itemNameField;
    private ParagraphField itemDescription;
    private DropdownField itemCategoryField;
    private InputField itemQuantityField;
    private Button createItem;

    private ItemDetails() {
        this.render();
    }

    private void render() {
        //Form fields
        itemNameField = new InputField("Item Name", TEXT);
        itemDescription = new ParagraphField("Item Description");
        itemCategoryField = new DropdownField("Item Category", "Tech", "Cars");
        itemQuantityField = new InputField("Item Quantity", NUMBER);

        createItem = new Button("Create Item");
        createItem.getStyleClass().add("btn-primary");
        createItem.setTranslateX(85);

        createItem.setOnAction(e -> Navigator.hidePage());

        //Item form container
        itemDetailsForm = new GridPane();
        itemDetailsForm.getStyleClass().add("card");
        itemDetailsForm.setPadding(new Insets(35, 50 , 35, 50));
        itemDetailsForm.setVgap(5);
        itemDetailsForm.setHgap(50);
        itemDetailsForm.setMaxWidth(550);
        itemDetailsForm.setAlignment(Pos.CENTER);

        GridPane.setConstraints(itemNameField.getInputField(), 0 , 0);
        GridPane.setConstraints(itemDescription.getParagraphField(), 0 , 1);
        GridPane.setConstraints(itemCategoryField.getDropdownField(), 0, 2);
        GridPane.setConstraints(itemQuantityField.getInputField(), 0, 3);
        GridPane.setConstraints(createItem, 0 , 4);

        itemDetailsForm.getChildren().addAll(itemNameField.getInputField(),
                                             itemDescription.getParagraphField(),
                                             itemCategoryField.getDropdownField(),
                                             itemQuantityField.getInputField(),
                                             createItem);

        //Item details container
        itemDetailsContainer = new BorderPane();
        itemDetailsContainer.setPadding(new Insets(20));
        itemDetailsContainer.setCenter(itemDetailsForm);
    }

    public BorderPane getItemDetails() {
        return itemDetailsContainer;
    }

    public static ItemDetails getInstance() {
        if (instance == null) {
            instance = new ItemDetails();
        }
        return instance;
    }
}
