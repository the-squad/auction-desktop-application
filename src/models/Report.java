/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;

/**
 *
 * @author mohamedkamal
 */
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
