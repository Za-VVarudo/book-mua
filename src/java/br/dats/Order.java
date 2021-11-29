/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dats;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Death
 */
public class Order implements Serializable{
    private String orderID, userID, address, phone, purchaseDate;
    private int total;
    private boolean paymentStatus;
    private ArrayList<OrderDetail> detail;
    public Order() {
    }

    public Order(String orderID, String userID, String address, String phone, String purchaseDate, int total, boolean paymentStatus) {
        this.orderID = orderID;
        this.userID = userID;
        this.address = address;
        this.phone = phone;
        this.purchaseDate = purchaseDate;
        this.total = total;
        this.paymentStatus = paymentStatus;
        this.detail = new ArrayList<>();
    }
    
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<OrderDetail> getDetail() {
        return detail;
    }

    public void setDetail(ArrayList<OrderDetail> detail) {
        this.detail = detail;
    }
 
    
}
