/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.TreeSet;
import util.ElementSet;
import java.util.List;

/**
 *
 * @author seochri1
 */
public class SCPModel {
    //sorted set of all the individual sets
    private TreeSet<ElementSet> coverOfSets;

    public SCPModel(SCPModel copy){
        coverOfSets = new TreeSet<>();
        for(ElementSet i:copy.getSet())
            this.coverOfSets.add(i);
    }
    public SCPModel() {
        coverOfSets = new TreeSet<>();
    }
    
    public TreeSet<ElementSet> getSet(){
        return coverOfSets;
    }
    
    public int getNumElements(){
        TreeSet<Integer> temp = new TreeSet<>();
        int num = 0;
        for (ElementSet i: coverOfSets)
            for (Integer j:i.getElements())
                temp.add(j);
        num = temp.size();        
        return num;
    }
    
    public TreeSet<Integer> getElements(){
        TreeSet<Integer> temp = new TreeSet<>();
        for (ElementSet i: coverOfSets)
            for (Integer j:i.getElements())
                temp.add(j);     
        return temp;
    }
    
    public void addSetToCover(int setNum, double weight,List<Integer> elements){
        //want to create a new instance of element set and add in the values
        ElementSet NewElements = new ElementSet();
        NewElements.add(setNum,weight,elements);
        //add the parameters into tho given elementset, and then add that element set into coverofSets.
            coverOfSets.add(NewElements);
                
    }
    
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Weighted SCP model:\n"));
        sb.append(String.format("---------------------\n"));
        
        //need to find number of unique element IDs
        sb.append(String.format("Number of elements (n): %d\n", getNumElements()));
        sb.append(String.format("Number of sets (m): %d\n\n", coverOfSets.size()));
        sb.append(String.format("Set details:\n"));
        sb.append(String.format("----------------------------------------------------------\n"));
        for (ElementSet var : coverOfSets)
            sb.append(var);      
    return sb.toString();
    }
}
