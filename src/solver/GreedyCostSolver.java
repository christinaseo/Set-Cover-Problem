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
public class GreedyCostSolver extends GreedySolver {

    @Override
    public String getName(){
        return ("Cost");
    }
        
    @Override
    public ElementSet nextBestSet(){
        ElementSet tempSet = new ElementSet();       
        double tempCost = 0.0;
        boolean uncovered = true;
        //need to first make sure that the elementset contains an uncovered element, then
        //store the cost somewhere
        for (ElementSet i: this._modelCopy.getSet()) {
            for (Integer j:i.getElements()){
                uncovered = false;
                if(this._elementsUncovered.contains(j)) {
                    uncovered = true;
                    break;
                }
            }
            //only runs if uncovered is true
            if (uncovered == true) {
                //if this is the first time temp is 0, just assign it the value
                if(tempCost == 0.0) {
                    tempCost = i.getCost();
                    tempSet = i;
                }
                //for every next iteration compare temp cost and the i cost and take the smaller one    
                else if(i.getCost()<tempCost) {
                    tempCost = i.getCost();
                    tempSet = i;
                }
            }
            }
        this._modelCopy.getSet().remove(tempSet);
        
        return tempSet;
    }
    
}
