/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dats;

import java.io.Serializable;

/**
 *
 * @author Death
 */
public class Product implements Serializable{
    private String productID, productName, imageURL, author, publisher, categoryID, categoryName;
    private int quantity, currQuantity, price;
    private int total;
    private boolean status;

    public Product() {
        
    }

    public Product(String productID, String productName, String imageURL, String author, String publisher, String categoryID, String categoryName, int quantity, int currQuantity, int price, boolean status) {
        this.productID = productID;
        this.productName = productName;
        this.imageURL = imageURL;
        this.author = author;
        this.publisher = publisher;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.quantity = quantity;
        this.currQuantity = currQuantity;
        this.price = price;
        this.status = status;
        this.total = this.quantity*this.price;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        setTotal();
    }

    public int getCurrQuantity() {
        return currQuantity;
    }

    public void setCurrQuantity(int currQuantity) {
        this.currQuantity = currQuantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotal() {
        return quantity*price;
    }

    public void setTotal() {
        this.total = quantity*price;
    }   

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    
}
