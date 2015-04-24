package polytech.tours.di.parallel.tsp.example;

import java.util.Collections;
import java.util.Properties;

import polytech.tours.di.parallel.tsp.Algorithm;
import polytech.tours.di.parallel.tsp.Instance;
import polytech.tours.di.parallel.tsp.InstanceReader;
import polytech.tours.di.parallel.tsp.Solution;
import polytech.tours.di.parallel.tsp.TSPCostCalculator;

/**
 * Implements an example in which we read an instance from a file and print out some of the distances in the distance matrix.
 * Then we generate a random solution and computer its objective function. Finally, we print the solution to the output console.
 * @author Jorge E. Mendoza (dev@jorge-mendoza.com)
 * @version %I%, %G%
 *
 */
public class ExampleAlgorithm implements Algorithm {

	@Override
	public Solution run(Properties config) {

		//read instance
		InstanceReader ir=new InstanceReader();
		ir.buildInstance(config.getProperty("instance"));
		//get the instance 
		Instance instance=ir.getInstance();
		//print some distances
		System.out.println("d(1,2)="+instance.getDistance(1, 2));
		System.out.println("d(10,19)="+instance.getDistance(10, 19));
		
		//build a random solution
		Solution s=new Solution();
		for(int i=0; i<instance.getN(); i++){
			s.add(i);
		}
		Collections.shuffle(s);
		//set the objective function of the solution
		s.setOF(TSPCostCalculator.calcOF(instance.getDistanceMatrix(), s));
		
		//return the solution
		return s;
	}

}
