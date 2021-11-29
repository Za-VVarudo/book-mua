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
public class OrderDetail implements Serializable{
    private String orderID, productID;
    private int quantity, price, total;

    public OrderDetail() {
    }

    public OrderDetail(String orderID, String productID, int quantity, int price) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.total = this.price*this.quantity;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotal() {
        return price * quantity;
    }

    public void setTotal() {
        this.total = price * quantity;
    }
    
    
}
