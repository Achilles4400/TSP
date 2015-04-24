package polytech.tours.di.parallel.tsp;

import java.util.ArrayList;

/**
 * Computes the cost of a TSP solution
 * @author Jorge E. Mendoza (dev@jorge-mendoza.com)
 * @version %I%, %G%
 * @since Jan 9, 2015
 *
 */
public class TSPCostCalculator{
	
	private static ArrayList<Integer> s;
	private static double[][] distMatrix;
	
	/**
	 * @throws ClassCastException if <code>instance</code> cannot be casted to an instance of {@link TSPInstance} 
	 * or <code>s</code> cannot be casted to an instance of {@link Permutation} 
	 */
	public double calcOF(Instance instance, Solution s) throws ClassCastException {
		distMatrix=instance.getDistanceMatrix();
		TSPCostCalculator.s=s;
		return calc();
	}
	/**
	 * static access to the calculator
	 * @param matrix the distance matrix
	 * @param s the TSP solution (permutation)
	 * @return the cost of <code>s</code>
	 */
	public static double calcOF(double[][] matrix, ArrayList<Integer> s){
		distMatrix=matrix;
		TSPCostCalculator.s=s;
		return calc();
	}
	/**
	 * internal implementation of the calculator
	 * @return the cost of a TSP solution
	 */
	private static double calc(){
		double cost=0;
		for(int i=1;i<s.size();i++){
			cost=cost+distMatrix[s.get(i-1)][s.get(i)];
		}
		cost=cost+distMatrix[s.get(s.size()-1)][s.get(0)];
		return cost;
	}

}
