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
import app.Validation;
import app.components.DropdownField;
import app.components.InputField;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import models.Item;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static app.Partials.*;

public class AuctionDetails {

    private static AuctionDetails instance;

    private BorderPane auctionDetailsContainer;

    private GridPane auctionFormContainer;
    private DropdownField auctionItemField;
    private InputField itemQuantityField;
    private InputField startingPriceField;
    private InputField biddingRangeField;
    private InputField startingDateField;
    private InputField startingTimeField;
    private InputField endingDateField;
    private InputField endingTimeField;
    private Button createAuction;

    private ArrayList<Item> items;

    private static Thread loadItemsThread;

    private AuctionDetails() {
        this.render();
    }

    private void render() {
        //Form fields
        auctionItemField = new DropdownField("Choose an item");
        itemQuantityField = new InputField("Quantity", NUMBER);
        startingPriceField = new InputField("Starting price", NUMBER);
        biddingRangeField = new InputField("Bidding Range", NUMBER);
        startingDateField = new InputField("Starting day", DATE);
        startingTimeField = new InputField("Starting hour", TIME);
        endingDateField = new InputField("Ending day", DATE);
        endingTimeField = new InputField("Ending hour", TIME);

        createAuction = new Button("Create Auction");
        createAuction.getStyleClass().add("btn-primary");
        createAuction.setTranslateX(250);

        createAuction.setOnAction(e -> {
            for (Node inputField : auctionFormContainer.getChildren()) {
                if (inputField.getStyleClass().contains("input-field--danger"))
                    return;
            }

            if (!Validation.validateAuctionTime(startingDateField.getValue(), startingTimeField.getValue(),
                    endingDateField.getValue(), endingTimeField.getValue())) {
                endingDateField.markAsDanger("Ending date must be after starting date");
                endingTimeField.markAsDanger("Ending time must be after starting time");
            } else if (items.get(auctionItemField.getSelectedItemIndex()).getQuantity() < Integer.parseInt(itemQuantityField.getValue())) {
                itemQuantityField.markAsDanger("You only have " + items.get(auctionItemField.getSelectedItemIndex()).getQuantity());
            } else {
                currentSeller.createAuction(items.get(auctionItemField.getSelectedItemIndex()),
                        Integer.parseInt(itemQuantityField.getValue()),
                        startingDateField.getValue(),
                        startingTimeField.getValue(),
                        endingDateField.getValue(),
                        endingTimeField.getValue(),
                        Double.parseDouble(startingPriceField.getValue()),
                        Double.parseDouble(biddingRangeField.getValue()));
                Navigator.hidePage();
            }
        });

        //Auction form container
        auctionFormContainer = new GridPane();
        auctionFormContainer.getStyleClass().add("card");
        auctionFormContainer.setPadding(new Insets(TOP_DOWN, RIGHT_LEFT , TOP_DOWN, RIGHT_LEFT));
        auctionFormContainer.setVgap(5);
        auctionFormContainer.setHgap(50);
        auctionFormContainer.setMaxWidth(CARD_WIDTH);
        auctionFormContainer.setAlignment(Pos.CENTER);

        GridPane.setConstraints(auctionItemField.getDropdownField(), 0 ,0);
        GridPane.setConstraints(itemQuantityField.getInputField(), 1 ,0);
        GridPane.setConstraints(startingPriceField.getInputField(), 0 ,1);
        GridPane.setConstraints(biddingRangeField.getInputField(), 1 ,1);
        GridPane.setConstraints(startingDateField.getInputField(), 0 ,2);
        GridPane.setConstraints(startingTimeField.getInputField(), 1 ,2);
        GridPane.setConstraints(endingDateField.getInputField(), 0 ,3);
        GridPane.setConstraints(endingTimeField.getInputField(), 1 ,3);
        GridPane.setConstraints(createAuction, 0 ,4);

        auctionFormContainer.getChildren().addAll(auctionItemField.getDropdownField(),
                                                  itemQuantityField.getInputField(),
                                                  startingPriceField.getInputField(),
                                                  biddingRangeField.getInputField(),
                                                  startingDateField.getInputField(),
                                                  startingTimeField.getInputField(),
                                                  endingDateField.getInputField(),
                                                  endingTimeField.getInputField(),
                                                  createAuction);

        //Auction details container
        auctionDetailsContainer = new BorderPane();
        auctionDetailsContainer.setPadding(new Insets(20));
        auctionDetailsContainer.setCenter(auctionFormContainer);
    }

    public void fillSellerItems() {
        auctionItemField.getDropdownField().setDisable(true);
        Task<String> loadItems = new Task<String>() {
            ArrayList<String> dropdownItems;

            @Override
            protected String call() throws Exception {
                items = currentSeller.getInventory().getItems();
                dropdownItems = new ArrayList<>(items.stream().map(Item::getName).collect(Collectors.toList()));
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                auctionItemField.addItems(dropdownItems);
                auctionItemField.getDropdownField().setDisable(false);
            }
        };

        if (loadItemsThread == null || !loadItemsThread.isAlive()) {
            loadItemsThread = new Thread(loadItems);
            loadItemsThread.start();
        }
    }

    public BorderPane getAuctionDetails() {
        return auctionDetailsContainer;
    }

    public static AuctionDetails getInstance() {
        if (instance == null) {
            instance = new AuctionDetails();
        }
        return instance;
    }
    
}
