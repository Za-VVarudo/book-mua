/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dats;

import java.io.Serializable;
import java.util.Hashtable;

/**
 *
 * @author Death
 */
public class ShoppingCart extends Hashtable<String, Product> implements Serializable{
    private String verification;
    public ShoppingCart() {
        super();
    }
    
    public boolean addItem(Product item) {
        boolean valid = false;
        try {         
            if (item!=null) {
                String productID = item.getProductID();
                int addQuantity = item.getQuantity();
                if (this.containsKey(productID)) {
                    int oldQuantity = this.get(productID).getQuantity();
                    if (oldQuantity + addQuantity <= item.getCurrQuantity()) {
                        item.setQuantity(addQuantity + oldQuantity);
                        this.put(productID, item);
                        valid = true;
                    }
                } else {
                    this.put(productID, item);
                    valid = true;                   
                }
            }  
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valid;
    }
    
    public void removeItem (String productID) {
        this.remove(productID);
    }
    
    public int getTotal () {
        if (this.isEmpty()) return 0;
        int total=0;
        java.util.Iterator<String> ite = this.keySet().iterator();
        while (ite.hasNext()) {
            String productID = ite.next();
            total = total + this.get(productID).getTotal();
        }
        return total;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }
    
}
