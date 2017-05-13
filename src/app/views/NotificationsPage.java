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
import static app.Partials.*;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationsPage {

    private static NotificationsPage instance;

    private ScrollView notificationsPageContainer;
    private BorderPane notificationsCardContainer;
    private GridPane notificationsContainer;
    private Label headline;
    private HashMap<Label, Integer> notificationsMap = new HashMap<>();
    private ArrayList<Label> notifications = new ArrayList<>();
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

        GridPane.setConstraints(headline, 0, 0);
        notificationsContainer.getChildren().add(headline);

        //Notifications card container
        notificationsCardContainer = new BorderPane();
        notificationsCardContainer.setPadding(new Insets(0, 0, 20, 0));
        notificationsCardContainer.setCenter(notificationsContainer);

        notificationsPageContainer = new ScrollView(notificationsCardContainer);
        this.fillNotifications();
    }

    public void fillNotifications() {
        notificationsContainer.getChildren().clear();

        for (int counter = 1; counter < 20; counter+=2) {
            Label notification = new Label("Notification Testing");
            notification.getStyleClass().add("notification");
            notification.setMinWidth(CARD_WIDTH);

            Rectangle divider = new Rectangle();
            divider.setHeight(1);
            divider.setWidth(CARD_WIDTH);
            divider.setFill(Color.rgb(144,146,165, 0.5));

            //notificationsMap.put(notification, auctionId);

            notification.setOnMouseClicked(e -> {
                //AuctionView.getInstance().fillAuctionData();
                //Navigator.viewPage(AUCTION_VIEW, "");
            });

            notifications.add(notification);
            dividers.add(divider);
            GridPane.setConstraints(notification, 0, counter);
            GridPane.setMargin(headline, new Insets(0, 0, 20, 0));
            GridPane.setConstraints(divider, 0, counter + 1);
            notificationsContainer.getChildren().addAll(notification, divider);
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
