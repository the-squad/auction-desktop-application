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

public class SellerReport extends Model<SellerReport>{

    private int _id;
    private int _userID;
    private int _sellerID;
    private String _message;

    protected SellerReport() {
    }

    public SellerReport(int userID, int sellerID, String message) {
        this._userID = userID;
        this._sellerID = sellerID;
        this._message = message;
    }

    public int getId() {
        return _id;
    }

    public int getUserID() {
        return _userID;
    }

    public SellerReport setUserID(int userID) {
        this._userID = userID;
        return this;
    }

    public int getSellerID() {
        return _sellerID;
    }

    public SellerReport setSellerID(int sellerID) {
        this._sellerID = sellerID;
        return this;
    }

    public String getMessage() {
        return _message;
    }

    public SellerReport setMessage(String message) {
        this._message = message;
        return this;
    }
}
