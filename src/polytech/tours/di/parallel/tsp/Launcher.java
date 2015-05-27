package polytech.tours.di.parallel.tsp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Launches the optimization algorithm
 * @author Jorge E. Mendoza (dev@jorge-mendoza.com)
 * @version %I%, %G%
 *
 */
public class Launcher {
	
	/**
	 * 
	 * @param args[0] the file (path included) with the configuration settings
	 */
	public static void main(String[] args) {
		//read properties
		Properties config=new Properties();
		try {
			config.loadFromXML(new FileInputStream(args[0]));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//dynamically load the algorithm class
		Algorithm algorithm=null;;
		try {
			Class<?> c = Class.forName(config.getProperty("algorithm"));
			algorithm=(Algorithm)c.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e){
			e.printStackTrace();
		}
		
//		//run algorithm
//		Solution s=algorithm.run(config);
//
//		//report solution
//		System.out.println(s);

        //experimental set up
        long runs[] = {10, 20, 30, 40, 50, 100};
        int tasks[]={1, 2, 5, 10, 20};
        int threads[]={1, 10, 20, 50, 100};
        for(int i=1; i<=100_000; i++); //warm up

        //run experiment for each combination of parameter values
        System.out.println("RUNS\tTASKS\tTHREADS\tCPU\t\tCOST");
        for(int r=0;r<runs.length;r++){
            for(int t=0; t<tasks.length; t++){
                for(int p=0; p<threads.length; p++){
                    config.setProperty("iterations",String.valueOf(runs[r]));
                    config.setProperty("threads", String.valueOf(threads[p]));
                    config.setProperty("tasks", String.valueOf(tasks[t]));
                    //experiment
                    long start=System.currentTimeMillis();
                    Solution s=algorithm.run(config);
                    long end=System.currentTimeMillis();
                    System.out.println(runs[r]+"\t\t"+tasks[t]+"\t\t"+threads[p]+"\t\t"+(end-start)/1000d+"\t\t"+s.getOF());
                }
            }
        }

	}

}
