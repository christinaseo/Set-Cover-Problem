package solver;
import java.util.SortedSet;
import java.util.TreeSet;
import model.SCPModel;
import util.ElementSet;

/** This is the main method that all solvers inherit from.  It is important
 *  to note how this solver calls nextBestSet() polymorphically!  Subclasses
 *  should *not* override solver(), they need only override nextBestSet().
 * 
 *  We'll assume models are well-defined here and do not specify Exceptions
 *  to throw.
 * 
 * @author ssanner@mie.utoronto.ca
 *
 */
public abstract class GreedySolver {
	
	protected String _name;			  // name of algorithm type
	protected double _alpha;          // minimum required coverage level in range [0,1]
	protected SCPModel _model;        // the SCP model we're currently operating on
	protected double _objFn;          // objective function value (*total cost sum* of all sets used)
	protected double _coverage;       // actual coverage fraction achieved
	protected long _compTime;         // computation time (ms)
        protected TreeSet<ElementSet> _solnSets;     // Set that stores all the sets that have already been used
        protected TreeSet<Integer> _elementsUncovered; //Set the stores all the integers that are still uncovered
        protected SCPModel _modelCopy;  //Make a copy of the model and use this to keep track of what sets have already been used
        
	// Basic setter (only one needed)
	public void setMinCoverage(double alpha) { _alpha = alpha; }
	public void setModel(SCPModel model) { _model = model; }
	
	// Basic getters
	public double getMinCoverage() { return _alpha; }
	public double getObjFn() { return _objFn; }
	public double getCoverage() { return _coverage; }
	public long getCompTime() { return _compTime; }
	public String getName() { return _name; }
		
	// TODO: Add any helper methods you need
        public void reset(){
            _objFn = 0.0;
            _coverage = 0.0;
            _compTime = 0;
        } 
        
        public double computeCoverage(){
            double temp = 0;
            TreeSet<Integer> temp1 = new TreeSet<>();
            //add up all the coverage of the solution sets and return the number as a fraction 
            for (ElementSet i : _solnSets)
                for (int j: i.getElements())
                    temp1.add(j);
            temp = (double)((double)temp1.size())/(double)(this._model.getNumElements());
            return temp;
        }
        
        public double computeCost(){
            double temp = 0.0;
            //TreeSet<Integer> temp1 = new TreeSet<>();
            //add up all the costs for each element in the solution set
            for (ElementSet i: _solnSets){
                temp += i.getCost();       
            }
            return temp;
        }
	
	/** Run the simple greedy heuristic -- add the next best set until either
	 *  (1) The coverage level is reached, or 
	 *  (2) There is no set that can increase the coverage.
	 */
	public void solve() {
		//ElementSet tempSet = new ElementSet();
		// Reset the solver
		reset();
                _modelCopy = new SCPModel(_model);
                
                //need to make a copy so I can iterate through the copy         
                //System.out.println("This is model copy: "+_modelCopy.getSet());
               // System.out.println("This is model: "+_model.getSet());
               GreedySolver tempSolver = this;
                _elementsUncovered = this._modelCopy.getElements();
                _solnSets = new TreeSet<>();
                
		// Begin the greedy selection loop
		long start = System.currentTimeMillis();
		System.out.println("Running '" + getName() + "'...");
		// while (set elements remaining not covered > max num that can be left uncovered
		//        AND all sets have not been selected)
		//
                while (((1-_coverage)>(1- _alpha))&& _solnSets.size() < _model.getSet().size()) { //need to add in the count of the sets
                    
		//      Call nextBestSet() to get the next best ElementSet to add (if there is one)                   
		// 		Update solution and local members  
                ElementSet tempSet = tempSolver.nextBestSet();
                if (tempSet != null)
                _solnSets.add(tempSet);
                if(tempSet == null)
                    break;
                //update elements uncovered by removing any that are in the temp set

                for(Integer i : tempSet.getElements())
                    if(_elementsUncovered.contains(i))
                        _elementsUncovered.remove(i);
                _coverage = this.computeCoverage();
                _objFn = this.computeCost();
                //System.out.println("objfun equals: "+ _objFn);
                System.out.format("- Selected: " + tempSet);
                }
                
		// Record final set coverage, compTime and print warning if applicable
		_compTime = System.currentTimeMillis() - start;
		if (_coverage < _alpha)
			System.out.format("\nWARNING: Impossible to reach %.2f%% coverage level.\n", 100*_alpha);
		System.out.println("Done.");
	}
	
	/** Returns the next best set to add to the solution according to the heuristic being used.
	 * 
	 *  NOTE 1: This is the **only** method to be implemented in child classes.
	 *  
	 *  NOTE 2: If no set can improve the solution, returns null to allow the greedy algorithm to terminate.
	 *  
	 *  NOTE 3: This references an ElementSet class which is a tuple of (Set ID, Cost, Integer elements to cover)
	 *          which you must define.
	 * 
	 * @return
	 */
	public abstract ElementSet nextBestSet(); // Abstract b/c it must be implemented by subclasses
	
	/** Print the solution
	 * 
	 */
	public void print() {
		System.out.println("\n'" + getName() + "' results:");
		System.out.format("'" + getName() + "'   Time to solve: %dms\n", _compTime);
		System.out.format("'" + getName() + "'   Objective function value: %.2f\n", _objFn);
		System.out.format("'" + getName() + "'   Coverage level: %.2f%% (%.2f%% minimum)\n", 100*_coverage, 100*_alpha);
		System.out.format("'" + getName() + "'   Number of sets selected: %d\n", _solnSets.size());
		System.out.format("'" + getName() + "'   Sets selected: ");
		for (ElementSet s : _solnSets) {
			System.out.print(s.getId() + " ");
		}
		System.out.println("\n");
	}
	
	/** Print the solution performance metrics as a row
	 * 
	 */
	public void printRowMetrics() {
		System.out.format("%-25s%12d%15.4f%17.2f\n", getName(), _compTime, _objFn, 100*_coverage);
	}	
}
