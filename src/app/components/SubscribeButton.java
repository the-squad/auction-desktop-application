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

import javafx.scene.control.Button;

import static app.Partials.currentBuyer;

public class SubscribeButton {

    private int auctionId;
    private Button subscribeButton;
    private Boolean userSubscribed = false;

    public SubscribeButton(int auctionId) {
        this.auctionId = auctionId;
        this.render();
    }

    private void render() {
        subscribeButton = new Button();
        subscribeButton.getStyleClass().add("subscribe-btn");

        subscribeButton.setOnAction(e -> {
            if (userSubscribed) {
                subscribeButton.getStyleClass().remove("subscribe-btn--active");
                currentBuyer.unSubscribeFromAuction(auctionId);
                userSubscribed = false;
            } else {
                subscribeButton.getStyleClass().add("subscribe-btn--active");
                currentBuyer.subscribeToAuction(auctionId);
                userSubscribed = true;
            }
        });
    }

    public void markAsSubscribed() {
        userSubscribed = true;
        subscribeButton.getStyleClass().add("subscribe-btn--active");
    }

    public Button getSubscribeButton() {
        return subscribeButton;
    }
}
