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
    
    public void solveTest1(){
        String[] input = {"20 17","9 4","5 20","17 13","19 18","5 16","18 19","11 7","10 4","8 15","15 13","13 16","2 11","4 3","2 7","16 12","15 3","8 3"};
        String[] solution = solve(input);
        for(String i: solution) System.out.println(i);
    }
    
    public void solveTest2(){
        String[] input = {"5 4","1 3","2 3","3 4","4 5"};
        String[] solution = solve(input);
        for(String i: solution) System.out.println(i);
    }
    
    public void simulateTest1(){
        String[] input = {"5 4","1 3","2 3","3 4","4 5"};
        simulate(input,true);
    }
    
    public void simulateTest2(){
        String[] input = {"8 8","1 2","1 3","2 3","1 5","1 6","3 4","6 7","6 8"};
        simulate(input,true);
    }
}