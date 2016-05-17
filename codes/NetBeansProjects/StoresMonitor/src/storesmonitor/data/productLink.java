/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storesmonitor.data;

/**
 *
 * @author Yi
 */
public class productLink {
	private String urlLink;
	private float retailPrice;
	private int setLimitPrice;
	private boolean inStock;
	private String title; 
	
	public productLink(String urlLink){
		this.urlLink = urlLink;
		inStock= false;
		title="";
	}
	
	
	public String getUrlLink() {
		return urlLink;
	}
	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}
	public float getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(float retailPrice) {
		this.retailPrice = retailPrice;
	}
	public int getSetLimitPrice() {
		return setLimitPrice;
	}
	public void setSetLimitPrice(int setLimitPrice) {
		this.setLimitPrice = setLimitPrice;
	}
	public boolean getInStock() {
		return inStock;
	}
	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}
	
	

}
