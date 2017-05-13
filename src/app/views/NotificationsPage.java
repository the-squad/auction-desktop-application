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
import app.layouts.ScrollView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.Auction;
import models.Notification;

import static app.Partials.*;

import java.util.ArrayList;

public class NotificationsPage {

    private static NotificationsPage instance;

    private ScrollView notificationsPageContainer;
    private BorderPane notificationsCardContainer;
    private GridPane notificationsContainer;
    private Label headline;
    private ArrayList<Label> notificationsLabels = new ArrayList<>();
    private ArrayList<Rectangle> dividers = new ArrayList<>();

    private NotificationsPage() {
        this.render();
    }

    private void render() {
        //Headline
        headline = new Label("Notifications Center");
        headline.getStyleClass().add("headline");
        headline.setMinWidth(CARD_WIDTH);
        headline.setAlignment(Pos.CENTER);

        //Notifications container
        notificationsContainer = new GridPane();
        notificationsContainer.getStyleClass().add("card");
        notificationsContainer.setPadding(new Insets(TOP_DOWN, RIGHT_LEFT, TOP_DOWN, RIGHT_LEFT));
        notificationsContainer.setMaxWidth(CARD_WIDTH);
        notificationsContainer.setTranslateY(20);
        notificationsContainer.setStyle("-fx-max-height: 150px");

        GridPane.setConstraints(headline, 0, 0);
        GridPane.setMargin(headline, new Insets(0, 0, 20, 0));
        notificationsContainer.getChildren().add(headline);

        //Notifications card container
        notificationsCardContainer = new BorderPane();
        notificationsCardContainer.setPadding(new Insets(0, 0, 20, 0));
        notificationsCardContainer.setCenter(notificationsContainer);
        BorderPane.setAlignment(notificationsContainer, Pos.TOP_CENTER);

        notificationsPageContainer = new ScrollView(notificationsCardContainer);
    }

    public void fillNotifications(ArrayList<Notification> notifications) {
        notificationsContainer.getChildren().clear();
        notificationsContainer.getChildren().add(headline);

        int counter = 1;
        for (Notification notification : notifications) {
            Label notificationLabel = new Label(notification.getNotification());
            notificationLabel.getStyleClass().add("notification");
            notificationLabel.setMinWidth(CARD_WIDTH);

            Rectangle divider = new Rectangle();
            divider.setHeight(1);
            divider.setWidth(CARD_WIDTH);
            divider.setFill(Color.rgb(144,146,165, 0.5));

            notificationLabel.setOnMouseClicked(e -> {
                if (notification.getAuctionId() != 0) {
                    AuctionView.getInstance().fillAuctionData(Auction.getAuction(notification.getAuctionId()));
                    Navigator.viewPage(AUCTION_VIEW, Auction.getAuction(notification.getAuctionId()).getItem().getName());
                } else {
                    AuctionView.getInstance().fillAuctionData(null);
                    Navigator.viewPage(AUCTION_VIEW, "Finished Auction");
                }

            });

            notificationsLabels.add(notificationLabel);
            dividers.add(divider);
            GridPane.setConstraints(notificationLabel, 0, counter);
            notificationsContainer.getChildren().add(notificationLabel);
            if (counter <= notifications.size()) {
                GridPane.setConstraints(divider, 0, counter + 1);
                notificationsContainer.getChildren().add(divider);
            }

            counter+=2;
        }
    }

    public ScrollPane getNotificationsPage() {
        return notificationsPageContainer.getScrollView();
    }

    public static NotificationsPage getInstance() {
        if (instance == null) {
            instance = new NotificationsPage();
        }
        return instance;
    }
}
