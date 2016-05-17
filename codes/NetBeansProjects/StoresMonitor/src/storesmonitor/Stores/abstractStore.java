/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storesmonitor.Stores;

/**
 *
 * @author Yi
 */
import java.util.List;
import storesmonitor.data.productLink;


public abstract class abstractStore {
	protected List<productLink> links;
	
	
	public abstractStore(){}
	
	public void isInStock(){}
	

	public List<productLink> getLinks() {
		return links;
	}

	public void setLinks(List<productLink> links) {
		this.links = links;
	}

	
	
	
}