/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dats;

/**
 *
 * @author Death
 */
public class ProductError {
    private String productID_E, productName_E, imageURL_E, author_E, publisher_E, categoryID_E;
    private String quantity_E, currQuantity_E, price_E;
    private String status_E;
    private String message_E;

    public ProductError() {
        this.productID_E = "";
        this.productName_E = "";
        this.imageURL_E = "";
        this.author_E = "";
        this.publisher_E = "";
        this.categoryID_E = "";
        this.quantity_E = "";
        this.currQuantity_E = "";
        this.price_E = "";
        this.status_E = "";
        this.message_E = "";
    }
   
    public ProductError(String productID_E, String productName_E, String imageURL_E, String author_E, String publisher_E, String categoryID_E, String quantity_E, String currQuantity_E, String price_E, String status_E, String message_E) {
        this.productID_E = productID_E;
        this.productName_E = productName_E;
        this.imageURL_E = imageURL_E;
        this.author_E = author_E;
        this.publisher_E = publisher_E;
        this.categoryID_E = categoryID_E;
        this.quantity_E = quantity_E;
        this.currQuantity_E = currQuantity_E;
        this.price_E = price_E;
        this.status_E = status_E;
        this.message_E = message_E;
    }

    public String getProductID_E() {
        return productID_E;
    }

    public void setProductID_E(String productID_E) {
        this.productID_E = productID_E;
    }

    public String getProductName_E() {
        return productName_E;
    }

    public void setProductName_E(String productName_E) {
        this.productName_E = productName_E;
    }

    public String getImageURL_E() {
        return imageURL_E;
    }

    public void setImageURL_E(String imageURL_E) {
        this.imageURL_E = imageURL_E;
    }

    public String getAuthor_E() {
        return author_E;
    }

    public void setAuthor_E(String author_E) {
        this.author_E = author_E;
    }

    public String getPublisher_E() {
        return publisher_E;
    }

    public void setPublisher_E(String publisher_E) {
        this.publisher_E = publisher_E;
    }

    public String getCategoryID_E() {
        return categoryID_E;
    }

    public void setCategoryID_E(String categoryID_E) {
        this.categoryID_E = categoryID_E;
    }

    public String getQuantity_E() {
        return quantity_E;
    }

    public void setQuantity_E(String quantity_E) {
        this.quantity_E = quantity_E;
    }

    public String getCurrQuantity_E() {
        return currQuantity_E;
    }

    public void setCurrQuantity_E(String currQuantity_E) {
        this.currQuantity_E = currQuantity_E;
    }

    public String getPrice_E() {
        return price_E;
    }

    public void setPrice_E(String price_E) {
        this.price_E = price_E;
    }

    public String getStatus_E() {
        return status_E;
    }

    public void setStatus_E(String status_E) {
        this.status_E = status_E;
    }

    public String getMessage_E() {
        return message_E;
    }

    public void setMessage_E(String message_E) {
        this.message_E = message_E;
    }
    
}
