package polytech.tours.di.parallel.tsp.example;

import java.util.Properties;

import polytech.tours.di.parallel.tsp.Algorithm;
import polytech.tours.di.parallel.tsp.Solution;

public class MyAlgorithm implements Algorithm {

	@Override
	public Solution run(Properties config) {
		System.out.println("Algorithm here: I'm going to solve the TSP");
		System.out.println("The instance is: "+config.getProperty("instance"));
		
		System.out.println("I am going to use: "+config.getProperty("threads")+ " threads");
		for(int p=0; p<Integer.valueOf(config.getProperty("threads"));p++){
			(new Thread(new Runnable()
					{
						@Override
						public void run() {
							System.out.println("Hi I am thread "+Thread.currentThread().getName());	
						}
					}
					)).start();;
		}
		
		System.out.println("I am going to carry: "+config.getProperty("iterations")+ " iterations");
		return null;
	}

}
