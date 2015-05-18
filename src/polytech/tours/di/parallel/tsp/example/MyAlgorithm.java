package polytech.tours.di.parallel.tsp.example;

import java.util.Properties;

import polytech.tours.di.parallel.tsp.Algorithm;
import polytech.tours.di.parallel.tsp.Instance;
import polytech.tours.di.parallel.tsp.InstanceReader;
import polytech.tours.di.parallel.tsp.Solution;
import polytech.tours.di.parallel.tsp.TSPComputation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyAlgorithm implements Algorithm {

	@Override
	public Solution run(Properties config) {
		System.out.println("Algorithm here: I'm going to solve the TSP");
		System.out.println("The instance is: " + config.getProperty("instance"));

		//read instance
		InstanceReader ir=new InstanceReader();
		ir.buildInstance(config.getProperty("instance"));
		//get the instance
		Instance instance=ir.getInstance();

        System.out.println("Taille de la matrice de distance : "+instance.getN());

        //print some distances
        for (int i = 0 ; i <= 2 ; i++)
			for (int j = 0 ; j <= 2 ; j++)
				System.out.println("d("+i+","+j+")="+instance.getDistance(i, j));


        System.out.println("I am going to use: "+config.getProperty("threads")+ " threads");

        int iterations = Integer.parseInt(config.getProperty("iterations"));
        int granularite = 16;   // Plus c'est grand, plus le grain est fin
        int nbTasks = iterations/granularite;

        System.out.println("I am going to carry: "+iterations+ " iterations");

        //Task are managed and executed by an ExecutorService
        ExecutorService executor = Executors.newFixedThreadPool(iterations);
        //Initialize the results list and the tasks list
        List<Future<Solution>> results = null;
        List<Callable<Solution>> tasks=new ArrayList<Callable<Solution>>();

        //Creating the list of tasks
        for(int t=1; t<=nbTasks; t++){
            tasks.add(new TSPComputation(iterations/nbTasks,instance));
        }


        try {
            //Ask the executor to execute the tasks and store the results on a list
            results=executor.invokeAll(tasks);
            executor.shutdown();
        } catch (InterruptedException e) {
            System.out.println("Echec lors de l'appel à l'éxécution des tâches");
        }


        //Getting the results
        try{
            Solution sStar = new Solution();
            Solution sFound;
            sStar.setOF(Double.MAX_VALUE);
            for(Future<Solution> res :results){
                sFound = res.get();
                if (sStar.getOF() <= sFound.getOF())
                    sStar = sFound;
            }
            return sStar;

        }catch (InterruptedException | ExecutionException e)
        {
            System.out.println("Error while getting results");
        }
        



		return null;
	}

}
