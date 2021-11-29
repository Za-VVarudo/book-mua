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
public class User implements Serializable{

    private String userID;
    private String fullName;
    private String password;
    private String email;
    private String address;
    private String phone;
    private char gender;
    private String roleID;
    private boolean status;

    public User() {
        this.userID = "";
        this.fullName = "";
        this.password = "";
        this.email = "";
        this.address = "";
        this.phone = "";
        this.gender = 'M';
        this.roleID = "US";
        this.status = true;
    }

    public User(String userID, String fullName, String password, String email, String address, String phone, char gender, String roleID, boolean status) {
        this.userID = userID;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.roleID = roleID;
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
