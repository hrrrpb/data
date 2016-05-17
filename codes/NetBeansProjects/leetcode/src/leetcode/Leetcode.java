/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leetcode;

import static java.lang.Integer.max;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;

/**
 *
 * @author yi
 */
public class Leetcode {

    public int[] countBits(int num) {
        int[] results = new int[num+1];
        for(int i=0; i<=num; i++) {
            if (i==0) results[i] = 0;
            
            else {
                if (i%2 != 0) results[i] = results[i-1]+1;
                else results[i] = results[i/2];
            }
        }
        
        return results;
    }
    
    
    
    
    
    public boolean increasingTriplet(int[] nums) {
        if (nums.length < 3) return false;
        
        int localHigh = 0; 
        int localLow = 0; 
        boolean set = false;
        for (int i =0; i< nums.length -1; i++) {
            
            if (nums[i] >= nums[i+1]) continue;
            else {
                if (set) {
                    if (localHigh < nums[i+1] || localLow < nums[i]) return true;
                    else {
                        localHigh = nums[i+1];
                        localLow = nums[i];
                    }
                }
                
                if (!set) {
                    localHigh = nums[i+1];
                    localLow = nums[i];
                    set = true;
                }            
                
            }
            
        }
        
        return false;
        
    }
    
    
    public List<String> findItinerary(String[][] tickets) {
        
        ArrayList<String> result = new ArrayList();
        
        Map<String, Map> info = new HashMap();
        
        int count = tickets.length + 1; 
        
        for (String[] ticket : tickets) {
            
            if (!info.containsKey(ticket[0])){
               
                Map<String, Integer> dests = new TreeMap(); //sorted map
                dests.put(ticket[1], 1);
                
                info.put(ticket[0], dests);
                
            
            }
            
            else {
                 Map<String, Integer> dests = (Map<String, Integer>) info.get(ticket[0]);
                 
                 if (dests.containsKey(ticket[1])) {
                     
                     dests.put(ticket[1], dests.get(ticket[1]) + 1);
                 }
                 else dests.put(ticket[1], 1);
                 
                
            }
            
            if (!info.containsKey(ticket[1])){
                Map<String, Integer> dests = new TreeMap(); //sorted map
       
                info.put(ticket[1], dests);
            
            }
    
        }
        
        //start to add "JFK"
        String start = "JFK";
        
        
        
        result.add(start);
        
        
        dfs(info, result, count, start);
        
        return result;
        
    }
    
    
    public boolean dfs(Map<String, Map> info, ArrayList<String> result, int totalCnt, String start) {
        if (result.size() == totalCnt) return true;
        Map<String, Integer> dests = (Map<String, Integer>) info.get(start);
        for(String dest : dests.keySet()) {
            if (dests.get(dest) > 0) {
                dests.put(dest, dests.get(dest)-1);
                result.add(dest);
                if (dfs(info,result,totalCnt,dest)) return true;
                result.remove(result.size()-1);
                dests.put(dest, dests.get(dest)+1);
                
            }
        }
        
        return false;
        
        
        
    }
    
    
    
    public static void main(String[] args) {
        Leetcode code = new Leetcode();
        
        
        String[][] tickets = {{"EZE","TIA"},{"EZE","HBA"},{"AXA","TIA"},{"JFK","AXA"},
            {"ANU","JFK"},{"ADL","ANU"},{"TIA","AUA"},{"ANU","AUA"},{"ADL","EZE"},
            {"ADL","EZE"},{"EZE","ADL"},{"AXA","EZE"},{"AUA","AXA"},{"JFK","AXA"},
            {"AXA","AUA"},{"AUA","ADL"},{"ANU","EZE"},{"TIA","ADL"},{"EZE","ANU"},{"AUA","ANU"}
        };
       // String[][] tickets = {{"EZE","AXA"},{"TIA","ANU"},{"ANU","JFK"},{"JFK","ANU"},
         //   {"ANU","EZE"},{"TIA","ANU"},{"AXA","TIA"},{"TIA","JFK"},{"ANU","TIA"},{"JFK","TIA"}};
        
            
        
        //System.out.println(tickets.length);
        
        System.out.println(code.findItinerary(tickets));
        
    }
    
}
