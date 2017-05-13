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
import app.tabs.InventoryTab;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import models.Category;
import models.Image;
import models.Item;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static app.Partials.*;
import models.Inventory;

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
    private Button deleteItem;

    private Item newItem;
    private static Thread loadingItemThread;
    private int itemId;

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
            for (Node inputField : itemDetailsForm.getChildren()) {
                    if (inputField.getStyleClass().contains("input-field--danger")) {
                        return;
                    }
                }
            Inventory sellerInventory = currentSeller.getInventory();
            sellerInventory.getItems();
            if (createItem.getText().contains("Create")) {
                if (photosViewer.getUploadedImages().size() == 0) {

                    photosViewer.markAsDanger();
                    return;
                }

                newItem = currentSeller.addItemToInventory(sellerInventory,
                        itemNameField.getValue(),
                        Integer.parseInt(itemQuantityField.getValue()),
                        itemCategoryField.getValue(),
                        itemDescription.getValue());
            } else {
                    newItem = currentSeller.updateItemInInventory(sellerInventory,
                            itemId,
                            itemNameField.getValue(),
                            Integer.parseInt(itemQuantityField.getValue()),
                            itemDescription.getValue());
                }

                newItem.setItemPhotos(photosViewer.getUploadedImages());
                InventoryTab.getInstance().loadCards(currentSeller.getItems(currentSeller.getInventory()));
                Navigator.hidePage();
            
        });

        //Delete button
        deleteItem = new Button("Delete");
        deleteItem.getStyleClass().addAll("btn-primary", "delete-btn");

        deleteItem.setOnAction(e -> {
            Inventory sellerInventory = currentSeller.getInventory();
            sellerInventory.getItems();
            currentSeller.deleteItemFromInventory(sellerInventory, itemId);
            InventoryTab.getInstance().loadCards(currentSeller.getItems(currentSeller.getInventory()));
            Navigator.hidePage();
        });

        //Item photosViewer
        photosViewer = new PhotosViewer(EDIT_MODE);

        //Item form container
        itemDetailsForm = new GridPane();
        itemDetailsForm.setVgap(5);

        GridPane.setConstraints(itemNameField.getInputField(), 0, 0);
        GridPane.setConstraints(itemDescription.getParagraphField(), 0, 1);
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
        parentContainer.setPadding(new Insets(TOP_DOWN, RIGHT_LEFT, TOP_DOWN, RIGHT_LEFT));
        parentContainer.setMaxWidth(CARD_WIDTH + 200);
        parentContainer.setAlignment(Pos.CENTER);

        GridPane.setConstraints(itemDetailsForm, 0, 0);
        GridPane.setConstraints(photosViewer.getPhotos(), 1, 0);
        GridPane.setConstraints(createItem, 0, 1);
        GridPane.setConstraints(deleteItem, 1, 1);

        parentContainer.getChildren().addAll(itemDetailsForm, photosViewer.getPhotos(), createItem);

        //Item details container
        itemDetailsContainer = new BorderPane();
        itemDetailsContainer.setPadding(new Insets(20));
        itemDetailsContainer.setCenter(parentContainer);
    }

    public void clearDetails() {
        itemNameField.clear();
        itemDescription.clear();
        itemCategoryField.clear();
        itemQuantityField.clear();
        photosViewer.resetPhotoView(EDIT_MODE);
        parentContainer.getChildren().remove(deleteItem);
        createItem.setTranslateX(275);
    }

    public void fillData(Item item) {
        clearDetails();

        Task<String> loadData = new Task<String>() {
            String name;
            String description;
            String category;
            String quantity;
            ArrayList<Image> itemImages;

            @Override
            protected String call() throws Exception {
                name = item.getName();
                description = item.getDescription();
                category = Category.getCategories().get(item.getCategoryID()).getName();
                quantity = String.valueOf(item.getQuantity());
                itemImages = item.getItemPhotos();
                itemId = item.getId();
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                itemNameField.setValue(name);
                itemDescription.setValue(description);
                itemCategoryField.setValue(category);
                itemCategoryField.disable();
                itemQuantityField.setValue(quantity);
                photosViewer.setPhotos(itemImages);
                parentContainer.getChildren().add(deleteItem);
                createItem.setTranslateX(185);
                createItem.setText("Update Item");
            }
        };

        if (loadingItemThread == null || !loadingItemThread.isAlive()) {
            loadingItemThread = new Thread(loadData);
            loadingItemThread.start();
        }
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
