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
import app.components.EmptyState;
import app.components.Header;
import app.components.InputField;
import app.tabs.AuctionsTab;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import models.Auction;
import models.Item;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static app.Partials.*;

public class AuctionDetails {

    private static AuctionDetails instance;
    private Auction auction;

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
    private Button controlAuction;
    private Button deleteAuction;

    private ArrayList<Item> items;
    private EmptyState emptyState;

    private static Thread loadItemsThread;
    private static Thread loadAuctionThread;

    private AuctionDetails() {
        this.render();
    }

    private void render() {
        //Form fields
        auctionItemField = new DropdownField("Choose an item");
        itemQuantityField = new InputField("Quantity", INTEGER_NUMBER);
        startingPriceField = new InputField("Starting price", DECIMAL_NUMBER);
        biddingRangeField = new InputField("Bidding Range", DECIMAL_NUMBER);
        startingDateField = new InputField("Starting day", DATE);
        startingTimeField = new InputField("Starting hour", TIME);
        endingDateField = new InputField("Ending day", DATE);
        endingTimeField = new InputField("Ending hour", TIME);

        controlAuction = new Button("Create Auction");
        controlAuction.getStyleClass().add("btn-primary");
        controlAuction.setTranslateX(250);

        controlAuction.setOnAction(e -> {
            if (auctionItemField.getValue().length() == 0 || itemQuantityField.getValue().length() == 0 ||
                    startingPriceField.getValue().length() == 0 || startingDateField.getValue().length() == 0 ||
                    startingTimeField.getValue().length() == 0 || endingDateField.getValue().length() == 0 ||
                    endingTimeField.getValue().length() == 0 || biddingRangeField.getValue().length() == 0)
                return;
            
            if (!Validation.validateAuctionTime(startingDateField.getValue(), startingTimeField.getValue(),
                    endingDateField.getValue(), endingTimeField.getValue())) {
                endingDateField.markAsDanger("Ending date must be after starting date");
                endingTimeField.markAsDanger("Ending time must be after starting time");
            } else if (controlAuction.getText().contains("Create")) {
                if (items.get(auctionItemField.getSelectedItemIndex()).getQuantity() < Integer.parseInt(itemQuantityField.getValue()))
                    itemQuantityField.markAsDanger("You only have " + items.get(auctionItemField.getSelectedItemIndex()).getQuantity());
            }

            for (Node inputField : auctionFormContainer.getChildren()) {
                if (inputField.getStyleClass().contains("input-field--danger"))
                    return;
            }

            if (controlAuction.getText().contains("Create")) {
                currentSeller.createAuction(items.get(auctionItemField.getSelectedItemIndex()),
                        Integer.parseInt(itemQuantityField.getValue()),
                        startingDateField.getValue(),
                        startingTimeField.getValue(),
                        endingDateField.getValue(),
                        endingTimeField.getValue(),
                        Double.parseDouble(startingPriceField.getValue()),
                        Double.parseDouble(biddingRangeField.getValue()));
            } else{
                if (!Auction.checkAuctionStatus(auction.getId())) {
                    controlAuction.setText("Auction has started");
                    deleteAuction.setDisable(true);
                    controlAuction.setDisable(true);
                } else {
                    currentSeller.updateAuction(auction.getId(),
                            startingDateField.getValue(),
                            startingTimeField.getValue(),
                            endingDateField.getValue(),
                            endingTimeField.getValue(),
                            Double.parseDouble(startingPriceField.getValue()),
                            Double.parseDouble(biddingRangeField.getValue()));
                }
            }

            AuctionsTab.getInstance().loadCards(currentSeller.getAuctions());
            Navigator.hidePage();
            Header.getInstance().switchTab(AUCTIONS_TAB);
        });

        //Delete auction
        deleteAuction = new Button("Delete");
        deleteAuction.getStyleClass().addAll("btn-primary", "danger-btn");
        deleteAuction.setTranslateX(-10);

        deleteAuction.setOnAction(e  -> {
            if (!Auction.checkAuctionStatus(auction.getId())) {
                deleteAuction.setText("Auction has started");
                deleteAuction.setDisable(true);
                controlAuction.setDisable(true);
            } else {
                currentSeller.deleteAuction(auction.getId());
                AuctionsTab.getInstance().loadCards(currentSeller.getAuctions());
                Navigator.hidePage();
                Header.getInstance().switchTab(AUCTIONS_TAB);
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
        GridPane.setConstraints(controlAuction, 0 ,4);
        GridPane.setConstraints(deleteAuction, 1, 4);

        auctionFormContainer.getChildren().addAll(auctionItemField.getDropdownField(),
                                                  itemQuantityField.getInputField(),
                                                  startingPriceField.getInputField(),
                                                  biddingRangeField.getInputField(),
                                                  startingDateField.getInputField(),
                                                  startingTimeField.getInputField(),
                                                  endingDateField.getInputField(),
                                                  endingTimeField.getInputField(),
                controlAuction,
                deleteAuction);

        //Auction details container
        auctionDetailsContainer = new BorderPane();
        auctionDetailsContainer.setPadding(new Insets(20));
        auctionDetailsContainer.setCenter(auctionFormContainer);

        //Empty state
        emptyState = new EmptyState();
        emptyState.setEmptyMessage("This Auction has been deleted or terminated");
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

    public void clearAuctionData() {
        auctionItemField.clear();
        itemQuantityField.clear();
        startingPriceField.clear();
        biddingRangeField.clear();
        startingDateField.clear();
        startingTimeField.clear();
        endingDateField.clear();
        endingTimeField.clear();
        controlAuction.setTranslateX(250);
        controlAuction.setText("Create Auction");
        auctionFormContainer.getChildren().remove(deleteAuction);
    }

    public void fillAuctionData(Auction auction) {
        clearAuctionData();
        controlAuction.setTranslateX(210);
        controlAuction.setText("Update");
        auctionFormContainer.getChildren().add(deleteAuction);
        this.auction = auction;

        Task<String> loadData = new Task<String>() {
            String name;
            double quantity;
            double price;
            double biddingRate;
            String startingDate;
            String startingTime;
            String endingDate;
            String endingTime;

            @Override
            protected String call() throws Exception {
                auctionItemField.disable(true);

                name = auction.getItem().getName();
                quantity = auction.getItemQuantity();
                price = auction.getInitialPrice();
                biddingRate = auction.getBiddingRate();
                startingDate = String.valueOf(Validation.convertDateToString(auction.getStartDate()).get(0));
                startingTime = String.valueOf(Validation.convertDateToString(auction.getStartDate()).get(1));
                endingDate = String.valueOf(Validation.convertDateToString(auction.getTerminationDate()).get(0));
                endingTime = String.valueOf(Validation.convertDateToString(auction.getTerminationDate()).get(1));
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                auctionItemField.setValue(name);
                itemQuantityField.setValue(String.valueOf(quantity));
                startingPriceField.setValue(String.valueOf(price));
                biddingRangeField.setValue(String.valueOf(biddingRate));
                startingDateField.setValue(startingDate);
                startingTimeField.setValue(startingTime);
                endingDateField.setValue(endingDate);
                endingTimeField.setValue(endingTime);
            }
        };

        if (loadAuctionThread == null || !loadAuctionThread.isAlive()) {
            loadAuctionThread = new Thread(loadData);
            loadAuctionThread.start();
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
