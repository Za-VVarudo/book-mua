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
public class UserError {
    private String userID_E;
    private String password_E;
    private String confirmPassword_E;
    private String fullName_E;
    private String email_E;
    private String address_E;
    private String phone_E;
    private String gender_E;
    private String roleID_E;
    private String message_E;

    public UserError(String userID_E, String password_E, String confirmPassword_E, String fullName_E, String email_E, String address_E, String phone_E, String gender_E, String roleID_E, String message_E) {
        this.userID_E = userID_E;
        this.password_E = password_E;
        this.confirmPassword_E = confirmPassword_E;
        this.fullName_E = fullName_E;
        this.email_E = email_E;
        this.address_E = address_E;
        this.phone_E = phone_E;
        this.gender_E = gender_E;
        this.roleID_E = roleID_E;
        this.message_E = message_E;
    }

    public UserError() {
        this.userID_E = "";
        this.password_E = "";
        this.confirmPassword_E = "";
        this.fullName_E = "";
        this.email_E = "";
        this.address_E = "";
        this.phone_E = "";
        this.gender_E = "";
        this.roleID_E = "";
        this.message_E = "";
    }

    public String getUserID_E() {
        return userID_E;
    }

    public void setUserID_E(String userID_E) {
        this.userID_E = userID_E;
    }

    public String getPassword_E() {
        return password_E;
    }

    public void setPassword_E(String password_E) {
        this.password_E = password_E;
    }

    public String getConfirmPassword_E() {
        return confirmPassword_E;
    }

    public void setConfirmPassword_E(String confirmPassword_E) {
        this.confirmPassword_E = confirmPassword_E;
    }

    public String getFullName_E() {
        return fullName_E;
    }

    public void setFullName_E(String fullName_E) {
        this.fullName_E = fullName_E;
    }

    public String getRoleID_E() {
        return roleID_E;
    }

    public void setRoleID_E(String roleID_E) {
        this.roleID_E = roleID_E;
    }

    public String getMessage_E() {
        return message_E;
    }

    public void setMessage_E(String message_E) {
        this.message_E = message_E;
    }

    public String getEmail_E() {
        return email_E;
    }

    public void setEmail_E(String email_E) {
        this.email_E = email_E;
    }

    public String getAddress_E() {
        return address_E;
    }

    public void setAddress_E(String address_E) {
        this.address_E = address_E;
    }

    public String getPhone_E() {
        return phone_E;
    }

    public void setPhone_E(String phone_E) {
        this.phone_E = phone_E;
    }

    public String getGender_E() {
        return gender_E;
    }

    public void setGender_E(String gender_E) {
        this.gender_E = gender_E;
    }
     
    
}
