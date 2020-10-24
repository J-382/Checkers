package town;

import java.math.*;
import java.util.*;
import java.awt.Toolkit;
import java.util.PriorityQueue;
import java.util.Random;
/**
 * A graph class with some common graph algorithm
 * 
 * @author Angie Medina - Jose Perez
 * @version 4/10/2020
 */
public class Graph
{
    private ArrayList<Tuple> deadEnds;
    private HashMap<Integer, ArrayList<Integer>> graph;
    private HashMap<Integer, Integer> cnt;
    // instance variables - replace the example below with your own
    /**
     * Constructor for objects of class Graph
     */
    public Graph(HashMap<Integer, ArrayList<Integer>> graph)
    {
        this.graph = graph;
    }
    
    static public HashMap<Integer, ArrayList<Integer>> graphMaker(String input[]){
        HashMap<Integer, ArrayList<Integer>> graph = new HashMap<Integer, ArrayList<Integer>>();
        for(String i: input){
            int a = Integer.parseInt(i.split(" ")[0]), b = Integer.parseInt(i.split(" ")[1]);
            if(!graph.keySet().contains(a)) graph.put(a,new ArrayList<Integer>());
            if(!graph.keySet().contains(b)) graph.put(b,new ArrayList<Integer>());
            if(!graph.get(a).contains(b)) graph.get(a).add(b);
            if(!graph.get(b).contains(a)) graph.get(b).add(a);
        }
        return graph;
    }
    
    /*
     * Makes a minimum Spanning Tree with the current information in the simulator, 
     * then makes slightly transparent those streets and signs that don't belong to the M.S.T
     */
    public static String[] mst(ArrayList<String> vertex, double[][] edges, ArrayList<Integer> components, int size){
        /* A 2 elements tuple Class */
        class TupleD{
            public final double a; public final double b;
            public TupleD(double a, double b){ this.a = a; this.b = b;}
        }
        /* A comparator for a 2 elements tuple */
        class tupleComparator implements Comparator<TupleD>{
            @Override public int compare(TupleD a, TupleD b){ return a.a>b.a?1:-1;}
        }
        double[] distances = new double[size]; String[] father = new String[size]; boolean[] visited = new boolean[size]; 
        PriorityQueue<TupleD> qu = new PriorityQueue(new tupleComparator()); Arrays.fill(visited,false); Arrays.fill(distances,Integer.MAX_VALUE);
        for(int ini: components){
            distances[ini] = 0; qu.add(new TupleD(0,ini));
            while(qu.size()>0){
                int u = (int) qu.poll().b;
                if (!visited[u]){
                    visited[u] = true;
                    for(int v=0; v<size;v++){
                        if(!visited[v] && distances[v]>edges[u][v]){
                            distances[v] = edges[u][v]; father[v] = vertex.get(u);
                            qu.add(new TupleD(distances[v],(double) v));
                        }
                    }
                }
            }
        }
        return father;
    }
    
    /* Breadth First Search Algorithm implementation
     * Returns a list with the visited nodes
     */
    private static ArrayList<Integer> bfs(int ini, int size, ArrayList<String> vertex, double[][] edges){
        boolean[] visited = new boolean[size];
        ArrayList<Integer> set = new ArrayList<Integer>();
        Arrays.fill(visited,false);
        Deque<Integer> stack = new ArrayDeque<Integer>();
        stack.push(ini);
        while(!stack.isEmpty()){
            int u = stack.pop();
            if(!visited[u]){
                set.add(u);
                for(int v=0; v<edges[u].length;v++){
                    if(!visited[v] && edges[u][v]!=Double.MAX_VALUE){
                        stack.push(v);
                    }
                }
                visited[u]=true;
            }
        }
        return set;
    }   
    
    /* 
     * Returns a list with the inital nodes for each subgraph in the current town
     */
    public static ArrayList<Integer> componentsFinder(int size, ArrayList<String> vertex, double[][] edges){
        ArrayList<Integer> vertexNumbers = new ArrayList<Integer>(), graphs = new ArrayList<Integer>();
        for(int i=0; i<size; i++) {vertexNumbers.add(i);}
        while(!vertexNumbers.isEmpty()){
            int ini = vertexNumbers.get(0);
            graphs.add(ini);
            ArrayList<Integer> used = bfs(ini,size,vertex,edges);
            ArrayList<Integer> aux = new ArrayList<Integer>();
            for(int i: vertexNumbers) if(!used.contains(i)) aux.add(i);
            vertexNumbers.clear();
            for(int i: aux) vertexNumbers.add(i);
        }
        return graphs;
    }
    
    public void findDeadEnds(){
        cnt = new HashMap<Integer, Integer>();
        Deque<Integer> qu = new ArrayDeque<Integer>();
        deadEnds = new ArrayList<Tuple>();
        for(Integer i: graph.keySet()){
            cnt.put(i,graph.get(i).size());
            if(graph.get(i).size()==1) qu.add(i);
        }
        while(!qu.isEmpty()){
            int u = qu.pollFirst();
            if(cnt.get(u)>0){
                cnt.replace(u,-1);
                boolean deadEnd = true;
                for(int i: graph.get(u)){
                    if(cnt.get(i)>0){
                        cnt.replace(i,cnt.get(i)-1);
                        int aux = cnt.get(i);
                        for(int j: graph.get(i)){if(qu.contains(j)){aux--;}}
                        if(cnt.get(i)==1 || aux==1){ if(cnt.get(i)==1) qu.add(i); deadEnd = false;}
                    }
                }
                if(deadEnd){
                    for(int i: graph.get(u)){
                        if(cnt.get(i)>0){deadEnds.add(new Tuple(i,u));break;}
                    }
                }
            }
        }
    }
    
    public void dfs(int father, int node){
        Deque<Tuple> qu = new ArrayDeque<Tuple>();
        qu.add(new Tuple(father,node));
        while(!qu.isEmpty()){
            boolean deadEnd=true;
            Tuple aux = qu.pollFirst();
            for(int i: graph.get(aux.b)){
                if(i!=aux.a){qu.add(new Tuple(aux.b,i));deadEnd=false;}
            }
            if(deadEnd){
                cnt.replace((int)aux.a,-2);
                cnt.replace((int)aux.b,-2);
                deadEnds.add(new Tuple(aux.b,aux.a));
            }
        }
    }
    
    public String[] solution(){
        findDeadEnds();
        int cont = 0;
        for(int i: cnt.keySet()){
            if(cnt.get(i)==0){
                if(graph.get(i).size()==1){
                    deadEnds.add(new Tuple(i,graph.get(i).get(0)));
                    deadEnds.add(new Tuple(graph.get(i).get(0),i));
                }
                else{dfs(-1,i);}
            }
        }
        for(int i=0; i<deadEnds.size();i++){
            if(cnt.get(deadEnds.get(i).a)==-1 && cnt.get(deadEnds.get(i).b)==-1){
                deadEnds.set(i,new Tuple(-1,-1));
                cont++;
            }
        }
        Collections.sort(deadEnds,new tupleComparator());
        String[] toReturn = new String[deadEnds.size()-cont+1];
        toReturn[0] = deadEnds.size()-cont+"";
        int index = 1;
        for(Tuple i: deadEnds){
            if(i.a!=-1 || i.b!=-1) {toReturn[index]=""+i.a+" "+i.b;index++;}
        }
        return toReturn;
    }
    /* A 2 elements tuple Class */
    static class Tuple{
        public final int a; public final int b;
        public Tuple(int a, int b){ this.a = a; this.b = b;}
    }
    /* A comparator for a 2 elements tuple */
    static class tupleComparator implements Comparator<Tuple>{
        @Override public int compare(Tuple a, Tuple b){ 
            return a.a==b.a?(a.b>b.b?1:-1):(a.a>b.a?1:-1);
        }
    }
}
