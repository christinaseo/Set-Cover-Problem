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
public class GreedyCoverageSolver extends GreedySolver{
        
        @Override
        public String getName(){
            return ("Coverage");
        }
    
        @Override  
        public ElementSet nextBestSet(){
            //initialize temp to the first elementset
        ElementSet temp = this._modelCopy.getSet().first();   
        int tempInt = 0;
        for (ElementSet i: this._modelCopy.getSet()) {
            int tempInt1 = 0;
            for (Integer j: i.getElements())
                if (this._elementsUncovered.contains(j))
                    tempInt1++;
            if (tempInt < tempInt1){
                temp = i;
                tempInt = tempInt1;
            }
        }
        this._modelCopy.getSet().remove(temp);
        
        return temp;
    }
}
