package town;

import Shapes.*;
import java.math.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import java.util.regex.*;
import java.util.PriorityQueue;
import java.util.Random;
/**
* Pretends to simulate a town who has decided to improve road sign placement, especially for dead ends.
* 
* @author Angie Medina - Jose Perez
* @version 25/09/2020
*/
public class Town{   
    // instance variables - replace the example below with your own
    private int height, width,actionsIterator;
    private HashMap<String,Location> locations;
    private HashMap<String,Street> streets;
    private HashMap<String,Sign> signs;
    private ArrayList<String> usefulThings, deadEnd;
    private boolean ok, isVisible,unReAction, slow;
    private Location lastLocation;
    private Street lastStreet;
    private Sign lastSign;
    private ArrayList<String> actions;
    /**
     * Constructor of the town given its length and width
     * 1st mini cicle: Create
     * @param height of the town in pixels
     * @param width of the town in pixels
     */
    public Town(int height, int width, boolean slow)
    {
        // initialise instance variables
        this.height = height;
        this.width = width;
        Canvas canvas = Canvas.getCanvas(height,width);
        locations = new HashMap<String,Location>();
        streets = new HashMap<String,Street>();
        signs = new HashMap<String,Sign>();
        usefulThings = new ArrayList<String>();
        deadEnd = new ArrayList<String>();       
        isVisible = false;
        actions = new ArrayList<String>(); 
        actionsIterator = -1;
        unReAction = true;
        ok = true;
    }
    
    public Town(int height, int width)
    {
        // initialise instance variables
        this(height,width,false);
    }
   
    /**
     * Constructor of a random town given some features
     * 1st mini cicle: Create
     * @param height of the town in pixels
     * @param width of the town in pixels
     * @param number of locations (must create those randomly)
     * @param number of street (must create thouse randomly)
     * @param number of dead-ends (must create thouse randomly)
     */
    public Town(int height, int width, int numberLocations, int numberStreets, int numberDeadEnds, boolean slow){
        this(height,width,slow);
        Random r = new Random(); int[] colors = new int[numberLocations];
        for(int i = 0; i < numberLocations; i++){
            boolean isAValidLocation = false; int n=0;
            while(!isAValidLocation){
                n = r.nextInt(50);
                addLocation(Canvas.colorsList()[n],r.nextInt(500),r.nextInt(500)); isAValidLocation = ok();
            } colors[i] = n;
        }
        
        for(int i = 0; i < numberStreets; i++){
            boolean isAValidStreet = false;
            while(!isAValidStreet){
                int a = r.nextInt(numberLocations-1), b = r.nextInt(numberLocations-1);
                if(a == b) continue;
                addStreet(Canvas.colorsList()[colors[a]],Canvas.colorsList()[colors[b]]); isAValidStreet = ok();
            }
        }
        
        for(int i = 0; i < numberDeadEnds; i++){
            ArrayList<String> aux = new ArrayList<String>(streets.keySet());
            if (streets.size() > 0){
                int a = r.nextInt(streets.size()) - 1;
                addSign(aux.get(a).split("-")[0],aux.get(a).split("-")[1]);
                if(!ok) addSign(aux.get(a).split("-")[1],aux.get(a).split("-")[0]);
            }
        }
        ok = true;
    }
    
    /**
     * Constructor of a town given some specification
     * 1st mini cicle: Create
     * @param the specification corresponds to the input of the icp problem
     */
    public Town(String[] input, boolean slow){
        this(1000,1000,slow);
        String[] instructions = input;
        int l = Integer.parseInt(instructions[0].split(" ")[0]), s = Integer.parseInt(instructions[0].split(" ")[1]);
        Random r = new Random();
        String[] colorsList = Canvas.colorsList();
        Arrays.sort(colorsList);
        for(int i = 0 ; i < l; i++){
            boolean isAValidLocation = false;
            int n=0;
            while(!isAValidLocation){
                n = r.nextInt(50);
                addLocation(colorsList[i],r.nextInt(500),r.nextInt(500));
                isAValidLocation = ok();
            }
        }
        
        for(int i = 1; i <= s; i++){
            int a = Integer.parseInt(instructions[i].split(" ")[0]), b = Integer.parseInt(instructions[i].split(" ")[1]);
            addStreet(colorsList[a-1],colorsList[b-1]);
        }
    }
    
    public Town(String[] input)
    {
        // initialise instance variables
        this(input,false);
    }
    
    
    public void visibleAction(String message, String object){
        String key = "";
        if (object.equals("location")){
            int[] coord = lastLocation.getLocation();
            key = lastLocation.getColor() + " " + coord[0] + " " + coord[1];
        }
        else if(object.equals("street")){
            key = lastStreet.getLocation1() + " " + lastStreet.getLocation2();
        }
        else if(object.equals("sign")){
            ArrayList<Location> signLocations = lastSign.getLocations();
            key = signLocations.get(0).getColor() + " " + signLocations.get(1).getColor();
        }
        
        if(ok){
            if(actionsIterator<actions.size() - 1 && !unReAction) {
                actions.set(actionsIterator,message + key);
                for(int i = actionsIterator + 1; i < actions.size(); i++) actions.remove(i);
            }
            else {actions.add(message + key);actionsIterator++;}
        }    
    }
    
    /**
     * Addicionate one location given certain information
     * 2nd mini cicle: add / delete location
     * @param color is written in RGBa format
     * @param x is the position on x axis
     * @param y is the position on y axis
     */
    public void addLocation(String color, int x, int y){
        color = color.toLowerCase();
        if(locations.containsKey(color)){
            raiseError("There's already a location with such color"); //error :o
        }
        else if(!Arrays.asList(Canvas.colorsList()).contains(color)){
            raiseError("The entered color is not available."); //error :o
        }
        else{
            boolean isCollisioning;
            if(lastLocation != null) lastLocation.changeFrame();
            lastLocation = new Location(color,x,y);
            isCollisioning = isCollisioning(lastLocation);
            if(!isCollisioning) locations.put(color,lastLocation);
            if(isCollisioning) {delLocation(lastLocation.getColor());raiseError("Can't place a new location here");}
            ok = !isCollisioning;
            if(isVisible) lastLocation.makeVisible();
            if (ok){visibleAction("undo add location ", "location");}            
        }
    }
    
    /**
     * Deletes one location given certain its color
     * 2nd mini cicle: add / delete location
     * @param color is written in RGBa format
     */
    public void delLocation(String color){
        color = color.toLowerCase();
        String pat = "([A-Za-z-])*"+color+"([A-Za-z-])*";
        int x = 0 ,y = 0;
        ok = true;
        if(locations.containsKey(color)){
            for(String i: streets.keySet()){
                if(i.matches(pat)){
                    String[] streetLocations = i.split("-");
                    delStreet(streetLocations[0],streetLocations[1]);
                }
            }
            x = locations.get(color).getLocation()[0];
            y = locations.get(color).getLocation()[1];
            locations.get(color).makeInvisible();
            locations.remove(color);
        }
        else {ok = false;}
        if (ok){visibleAction("undo del location ", "location");}        
    }
    
    /**
     * Addicionate one street given certain infomation
     * 3rd mini cicle: add / delete street
     * @param locationA the color of one linked location
     * @param locationB the color of one linked location
     */
    public void addStreet(String locationA, String locationB){
        locationA = locationA.toLowerCase(); locationB = locationB.toLowerCase();
        String identifier = locationA.compareTo(locationB)<0?(locationA+"-"+locationB):(locationB+"-"+locationA);
        int[] a,b;
        if(!(locations.containsKey(locationA) && locations.containsKey(locationB))){
            raiseError("There isn't a location with such color"); //error :o
        }
        else if(streets.containsKey(identifier)){
            raiseError("There's already an identical street"); //error :o
        }
        else{
            if(lastStreet != null) lastStreet.changeColor("black");
            lastStreet = new Street(locations.get(locationA),locations.get(locationB));
            lastStreet.changeColor("gold");
            streets.put(identifier, lastStreet);
            mst();
            if(isVisible) lastStreet.makeVisible();
            ok = true;
        }
        if(ok){visibleAction("undo add street ", "street");}
    }
    
    /**
     * Deletes a street with given information
     * 3rd mini cicle: add / delete street
     * @param LocationA color of the linked location
     * @param LocationB color of the linked location
     */
    public void delStreet(String locationA, String locationB){
        locationA = locationA.toLowerCase();
        locationB = locationB.toLowerCase();
        String identifier = locationA.compareTo(locationB)<0?(locationA+"-"+locationB):(locationB+"-"+locationA);
        ok = true;
        if(streets.containsKey(identifier)){
            if(signs.containsKey(identifier)){
                delSign(locationA, locationB);
            }
            streets.get(identifier).makeInvisible();
            streets.remove(identifier);
        }
        else ok = false;
        if(ok){visibleAction("undo del street ", "street");} 
    }
    
    /**
     * Deletes a excess street
     * 3rd mini cicle: add / delete street
     */
    public void delStreet(){
        String aux="";
        double max=0;
        for(String i: streets.keySet()){
            String auxIdentifier = i.split("-")[1] + "-" + i.split("-")[0];
            if(!usefulThings.contains(i) && streets.get(i).getLength()>max && !signs.keySet().contains(i) && !signs.keySet().contains(auxIdentifier)){
                aux = i; 
                max = streets.get(i).getLength();
            }
        }
        if(aux != "") delStreet(aux.split("-")[0],aux.split("-")[1]);
        ok = true;
    }
   
    /**
     * Addicionate one sign given certain infomation
     * 4th mini cicle: add / delete sign
     * @param locationA the color of one linked location
     * @param locationB the color of one linked location
     */
    public void addSign(String locationA, String locationB){
        locationA = locationA.toLowerCase(); locationB = locationB.toLowerCase();
        String identifier1 = locationA+"-"+locationB;
        String identifier2 = locationB+"-"+locationA;
        int[] a,b;
        if(!(locations.containsKey(locationA) && locations.containsKey(locationB))){
          raiseError("There isn't a location with such color"); //error :o
        }
        else if(!streets.containsKey(identifier1) && !streets.containsKey(identifier2)){
            raiseError("There isn't a street who connects those locations"); //error :o
        }
        else if(signs.containsKey(identifier1)){
            raiseError("There's already an identical sign"); //error :o
        }
        else{
            if(lastSign != null) lastSign.changeColor();
            lastSign = new Sign(locations.get(locationA),locations.get(locationB));
            signs.put(identifier1,lastSign);
            if(isVisible) lastSign.makeVisible();
            ok = true;
        }
        
        if(ok){visibleAction("undo add sign ", "sign");}
    }
    
    /**
     * Deletes a sign with given information
     * 4th mini cicle: add / delete sign
     * @param LocationA color of the linked location
     * @param LocationB color of the li nked location
     */
    public void delSign(String locationA, String locationB){
        locationA = locationA.toLowerCase();
        locationB = locationB.toLowerCase();
        String identifier = locationA+"-"+locationB;
        ok = true;
        if(signs.containsKey(identifier)){
            signs.get(identifier).makeInvisible();
            signs.remove(identifier);
        }
        else ok = false;
        if(ok){visibleAction("undo del sign ", "sign");}
    }
    
    /**
    * Deletes an excess sign
    * 4th mini cicle: add / delete sign
    */
    public void delSign(){
        boolean wasDelete = false;
        for (String[] value : wrongSignals()){
            if (value[0] != null && value[1] != null){
                delSign(value[0], value[1]);
                wasDelete = true;
                break;
            }
        }
        ok = wasDelete;
    }
    
    /**
     * Undo the visible actions made by the town
     * 5th mini-cicle : undo / redo
     * @param action the list of visible actions made by the town
     * @param ur a string idicating if it desire to redo or undo
     */
    public void undoRedoActions(String[] action, String ur){
        if (ur.equals("redo")){
            if (action[1].equals("del")) action[1] = "add";
            else{action[1] = "del";}
        }
        
        if(action[1].equals("add")){
            if(action[2].equals("location")) delLocation(action[3]);
            else if(action[2].equals("street")) delStreet(action[3],action[4]);
            else if(action[2].equals("sign")) delSign(action[3],action[4]);
        }
        else{
            if(action[2].equals("location")) addLocation(action[3],Integer.parseInt(action[4]), Integer.parseInt(action[5]));
            else if(action[2].equals("street")) addStreet(action[3],action[4]);
            else if(action[2].equals("sign")) addSign(action[3],action[4]);
        }
    }
    
    /**
     * Undo the last visual action performed in the simulator
     * 5th mini-cicle : undo / redo
     */
    public void undo(){
        boolean canUndo = false;
        ok = true;
        for(int i=0; i<actions.size();i++) if(actions.get(i).equals("")) actions.remove(i);
        if(actionsIterator>-1){
            String[] action = actions.get(actionsIterator).split(" ");
            if(action[0].equals("undo")){ canUndo = true; }
            else if(actionsIterator > 0){
                actionsIterator--;
                action = actions.get(actionsIterator).split(" ");
                canUndo = true;
            }
            if(canUndo){
                unReAction = true;
                action[0] = "redo";
                actions.set(actionsIterator,String.join(" ",action));
                undoRedoActions(action, "undo");
                actions.remove(actions.size()-1);
                actionsIterator--;
                actions.add("");
            }
            else ok = false;
        }
        else ok = false;
        if(!ok) raiseError("Invalid action");
        unReAction = false;
    }
    
    /**
     * Redo the last visual action performed in the simulator
     * 5th mini-cicle : undo / redo
     */
    public void redo(){
        boolean canRedo = false;
        for(int i = 0; i < actions.size(); i++) if(actions.get(i).equals("")) actions.remove(i);
        ok = true;
        if(actionsIterator > - 1){
            String[] action = actions.get(actionsIterator).split(" ");
            if(action[0].equals("redo")){
                canRedo = true;
            }
            else if(actionsIterator < actions.size() - 1){
                actionsIterator++;
                action = actions.get(actionsIterator).split(" ");
                canRedo = true;
            }
            if(canRedo){
                unReAction = true;
                action[0]="undo";
                actions.set(actionsIterator,String.join(" ",action));
                undoRedoActions(action, "redo");
                actions.remove(actions.size()-1);
                actionsIterator--;
                actions.add("");
            }
            else ok = false;
        }
        else ok = false;
        if(!ok) raiseError("Invalid action");
        unReAction = false;
    }
    
    /**
    * Gives the information of the streets
    * 6th mini-cicle : consult
    * @return the colors of the locations connected by a street
    */ 
    public String[][] roadMap(){
        int dimension = streets.size();
        String[][] toReturn = new String[dimension][2];
        int i = 0;
        for (String identifier : streets.keySet()){
            String[] temp = {identifier.split("-")[0], identifier.split("-")[1]};
            toReturn[i] = temp;
            i++;
        }
        ok = true;
        return toReturn;
    }
    
    /**
    * Gives the information of the last action excuted by the simulator
    * 6th mini-cicle : consult
    * @return true if the last action was succesfully excuted
    * false otherwise
    */
    public boolean ok(){
        return ok;
    }
    
    /**
     * Gives the information of all the locations ordered alphabetically and in lower case
     * 6th mini-cicle : consult
     * @return the colors and position of the locations
     */
    public String[][] allLocations(){
        String[][] matriz = new String[locations.size()][3];
        String[] aux = new String[locations.size()];
        locations.keySet().toArray(aux);
        Arrays.sort(aux);
        for(int i=0; i<locations.size(); i++){
            int x = locations.get(aux[i]).getLocation()[0], y = locations.get(aux[i]).getLocation()[1];
            matriz[i][0] = aux[i];
            matriz[i][1] = ""+x;
            matriz[i][2] = ""+y;
        }
        return matriz;
    }
    
    /**
     * Gives the information of all the streets ordered alphabetically and in lower case
     * 6th mini-cicle : consult
     * @return the colors of the locations connected by a street
     */
    public String[][] allStreets(){
        String[][] matriz = new String[streets.size()][2];
        String[] aux = new String[streets.size()];
        streets.keySet().toArray(aux);
        Arrays.sort(aux);
        for(int i=0; i<streets.size(); i++){
            matriz[i][0] = aux[i].split("-")[0];
            matriz[i][1] = aux[i].split("-")[1];
        }
        return matriz;
    }
    
    /**
     * Gives the information of all the signs
     * 6th mini-cicle : consult
     * @return the colors of the locations connected by a street with a sign
     */
    public String[][] allSigns(){
        String[][] matriz = new String[signs.size()][2];
        String[] aux = new String[signs.size()];
        signs.keySet().toArray(aux);
        Arrays.sort(aux);
        for(int i=0; i<signs.size(); i++){
            matriz[i][0] = aux[i].split("-")[0];
            matriz[i][1] = aux[i].split("-")[1];
        }
        return matriz;
    }
    
    /**
     * Returns a list with the streets that are not necessary to move around the town 
     * 6th mini-cicle : consult
     * @return A list of tuples with the locations that delimit the unnecessary streets
       */
    public String[][] unnecessaryStreets(){ 
        ArrayList<String> useless = new ArrayList<String>();
        for(String i: streets.keySet()){
            if(!usefulThings.contains(i)) useless.add(i);
        }
        Collections.sort(useless);
        String[][] matriz = new String[useless.size()][2];
        for(int i=0;i<useless.size();i++){
            matriz[i][0] = useless.get(i).split("-")[0];
            matriz[i][1] = useless.get(i).split("-")[1];
        }
        return matriz;
    }
    
    /* Make a minimum spanning tree to find the unnecesary streets*/
    private void mst(){
        double[][] edges = new double[locations.size()][locations.size()];        
        ArrayList<String> vertex = new ArrayList<String>(); 
        for(String i: locations.keySet()){vertex.add(i);}
        for(int i=0;i<edges.length;i++) {for(int j=0; j<edges.length;j++) {edges[i][j]= Double.MAX_VALUE;}}
        for(String i: streets.keySet()){
            int a = vertex.indexOf(streets.get(i).getLocation1()); int b = vertex.indexOf(streets.get(i).getLocation2());
            edges[a][b] = streets.get(i).getLength(); edges[b][a] = streets.get(i).getLength();
        }
        ArrayList<Integer> components = Graph.componentsFinder(locations.size(),vertex,edges);
        String[] father = Graph.mst(vertex, edges, components, locations.size());
        for(String i: streets.keySet()) { if(!(lastStreet==streets.get(i))) streets.get(i).changeColor("red-");}
        usefulThings.clear();
        for(int i=1; i<locations.size();i++){
            try{
                String identifier = vertex.get(i).compareTo(father[i])<0?(vertex.get(i)+"-"+father[i]):(father[i]+"-"+vertex.get(i));
                usefulThings.add(identifier);
                if(!(lastStreet==streets.get(identifier))) streets.get(identifier).changeColor("black");
            }
            catch(Exception e){}
        }
        if(isVisible) makeVisible();
    }
    
    /**
     * Gives the information of the wrong signals in the city ordered alphabetically and in lower case
     * 6th mini-cicle: Consult
     * @return the colors of the locations connected to the signal's street
     */
    public String[][] wrongSignals(){
        String[] input = new String[streets.keySet().size()]; int i = 0;
        ArrayList<String> auxLocations = new ArrayList<String>(locations.keySet());
        Collections.sort(auxLocations);
        for(String edge: streets.keySet()){
            String aString = edge.split("-")[0],bString = edge.split("-")[1];
            input[i] = auxLocations.indexOf(aString)+" "+auxLocations.indexOf(bString);
            i++;
        }
        HashMap<Integer, ArrayList<Integer>> graph = Graph.graphMaker(input);
        ArrayList<String> validSigns = new ArrayList<String>(Arrays.asList(Graph.solution(graph)));
        for(i=1; i<validSigns.size(); i++){
            String a = validSigns.get(i).split(" ")[0], b = validSigns.get(i).split(" ")[1], aux;
            aux = auxLocations.get(Integer.parseInt(a))+"-"+auxLocations.get(Integer.parseInt(b));
            validSigns.set(i,aux);
        }
        String[][] wrongSigns = new String[signs.size()-Integer.parseInt(validSigns.get(0))][2];
        i = 0;
        ArrayList<String> auxSigns = new ArrayList<String>(signs.keySet());
        Collections.sort(auxSigns);
        for(String s: auxSigns){
            if(!validSigns.contains(s)){wrongSigns[i][0]=s.split("-")[0]; wrongSigns[i][1]=s.split("-")[1];i++;}
        }
        return wrongSigns;
    }
    
    /**
    * Make visible the objects on the simulator
    * 7th mini-cicle : make visible / make invisible
    */
    public void makeVisible(){
        Canvas canvas = Canvas.getCanvas();
        for(String i: streets.keySet()) {streets.get(i).makeVisible(); if(slow) canvas.wait(90);}
        for(String i: signs.keySet()) {signs.get(i).makeVisible(); if(slow) canvas.wait(90);}
        for(String i: locations.keySet()) {locations.get(i).makeVisible(); if(slow) canvas.wait(90);}
        ok = true;
        isVisible = true;
    }
    
    /**
    * Make invisible the objects on the simulator
    * 7th mini-cicle : make visible / make invisible
    */
    public void makeInvisible(){
        for(String i: locations.keySet()) locations.get(i).makeInvisible();
        for(String i: streets.keySet()) streets.get(i).makeInvisible();
        for(String i: signs.keySet()) signs.get(i).makeInvisible();
        ok = true;
        isVisible = false;
    }
    
    /**
    * Exit the simutator
    * 8th mini-cicle : finish
    */
    public void finish(){
        ok = true;
        System.exit(0);
    }

    /**
      * Usability requirement 4: Raise an error
      * @param text dispay in the error window
      */
    private void raiseError(String text){
        if(isVisible){
            Toolkit.getDefaultToolkit().beep(); 
            JOptionPane.showMessageDialog(null, text,"Error",JOptionPane.ERROR_MESSAGE);
        }
        ok = false;
    }
    
    /*
     * Checks if the given location is overlaping with another one 
     */
    private boolean isCollisioning(Location a){
        boolean isCollisioning = false;
        for(Location i: locations.values()){
            if(a.getShape().getBounds().intersects(i.getShape().getBounds())){
                isCollisioning = true;
                break;
            }
        }
        return isCollisioning;
    }
    
    /*
     * Python print function implementation.
     * @params Anything
     */ 
    private <T> void print(T ...a){
        for(T i: a){
            System.out.print(""+i+" ");
        }System.out.println();
    }    
    
    /**
     * Return if the town is visible
     */
    public boolean isVisible(){
        return isVisible;
    }
}
