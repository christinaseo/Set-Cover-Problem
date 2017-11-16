/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solver;

import util.ElementSet;

/**
 *
 * @author seochri1
 */
public class ChvatalSolver extends GreedySolver{
            @Override
        public String getName(){
            return ("Chvatal");
        }
    
    @Override
public ElementSet nextBestSet(){
        ElementSet tempSet = new ElementSet();       
        double tempBest = 0.0;
        boolean uncovered;
        //need to first make sure that the elementset contains an uncovered element
        for (ElementSet i: this._modelCopy.getSet()) {
            uncovered = false;
            int elementCount = 0;
            for (Integer j:i.getElements()){
                if(this._elementsUncovered.contains(j)) {
                    uncovered = true;
                    elementCount++;
                }             
            }
            //only runs if uncovered is true, meaning at least one element in the set is overlapped with the uncovered elements
            if (uncovered == true) {
                //if this is the first time temp is 0, just assign it the value
                if(tempBest == 0.0) {
                    tempBest =((double)i.getCost()/(double)elementCount);
                    tempSet = i;
                }
                //for every next iteration compare temp cost and the i cost and take the smaller one    
                else if((double)i.getCost()/(double)elementCount<tempBest) {
                    tempBest = ((double)i.getCost()/(double)elementCount);
                    tempSet = i;
                }
                
            }
        }
        this._modelCopy.getSet().remove(tempSet);
        
        return tempSet;
    }
    
}
