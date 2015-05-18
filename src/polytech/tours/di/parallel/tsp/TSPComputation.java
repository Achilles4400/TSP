package polytech.tours.di.parallel.tsp;

import polytech.tours.di.parallel.tsp.Instance;
import polytech.tours.di.parallel.tsp.Solution;

import java.util.Collections;
import java.util.concurrent.Callable;

/**
 * Created by kev1 on 02/05/15.
 *
 */

public class TSPComputation implements Callable<Solution> {

    private long runs;
    private Instance instance;

    public TSPComputation(long runs, Instance instance){
        this.runs=runs;
        this.instance = instance;
    }

    public Solution call() throws Exception {

        Solution sStar;

        sStar = this.MSLS();
        return sStar;
    }

    public Solution MSLS(){
        Solution sStar = new Solution();
        Solution s;
        for (int i = 0 ; i < runs ; i++){
            s=buidInitialSolution();
            s.setOF(TSPCostCalculator.calcOF(instance.getDistanceMatrix(), s));
            s=localSearch(s);
            if (i == 0)
                sStar = s;
            else if (s.getOF() <= sStar.getOF())
                sStar = s;
        }

        return sStar;
    }


    //Construction d'une solution initial par random
    public Solution buidInitialSolution(){
        Solution s=new Solution();
        for(int i=0; i<instance.getN(); i++){
            s.add(i);
        }
        Collections.shuffle(s);
        return s;
    }


    public Solution localSearch(Solution s){
        Solution sStar = s.clone();
        Solution temp;
        Boolean continu = true;
        while(continu)
        {
            continu=false;
            for (int i = 0 ; i <= s.size() ; i++){
                for (int j = 0 ; j <= s.size() ; j++){
                    if (i == j)
                        continue;
                    temp = s.clone();
                    temp.swap(i,j);
                    temp.setOF(TSPCostCalculator.calcOF(instance.getDistanceMatrix(), s));
                    if (temp.getOF() <= sStar.getOF()) {
                        sStar = temp;
                        continu = true;
                    }
                }
            }
            s = sStar;
        }
        return sStar;
    }
}
