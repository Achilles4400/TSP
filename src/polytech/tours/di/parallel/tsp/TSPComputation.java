package polytech.tours.di.parallel.tsp;

import polytech.tours.di.parallel.tsp.Instance;
import polytech.tours.di.parallel.tsp.Solution;

import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by kev1 on 02/05/15.
 */
public class TSPComputation implements Callable<Solution> {

    private long runs;
    private int rand;
    private Instance instance;

    public TSPComputation(long runs, int d, Instance instance){
        this.runs=runs;
        this.rand=d;
        this.instance = instance;
    }

    public Solution call() throws Exception {

        Solution sstar = new Solution();

        this.MSLS();
        return sstar;
    }

    public Solution MSLS(){
        Solution sStar = new Solution();
        Solution s;
        for (int i = 0 ; i < runs ; i++){
            s=buidInitialSolution();
//            s=localSearch(s);

        }

        return sStar;
    }

    public Solution buidInitialSolution(){
        Solution s=new Solution();
        for(int i=0; i<instance.getN(); i++){
            s.add(i);
        }
        Collections.shuffle(s);
        return s;
    }


    public Solution LocalSearch(Solution s){
        Solution sStar = s.clone();
        sStar.setOF(5l);




        return sStar;
    }


}
