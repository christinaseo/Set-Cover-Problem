/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.TreeSet;
import java.util.List;

/**
 *
 * @author seochri1
 */
public class ElementSet implements Comparable {
    private int setID;
    private double cost;
    private TreeSet<Integer> elements;
    
    
    public ElementSet(){
        elements = new TreeSet<>();       
    }    
    
    public double getCost(){
        return cost;
    }   
    
    public int getId(){
        return setID;
    }
    
    public int getSizeElements(){
        return elements.size();
    }
    
    public TreeSet<Integer> getElements(){
        return elements;
    }
    
    public void add(int setNum, double weight, List<Integer> set){
        setID = setNum;
        cost = weight;
        for (int i : set)
            elements.add(i);
    }
    
    
    //@Override
    
    // Rule: return 1  if this should come after o
		//       return -1 if this should come before o
		//       return 0 if equals() say these are equal!
    public int compareTo(Object O){
        if (!(O instanceof ElementSet))
            return 1;
        
        ElementSet E = (ElementSet)O;
        
        if(this.setID == E.setID)
            return 0;
        else if(this.setID > E.setID)
            return 1;
        else
            return -1;
        
        //if (this.elements.size()*this.cost == E.elements.size()*E.cost)//if two sets have the same score for the greedy function, the set with the lower ID should be chosen
            //return Math.min(this.setID,E.setID); 
        //else {
            //if they are not equal, just return the ID of the larger one. How do 
            //if (this.elements.size()*this.cost < E.elements.size()*E.cost)
               // return this.setID;
            //else
               // return E.setID;
        }
    
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Set ID:  %d  Cost:  %4.2f  Element IDs: ", setID, cost));
        sb.append(elements);
       // for (int var : elements)
           // sb.append(String.format("%d, ",var));
       // sb.append(String.format("]"));
        sb.append(String.format("\n"));
    return sb.toString();  
    }
    
   
    
}
