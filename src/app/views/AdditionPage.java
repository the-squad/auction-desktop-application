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
import app.components.AuctionCard;
import app.components.InputField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.Objects;

import static app.Partials.*;

public class AdditionPage {

    private static AdditionPage instance;
    private static int ITEM = 0;
    private static int AUCTION = 1;
    private static int WIDTH = 230;
    private static int HEIGHT = 180;

    private BorderPane additionPageContainer;

    private GridPane selectorsContainer;
    private GridPane itemSelectorContainer;
    private Label itemSelectorHeadline;
    private Label itemSelectorDescription;

    private GridPane auctionSelectorContainer;
    private Label auctionSelectorHeadline;
    private Label auctionSelectorDescription;

    private Button selectButton;
    private InputField hiddenField;

    private AdditionPage() {
        this.render();
    }

    private void render() {
        //Selectors
        itemSelectorHeadline = new Label("Create new item");
        itemSelectorHeadline.setMinWidth(WIDTH);
        itemSelectorHeadline.setAlignment(Pos.CENTER);
        itemSelectorHeadline.getStyleClass().add("select-headline");

        itemSelectorDescription = new Label("Add a new item to the inventory so you can create" +
                " an auction and sell it whenever you want");
        itemSelectorDescription.getStyleClass().add("select-description");
        itemSelectorDescription.setMinWidth(WIDTH - 20);
        itemSelectorDescription.setMaxWidth(WIDTH - 20);
        itemSelectorDescription.setTranslateX(10);
        itemSelectorDescription.setWrapText(true);
        itemSelectorDescription.setStyle("-fx-text-alignment: center");

        itemSelectorContainer = new GridPane();
        itemSelectorContainer.setPadding(new Insets(20));
        itemSelectorContainer.setMinWidth(WIDTH);
        itemSelectorContainer.setMinHeight(HEIGHT);
        itemSelectorContainer.setVgap(10);
        itemSelectorContainer.setAlignment(Pos.CENTER);
        itemSelectorContainer.getStyleClass().add("select-container");

        GridPane.setConstraints(itemSelectorHeadline, 0, 0);
        GridPane.setConstraints(itemSelectorDescription, 0, 1);

        itemSelectorContainer.getChildren().addAll(itemSelectorHeadline, itemSelectorDescription);
        itemSelectorContainer.setOnMouseClicked(e -> this.select(ITEM));

        auctionSelectorHeadline = new Label("Create new auction");
        auctionSelectorHeadline.getStyleClass().add("select-headline");
        auctionSelectorHeadline.setMinWidth(WIDTH);
        auctionSelectorHeadline.setAlignment(Pos.CENTER);

        auctionSelectorDescription = new Label("Choose an item and set minimum price, starting date" +
                " ,ending date and bidding minimum rate");
        auctionSelectorDescription.getStyleClass().add("select-description");
        auctionSelectorDescription.setMinWidth(WIDTH - 20);
        auctionSelectorDescription.setMaxWidth(WIDTH - 20);
        auctionSelectorDescription.setTranslateX(10);
        auctionSelectorDescription.setWrapText(true);
        auctionSelectorDescription.setStyle("-fx-text-alignment: center");

        auctionSelectorContainer = new GridPane();
        auctionSelectorContainer.setPadding(new Insets(20));
        auctionSelectorContainer.setMinWidth(WIDTH);
        auctionSelectorContainer.setMinHeight(HEIGHT);
        auctionSelectorContainer.setVgap(10);
        auctionSelectorContainer.setAlignment(Pos.CENTER);
        auctionSelectorContainer.getStyleClass().add("select-container");

        GridPane.setConstraints(auctionSelectorHeadline, 0, 0);
        GridPane.setConstraints(auctionSelectorDescription, 0, 1);

        auctionSelectorContainer.getChildren().addAll(auctionSelectorHeadline, auctionSelectorDescription);
        auctionSelectorContainer.setOnMouseClicked(e -> this.select(AUCTION));

        //Selectors container
        selectorsContainer = new GridPane();
        selectorsContainer.setHgap(20);
        selectorsContainer.setVgap(30);
        selectorsContainer.getStyleClass().add("card");
        selectorsContainer.setAlignment(Pos.CENTER);
        selectorsContainer.setMaxWidth(530);
        selectorsContainer.setStyle("-fx-max-height: 100px");
        selectorsContainer.setPadding(new Insets(50));

        GridPane.setConstraints(itemSelectorContainer, 0, 0);
        GridPane.setConstraints(auctionSelectorContainer, 1, 0);

        selectorsContainer.getChildren().addAll(itemSelectorContainer, auctionSelectorContainer);

        //Select button
        selectButton = new Button("Create");
        selectButton.getStyleClass().add("btn-primary");
        selectButton.setTranslateX(187);

        selectButton.setOnAction(e -> {
            if (Objects.equals(hiddenField.getValue(), "Item")) {
                Navigator.viewPage(ITEM_DETAILS, "Create new item");
            } else {
                Navigator.viewPage(AUCTION_DETAILS, "Create new auction");
                AuctionDetails.getInstance().fillSellerItems();
            }
        });

        GridPane.setConstraints(selectButton, 0, 1);
        selectorsContainer.getChildren().add(selectButton);

        //Addition page container
        additionPageContainer = new BorderPane();
        additionPageContainer.setPadding(new Insets(50, 0, 0, 0));
        additionPageContainer.setTop(selectorsContainer);
        BorderPane.setAlignment(selectorsContainer, Pos.BOTTOM_CENTER);

        //Hidden field
        hiddenField = new InputField("", TEXT);
    }

    private void select(int selector) {
        if (selector == ITEM) {
            if (itemSelectorContainer.getStyleClass().contains("select-container--active")) return;

            auctionSelectorContainer.getStyleClass().remove("select-container--active");
            itemSelectorContainer.getStyleClass().add("select-container--active");
            hiddenField.setValue("Item");
        } else {
            if (auctionSelectorContainer.getStyleClass().contains("select-container--active")) return;

            itemSelectorContainer.getStyleClass().remove("select-container--active");
            auctionSelectorContainer.getStyleClass().add("select-container--active");
            hiddenField.setValue("Auction");
        }
    }

    public BorderPane getAdditionPage() {
        return additionPageContainer;
    }

    public static AdditionPage getInstance() {
        if (instance == null) {
            instance = new AdditionPage();
        }
        return instance;
    }
}
