package town;

import java.util.*;
import java.util.Arrays;
import java.io.*;
/**
 * Solve the ICPC problem dead-end detector
 * @author Angie Medina - Jose Perez
 * @version 25/09/20
 */
public class TownContest
    
{
    private boolean ok;
    
    /**
     * Constructor for objects of class TownContest
     */
    public TownContest(){}
    
    /**
     * Solve the problem of the dead-end detector
     * @param input the format is the same of the problem dead-end detector
     * @return the number of the no reduntant signs in the city along to the corresponding signs
     */
    public String[] solve(String[] input){
        String[] aux = Arrays.copyOfRange(input,1,input.length);
        HashMap<Integer, ArrayList<Integer>> graph = Graph.graphMaker(aux);
        ok = true;
        return Graph.solution(graph);
    }
    
    /**
     * Simulate the solution to the problem dead-end detector
     * @param input the format is the same of the problem dead-end detector
     * @param slow true if the user wants the simulator go slower tha usual
     *              false otherwise
     */
    public void simulate(String[] input, boolean slow){
        ok = true;
        Town simulator = new Town(input, slow);
    }
    
    /**
     * Return if the class has been used
     */
    public boolean ok(){
        return ok;
    }
}