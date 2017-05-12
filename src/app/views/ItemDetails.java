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

package app.views;

import app.Navigator;
import app.components.DropdownField;
import app.components.InputField;
import app.components.ParagraphField;
import app.components.PhotosViewer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import models.Category;
import models.Item;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static app.Partials.*;

public class ItemDetails {

    private static ItemDetails instance;

    private BorderPane itemDetailsContainer;
    private GridPane parentContainer;
    private GridPane itemDetailsForm;
    private InputField itemNameField;
    private ParagraphField itemDescription;
    private DropdownField itemCategoryField;
    private InputField itemQuantityField;
    private PhotosViewer photosViewer;
    private Button createItem;

    private Item newItem;

    private ItemDetails() {
        this.render();
    }

    private void render() {
        //Form fields
        itemNameField = new InputField("Item Name", TEXT);
        itemDescription = new ParagraphField("Item Description");
        itemCategoryField = new DropdownField("Item Category", new ArrayList<String>(Category.getCategories().stream().map(Category::getName).collect(Collectors.toList())));
        itemQuantityField = new InputField("Item Quantity", NUMBER);

        createItem = new Button("Create Item");
        createItem.getStyleClass().add("btn-primary");
        createItem.setTranslateX(275);

        createItem.setOnAction(e -> {
            if (photosViewer.getUploadedImages().size() == 0)
                photosViewer.markAsDanger();
            else {
                for (Node inputField : itemDetailsForm.getChildren()) {
                    if (inputField.getStyleClass().contains("input-field--danger"))
                        return;
                }

                newItem = currentSeller.addItemToInventory(currentSeller.getInventory(),
                                                            itemNameField.getValue(),
                                                            Integer.parseInt(itemQuantityField.getValue()),
                                                            itemCategoryField.getValue(),
                                                            itemDescription.getValue());

                newItem.setItemPhotos(photosViewer.getUploadedImages());
                Navigator.hidePage();
            }
        });

        //Item photosViewer
        photosViewer = new PhotosViewer(EDIT_MODE);

        //Item form container
        itemDetailsForm = new GridPane();
        itemDetailsForm.setVgap(5);

        GridPane.setConstraints(itemNameField.getInputField(), 0 , 0);
        GridPane.setConstraints(itemDescription.getParagraphField(), 0 , 1);
        GridPane.setConstraints(itemCategoryField.getDropdownField(), 0, 2);
        GridPane.setConstraints(itemQuantityField.getInputField(), 0, 3);

        itemDetailsForm.getChildren().addAll(itemNameField.getInputField(),
                                             itemDescription.getParagraphField(),
                                             itemCategoryField.getDropdownField(),
                                             itemQuantityField.getInputField());

        //Parent container
        parentContainer = new GridPane();
        parentContainer.getStyleClass().add("card");
        parentContainer.setHgap(40);
        parentContainer.setPadding(new Insets(TOP_DOWN, RIGHT_LEFT , TOP_DOWN, RIGHT_LEFT));
        parentContainer.setMaxWidth(CARD_WIDTH + 200);
        parentContainer.setAlignment(Pos.CENTER);

        GridPane.setConstraints(itemDetailsForm, 0, 0);
        GridPane.setConstraints(photosViewer.getPhotos(), 1, 0);
        GridPane.setConstraints(createItem, 0, 1);

        parentContainer.getChildren().addAll(itemDetailsForm, photosViewer.getPhotos(), createItem);

        //Item details container
        itemDetailsContainer = new BorderPane();
        itemDetailsContainer.setPadding(new Insets(20));
        itemDetailsContainer.setCenter(parentContainer);
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
