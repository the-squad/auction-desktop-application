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

public class SubscribeAuction extends Model<SubscribeAuction>{

    private int _id;
    private int _auctionID;
    private int _subscriberID;

    protected SubscribeAuction() {
    }

    public SubscribeAuction(int auctionID, int subscriberID) {
        this._auctionID = auctionID;
        this._subscriberID = subscriberID;
    }

    public Integer getId() {
        return _id;
    }

    public int getAuctionID() {
        return _auctionID;
    }

    public void setAuctionID(int auctionID) {
        this._auctionID = auctionID;
    }

    public int getSubscriberID() {
        return _subscriberID;
    }

    public void setSubscriberID(int subscriberID) {
        this._subscriberID = subscriberID;
    }
}
