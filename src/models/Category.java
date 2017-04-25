/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;


/**
 *
 * @author Mohamed
 */
public class Category extends Model<Category>{

    private int _id;
    private String _name;

    protected Category() {
    }

    public Category(String name) {
        this._name = name;
    }

    public Integer getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }
    
    public ArrayList<Category> getCategories()
    {
        // TODO
        return null;
    }
}
