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

package models;

import java.util.ArrayList;

public class Buyer extends User implements IAuctionInterface {

    public void reportSeller(int sellerId, String complaint) {
        // TODO
    }

    public void reportAuction(int auctionId, String complaint) {
        // TODO
    }

    public ArrayList<Item> getItems() {
        return null;
        // TODO
    }

    public boolean makeBid(Auction auction , double money) {
        return auction.bidAuction(money, this.getId());
    }

    public boolean followSeller(User seller) {
        return new SubscribeSeller(this.getId(),seller.getId()).create();
    }

    public void unFollowSeller() {
        // TODO
    }

    public void subscribeAuction() {
        // TODO
    }

    public ArrayList<Auction> getFeed() {
        return null;
        // TODO
    }

    public ArrayList<Auction> explore(Category category) {
        // TODO
        return null;
    }

    public ArrayList<Auction> search(double price, int status, int numberOfBidders) {
        // TODO
        return null;
    }

    @Override
    public ArrayList<Auction> search(String query) {
        // TODO
        return null;
    }

    @Override
    public ArrayList<Auction> getAuction() {
        return null;
        // TODO
    }

}
