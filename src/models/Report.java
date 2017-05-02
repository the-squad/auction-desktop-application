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

public class Report {
    private Auction auction;
    private Seller  Seller ;
    private String compliant; 
    private Report(){}

    public Report(Auction auction, Seller Seller, String compliant) {
        this.auction = auction;
        this.Seller = Seller;
        this.compliant = compliant;
    }

    public Auction getAuction() {
        return auction;
    }

    public Seller getSeller() {
        return Seller;
    }

    public String getCompliant() {
        return compliant;
    }
    
    public ArrayList<Report> getAllReport(){   
        //Delete
        return null;
        
    }
    public void deleteReport(){
        // TODO
    }
    public void acceptReport(){
        // TODO
    }
}
