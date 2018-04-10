package com.web.model;

import java.io.Serializable;

public class Order implements  Serializable {  
    
    private static final long serialVersionUID = 1853355651832301663L;  
  
    private String code;  
    private String serviceType;  
    private int quantity;  
    private String offer;  
    private String billType;  
    private String orderDate;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getOffer() {
		return offer;
	}
	public void setOffer(String offer) {
		this.offer = offer;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	} 
    
}
