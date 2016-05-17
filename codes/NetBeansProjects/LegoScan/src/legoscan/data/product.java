/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legoscan.data;

/**
 *
 * @author yi
 */
public class product {
    private String itemNum;
    private String itemName;
    private String itemUrl;
    private float historyLow;
    private float historyHigh;
    private float current; 
    
    
    public product() {
        
    }
    
    public product(String itemNum, String itemUrl,float current, float hisHigh, float hisLow){
        this.itemNum = itemNum;
        this.itemUrl = itemUrl;
        this.current = current;
        this.historyHigh = hisHigh;
        this.historyLow = hisLow;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    
    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public float getHistoryLow() {
        return historyLow;
    }

    public void setHistoryLow(float historyLow) {
        this.historyLow = historyLow;
    }

    public float getHistoryHigh() {
        return historyHigh;
    }

    public void setHistoryHigh(float historyHigh) {
        this.historyHigh = historyHigh;
    }

   

    public float getCurrent() {
        return current;
    }

    public void setCurrent(float current) {
        this.current = current;
    }
    
    
    
}
