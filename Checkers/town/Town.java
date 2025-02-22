package town;

import shapes.*;
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
* @version 11/10/2020
*/
public class Town{   
    private int height, width,actionsIterator;
    private HashMap<String,Location> locations;
    private HashMap<String,Street> streets;
    private ArrayList<String> usefulThings, deadEnd;
    private boolean ok, isVisible,unReAction, slow, simulating;
    private Location lastLocation;
    private Street lastStreet;
    private String[] lastSign;
    private String lastElementType;
    private ArrayList<String> actions;
    private static final ArrayList<String> validLocations = new ArrayList<String>(Arrays.asList("normal", "isolated", "reverse", "locked"));
    private static final ArrayList<String> validStreets = new ArrayList<String>(Arrays.asList("normal", "silent", "prudent"));
    private static final ArrayList<String> validSigns = new  ArrayList<String>(Arrays.asList("normal", "bouncy", "fixed"));
    /** 
     * Constructor of the town given its length and width
     * 1st mini cicle: Create
     * @param height of the town in pixels
     * @param width of the town in pixels
     * @param slow speed of simulation
     */
    public Town(int height, int width, boolean slow){
        this.height = height;
        this.width = width;
        Canvas canvas = Canvas.getCanvas(height,width);
        locations = new HashMap<String,Location>();
        streets = new HashMap<String,Street>();
        usefulThings = new ArrayList<String>();
        deadEnd = new ArrayList<String>();       
        isVisible = false;
        actions = new ArrayList<String>(); 
        actionsIterator = -1;
        unReAction = true;
        this.slow = slow;
        lastElementType = "null";
        simulating = false;
        ok = true;
    }
    
    /**
     * Constructor of the town given its length and width
     * 1st mini cicle: Create
     * @param height of the town in pixels
     * @param width of the town in pixels
     */
    public Town(int height, int width){
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
     * @throws TownException INVALID_NUMBER_STREETS,  if is imposible to create that number of streets with the given locations
     *                       INVALID_NUMBER_DEADENDS, if is imposible to create that number of signs with the given streets  
     *                       WRONG_LOCATION_TYPE,     if the location's type is not valid
     *                       WRONG_STREET_TYPE,       if the street's type is not valid
     *                       WRONG_SIGN_TYPE,         if the sign's type is not valid
     */
    public Town(int height, int width, int numberLocations, int numberStreets, int numberDeadEnds, boolean slow) throws TownException{
        this(height,width,slow);
        String[] colors = Canvas.colorsList();
        Arrays.sort(colors);
        String [] typeLocations = new String[numberLocations];
        String [] typeStreets = new String[numberStreets];
        String [] typeSigns = new String[numberDeadEnds];
        for(int i = 0; i < numberLocations; i++) typeLocations[i] = "normal";
        for(int i = 0; i < numberStreets; i++) typeStreets[i] = "normal";
        for(int i = 0; i < numberDeadEnds; i++) typeSigns[i] = "normal";
        randomElements(typeLocations, typeStreets, typeSigns, colors);
    }
    
    /**
     * Constructor of a town given some specification
     * 1st mini cicle: Create
     * @param the specification corresponds to the input of the icp problem
     * @param boolean that specifics the speed of the simulation
     */
    public Town(String[] input, boolean slow){
        this(700,700,slow);
        if(slow) makeVisible();
        simulating = true;
        String[] instructions = input;
        int l = Integer.parseInt(instructions[0].split(" ")[0]), s = Integer.parseInt(instructions[0].split(" ")[1]);
        Random r = new Random();
        String[] colorsList = Canvas.colorsList();
        Arrays.sort(colorsList);
        for(int i = 0 ; i < l; i++){
            boolean isAValidLocation = false;
            int n = 0;
            while(!isAValidLocation){
                addLocation(colorsList[i],r.nextInt(660)+20,r.nextInt(660)+20);
                isAValidLocation = ok();
            }
        }
        
        for(int i = 1; i <= s; i++){
            int a = Integer.parseInt(instructions[i].split(" ")[0]), b = Integer.parseInt(instructions[i].split(" ")[1]);
            addStreet(colorsList[a-1],colorsList[b-1]);
        }
    }
    
    /**
     * Constructor of a town given some specification
     * 1st mini cicle: Create
     * @param the specification corresponds to the input of the icp problem
     */
    public Town(String[] input){
        this(input,false);
    }    
    
    /**
     * Constructor of a random town given some features
     * 1st mini cicle: Create
     * @param height of the town in pixels
     * @param width of the town in pixels
     * @param typeLocations the types of the locations (must create those randomly)
     * @param typeStreet the types of the streets (must create thouse randomly)
     * @param typeSign the types of the signs (must create thouse randomly)
     * @throws TownException INVALID_NUMBER_STREETS,  if is imposible to create that number of streets with the given locations
     *                       INVALID_NUMBER_DEADENDS, if is imposible to create that number of signs with the given streets  
     *                       WRONG_LOCATION_TYPE,     if the location's type is not valid
     *                       WRONG_STREET_TYPE,       if the street's type is not valid
     *                       WRONG_SIGN_TYPE,         if the sign's type is not valid
     */
    public Town(int height, int width, String[] typeLocations, String[] typeStreets, String[] typeSigns) throws TownException{
        this(height,width,false);
        String[] colors = Canvas.colorsList();
        Arrays.sort(colors);
        randomElements(typeLocations, typeStreets, typeSigns, colors);
    }
    
    /*
     * Creates elements randomly
     * @param typeLocations the types of the locations (must create those randomly)
     * @param typeStreet the types of the streets (must create thouse randomly)
     * @param typeSign the types of the signs (must create thouse randomly)
     * @param colors the valid colors
     * @throws TownException INVALID_NUMBER_STREETS,  if is imposible to create that number of streets with the given locations
     *                       INVALID_NUMBER_DEADENDS, if is imposible to create that number of signs with the given streets  
     *                       WRONG_LOCATION_TYPE,     if the location's type is not valid
     *                       WRONG_STREET_TYPE,       if the street's type is not valid
     *                       WRONG_SIGN_TYPE,         if the sign's type is not valid
     */
    private void randomElements(String[] typeLocations, String[] typeStreets, String[] typeSigns, String[] colors) throws TownException{
        int maxConnectionsNumber = 1, contSigns = 0;
        ArrayList<String> locationStreets = new ArrayList<String>();
        try{
            locationStreets = randomLocation(typeLocations, colors);
        } catch(TownException e){throw e;}
        for(int i = 2; i < locationStreets.size(); i++) maxConnectionsNumber *= i;
        if(locationStreets.size() == 1) maxConnectionsNumber = 0;
        ok = false;
        if(maxConnectionsNumber < typeStreets.length) throw new TownException(TownException.INVALID_NUMBER_STREETS);
        ArrayList<String> streetSigns = new ArrayList<String>();
        try{  
            streetSigns = randomStreet(locationStreets, typeStreets, colors);
        }
        catch(TownException e){throw e;}
        for (String identifier : streetSigns){
            if (!streets.get(identifier).canAddMore()) contSigns += 1;
            else{contSigns += 2;}
        }
        if(typeSigns.length > contSigns) throw new TownException(TownException.INVALID_NUMBER_DEADENDS);
        try{  
            randomSigns(streetSigns, typeSigns);
        }
        catch(TownException e){throw e;}
        ok = true;
    }
    
    /*
     * Creates locations randomly
     * @param typeLocations the types of the locations
     * @param colors the valid colors
     * @throws TownException WRONG_LOCATION_TYPE,if the location's type is not valid
     * @return locationStreets the colors of the locations that can have streets
     */
    private ArrayList<String> randomLocation(String[] typeLocations, String[] colors) throws TownException{
        ArrayList<String> locationStreets = new ArrayList<>();
        Random r = new Random();
        for(int i = 0; i < typeLocations.length; i++){
            if(!validLocations.contains(typeLocations[i].toLowerCase())) {
                throw new TownException(TownException.WRONG_LOCATION_TYPE);
            }
            boolean isAValidLocation = false;
            while(!isAValidLocation){
                addLocation(typeLocations[i], colors[i], r.nextInt(height - 20), r.nextInt(width - 20)); 
                isAValidLocation = ok();
            }
            if (locations.get(colors[i]).canHaveStreets()) {
                locationStreets.add(colors[i]);
            }
        }
        return locationStreets;
    }
    
    /*
     * Creates streets randomly
     * @param locationStreets the colors of the locations that can have streets
     * @param typeStreets the types of the streets
     * @param colors the valid colors
     * @throws TownException WRONG_STREET_TYPE, if the street's type is not valid
     * @return streetSigns the color of the streets that can have signs
     */
    private ArrayList<String> randomStreet(ArrayList<String> locationStreets, String[] typeStreets, String[] colors)throws TownException{
        ArrayList<String> streetSigns = new ArrayList<String>();
        Random r = new Random();
        for(int i = 0; i < typeStreets.length; i++){
            if(!validStreets.contains(typeStreets[i])) {
                throw new TownException(TownException.WRONG_STREET_TYPE);
            }
            boolean isAValidStreet = false;
            int a = 0, b = 0;
            while(!isAValidStreet){
                a = r.nextInt(locationStreets.size()); b = r.nextInt(locationStreets.size());
                if(a == b) b = b < locationStreets.size() - 1 ? b + 1 : b - 1;
                addStreet(typeStreets[i], locationStreets.get(a),locationStreets.get(b)); isAValidStreet = ok();
            }
            String locationA = locationStreets.get(a), locationB = locationStreets.get(b);
            String identifier = locationA.compareTo(locationB) < 0 ? (locationA + "-" + locationB):(locationB + "-" + locationA);
            if (streets.get(identifier).canHaveSigns()) {
                streetSigns.add(identifier);
            }
        }
        return streetSigns;
    }
    
    /*
     * Creates signs randomly
     * @param streetSigns the identifier of the streets that can have signs
     * @param typeSigns the types of the signs
     * @throws TownException WRONG_SIGN_TYPE, if the sign's type is not valid
     */
    private void randomSigns(ArrayList<String> streetSigns, String[] typeSigns) throws TownException{
        Random r = new Random();
        for(int i = 0; i < typeSigns.length; i++){
            if(!validSigns.contains(typeSigns[i])) {
                throw new TownException(TownException.WRONG_SIGN_TYPE);
            }
            if (streets.size() > 0){
                int a = r.nextInt(streetSigns.size());
                addSign(streetSigns.get(a).split("-")[0], streetSigns.get(a).split("-")[1]);
                if(!ok) addSign(streetSigns.get(a).split("-")[1], streetSigns.get(a).split("-")[0]);
            }
        }
    }
    
    /*
     * Checks if the wanted location can be added to the town, if thats the case creates the location desired.
     * 2nd mini cicle: add / delete location
     * @param color is written in RGBa format
     * @param x is the position on x axis
     * @param y is the position on y axis
     * @param type valid types: normal, reverse, isolate
     * @throws TownException COLOR_UNAVAILABLE,   if the color is not in the color list provided by the town;
     *                       EXISTING_LOCATION,   if the color is already used by other location;
     *                       WRONG_LOCATION_TYPE, if the location's type is not valid;
     *                       LOCATION_COLLISION,  if the location's positions are already occupied
     */
    private void checkLocation(String color, int x, int y, String type) throws TownException{
       if(!Arrays.asList(Canvas.colorsList()).contains(color)) {
           throw new TownException(TownException.COLOR_UNAVAILABLE);
       }
       if(locations.containsKey(color)){
           throw new TownException(TownException.EXISTING_LOCATION);
       }
       if(!(validLocations.contains(type))) {
           throw new TownException(TownException.WRONG_LOCATION_TYPE);
       }
       if(lastLocation != null) {
           lastLocation.changeFrame();
       }
       lastLocation = Town.createLocation(color,x,y,type);
       if(isCollisioning(lastLocation)){
           delLocation(lastLocation.getColor());
           throw new TownException(TownException.LOCATION_COLLISION);
       }
    }
    
    /**
     * Creates a new location with the given params
     * @param color is written in RGBa format
     * @param x is the position on x axis
     * @param y is the position on y axis
     * @param type valid types: normal, reverse, isolate
       */
    public static Location createLocation(String color, int x, int y, String type){
       Location location = null;
       if (type.equals("reverse")) {
           location = new Reverse(color, x, y);
       }
       else if (type.equals("isolated")) {
           location = new Isolated(color, x, y);
       }
       else if (type.equals("locked")) {
           location = new Locked(color, x, y);
       }
       else{
           location = new Location(color, x, y);
       }
       return location;
    }
    
    /**
     * 
     * Addicionate one location given certain information
     * 2nd mini cicle: add / delete location
     * @param color is written in RGBa format
     * @param x is the position on x axis
     * @param y is the position on y axis
     */
    public void addLocation(String color, int x, int y){
        addLocation("normal",color,x,y);
    }
    
    /**
     * Addicionate one location given certain information
     * 2nd mini cicle: add / delete location
     * @param color is written in RGBa format
     * @param x is the position on x axis
     * @param y is the position on y axis
     * @param type Type of the new location
     */
    public void addLocation(String type, String color, int x, int y){
        color = color.toLowerCase();
        type = type.toLowerCase();
        try{
            checkLocation(color, x, y, type);
            locations.put(color, lastLocation);
            ok = true;
            visibleAction("undo add location ", "location");
            lastElementType = "location";
            if(isVisible) makeVisible();
        }
        catch(TownException e){
            ok = false;
            raiseError(e.getMessage());
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
        boolean changeLastLocation = false;
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
     * Checks if the wanted street can be added to the town, if thats the case creates the street desired.
     * 3rd mini cicle: add / delete street
     * @param locationA
     * @param type valid types: normal, silent, prudent
     * @throws TownException LOCATION_NOT_FOUND, if there isn't a location with such color;
     *                       EXISTING_STREET,    if the street is already added in the town;
     *                       WRONG_STREET_TYPE,  if the street's type is not valid;
     *                       LOCATION_NO_STREET, if one of the locations can't have streets
     */
    private void checkStreet(String identifier, String locationA, String locationB, String type) throws TownException{
       if(!(locations.containsKey(locationA) && locations.containsKey(locationB))){
           throw new TownException(TownException.LOCATION_NOT_FOUND);
       }
       if(streets.containsKey(identifier)){
           throw new TownException(TownException.EXISTING_STREET);
       }
       if(!(validStreets.contains(type))) {
           throw new TownException(TownException.WRONG_STREET_TYPE);
       }
       Location lA = locations.get(locationA), lB = locations.get(locationB);
       if (!lA.canHaveStreets() || !lB.canHaveStreets()) {
           throw new TownException(TownException.LOCATION_NO_STREET);
       }
       if (!lA.getAllowedStreetType().equals("anyone") && !lA.getAllowedStreetType().equals(type)){
           throw new TownException(TownException.WRONG_STREET_TYPE_SIGN);
       }
       if (!lB.getAllowedStreetType().equals("anyone") && !lB.getAllowedStreetType().equals(type)){
           throw new TownException(TownException.WRONG_STREET_TYPE_SIGN);
       }
       lA.addStreet();lB.addStreet();
       if(lastStreet != null) lastStreet.changeColor("black");
       lastStreet = Town.createStreet(type,lA,lB);
    }
    
    /**
     * @param locationA first location's identifier
     * @param locationB second location's identifier
     * @param type valid types: normal, silent, prudent
     */
    public static Street createStreet(String type, Location lA, Location lB){
        Street street = null;
        if (type.equals("prudent")) {
            street = new Prudent(lA, lB);
        }
        else if (type.equals("silent")) {
            street = new Silent(lA, lB);
        }
        else{
            street = new Street(lA, lB);
        }
        return street;
    }
    
    /**
     * Addicionate one street given certain infomation
     * 3rd mini cicle: add / delete street
     * @param locationA the color of one linked location
     * @param locationB the color of one linked location
     * @param type Type of the new location
     */
    public void addStreet(String locationA, String locationB){
        addStreet("normal",locationA,locationB);
    }
    
    /**
     * Addicionate one street given certain infomation
     * 3rd mini cicle: add / delete street
     * @param locationA the color of one linked location
     * @param locationB the color of one linked location
     * @param type Type of the new street
     */
    public void addStreet(String type, String locationA, String locationB){
        locationA = locationA.toLowerCase(); locationB = locationB.toLowerCase();
        String identifier = locationA.compareTo(locationB) < 0 ? (locationA + "-" + locationB):(locationB + "-" + locationA);
        type = type.toLowerCase();
        try{
            checkStreet(identifier, locationA, locationB, type);
            streets.put(identifier, lastStreet);
            lastElementType = "street";
            mst();
            if(isVisible) makeVisible();
            visibleAction("undo add street ", "street");
            ok = true;
        }
        catch(TownException e){
            ok = false;
            raiseError(e.getMessage());
        }   
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
        String identifier = locationA.compareTo(locationB) < 0 ? (locationA+"-"+locationB):(locationB+"-"+locationA);
        ok = true;
        boolean canDel = true;
        if(streets.containsKey(identifier)){
            Street street = streets.get(identifier);
            for(String i: street.signsKeys()) if(!street.getSign(i).canBeRemoved()) canDel = false;
            if(canDel){
                for(String i: street.signsKeys()){
                    delSign(i.split("-")[0],i.split("-")[1]);
                }
                streets.get(identifier).makeInvisible();
                streets.remove(identifier);
            }else ok = false;
        }
        else ok = false;
        if(ok){visibleAction("undo del street ", "street");} 
    }
    
    /**
     * Deletes a excess street
     * 3rd mini cicle: add / delete street
     */
    public void delStreet(){
        String aux = "";
        double max = 0;
        for(String i: streets.keySet()){
            String auxIdentifier = i.split("-")[1] + "-" + i.split("-")[0];
            if(!usefulThings.contains(i) && streets.get(i).getLength()>max && !streets.get(i).containsSign(i) && !streets.get(i).containsSign(auxIdentifier)){
                aux = i; 
                max = streets.get(i).getLength();
            }
        }
        if(aux != "") delStreet(aux.split("-")[0],aux.split("-")[1]);
        ok = true;
    }
   
    /**
     * Checks if the wanted street can be added to the town, if thats the case creates the street desired.
     * 3rd mini cicle: add / delete street
     * @param locationA
     * @param type valid types: normal, fixed, bouncy
     * @throws TownException LOCATION_NOT_FOUND, if there isn't a location with such color;
     *                       STREET_NOT_FOUND,   if there isn't a street where to put it;
     *                       EXISTING_SIGN,      if the sign is already added in the town;
     *                       WRONG_SIGN_TYPE,    if the signs's type is not valid;
     */
    private void checkSign(String streetIdentifier,String signIdentifier, String locationA, String locationB, String type) throws TownException{
       if(!(locations.containsKey(locationA) && locations.containsKey(locationB))){
           throw new TownException(TownException.LOCATION_NOT_FOUND);
       }
       if(!streets.containsKey(streetIdentifier)) {
           throw new TownException(TownException.STREET_NOT_FOUND);
       }
       if(streets.get(streetIdentifier).containsSign(signIdentifier)){
           throw new TownException(TownException.EXISTING_SIGN);
       }
       if(!streets.get(streetIdentifier).canHaveSigns()) {
           throw new TownException(TownException.STREET_NO_SIGN);
       }
       if(!(validSigns.contains(type))) {
           throw new TownException(TownException.WRONG_SIGN_TYPE);
       }
       
       if(lastSign != null) {
           try{
               streets.get(lastSign[0]).getSign(lastSign[1]).changeColor();
           }catch (Exception e){}
       }
       lastSign = new String[]{streetIdentifier,signIdentifier};
       streets.get(streetIdentifier).addSign(type, signIdentifier);
       if(!streets.get(streetIdentifier).canAddMore()){
           String aux = signIdentifier.split("-")[1] + "-" + signIdentifier.split("-")[0];
           if(streets.get(streetIdentifier).signsKeys().contains(aux)){
               lastSign = new String[]{streetIdentifier,aux};
               visibleAction("undo add street ", "street");
           }
       } 
    }
    
    /**
     * Addicionate one sign given certain infomation
     * 4th mini cicle: add / delete sign
     * @param locationA the color of one linked location
     * @param locationB the color of one linked location
     */
    public void addSign(String locationA, String locationB){
        addSign("normal",locationA,locationB);
    }
    
    /**
     * Addicionate one sign given certain infomation
     * 4th mini cicle: add / delete sign
     * @param locationA the color of one linked location
     * @param locationB the color of one linked location
     * @param type Type of the new sign
     */
    public void addSign(String type, String locationA, String locationB){
        locationA = locationA.toLowerCase(); locationB = locationB.toLowerCase();
        String streetIdentifier = locationA.compareTo(locationB) < 0 ? (locationA + "-" + locationB):(locationB + "-" + locationA);
        String signIdentifier = locationA + "-" + locationB;
        try{
            checkSign(streetIdentifier, signIdentifier, locationA, locationB, type);
            ok = true;
            lastElementType = "sign";
            if(isVisible) makeVisible();
            visibleAction("undo add sign ", "sign");
        } catch(TownException e){
            ok = false;
            raiseError(e.getMessage());
        }
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
        String streetIdentifier = locationA.compareTo(locationB)<0?(locationA+"-"+locationB):(locationB+"-"+locationA);
        String signIdentifier = locationA+"-"+locationB;
        ok = true;
        if(streets.containsKey(streetIdentifier)){
            if(streets.get(streetIdentifier).containsSign(signIdentifier)){
                if (!streets.get(streetIdentifier).removeSign(signIdentifier)){ 
                    ok = false;
                    if(isVisible) {
                        boolean aux = slow; slow = false;
                        makeVisible(); slow = aux;
                    }
                }
            } else ok = false;
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
    private void undoRedoActions(String[] action, String ur){
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
        ArrayList<String> aux = new ArrayList<String>();
        for(Street i: streets.values()){
            for(String j: i.signsKeys()){
                aux.add(j);
            }
        }
        Collections.sort(aux);
        String[][] matriz = new String[aux.size()][2];
        for(int i = 0; i < aux.size(); i++){
            matriz[i][0] = aux.get(i).split("-")[0];
            matriz[i][1] = aux.get(i).split("-")[1];
        }
        return matriz;
    }
    
    /**
     * Returns a list with the streets that are not necessary to move around the town 
     * 6th mini-cicle : consult
     * @return A list of tuples with the locations that delimit the unnecessary streets
       */
    private String[][] unnecessaryStreets(){ 
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
        HashMap<Integer, ArrayList<Integer>> graphMap = Graph.graphMaker(input);
        Graph graph = new Graph(graphMap);
        ArrayList<String> validSigns = new ArrayList<String>(Arrays.asList(graph.solution()));
        for(i=1; i<validSigns.size(); i++){
            String a = validSigns.get(i).split(" ")[0], b = validSigns.get(i).split(" ")[1], aux;
            aux = auxLocations.get(Integer.parseInt(a))+"-"+auxLocations.get(Integer.parseInt(b));
            validSigns.set(i,aux);
        }
        ArrayList<String> auxSigns = new ArrayList<String>();
        for(Street s: streets.values()){
            for(String j: s.signsKeys()) auxSigns.add(j);
        }
        String[][] wrongSigns = new String[auxSigns.size()-Integer.parseInt(validSigns.get(0))][2];
        i = 0;
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
        if(slow){
            for(Street i: streets.values()){
                if(!lastElementType.equals("street") || !i.equals(lastStreet)) i.makeVisible();
                for(String j: i.signsKeys()) if(!lastElementType.equals("sign") || !j.equals(lastSign[1])) i.getSign(j).makeVisible();
            }
            for(Location i: locations.values()) if(!lastElementType.equals("location") || !i.equals(lastLocation)) {i.makeVisible();}
            canvas.wait(1500);
            if(lastElementType.equals("location")) lastLocation.makeVisible();
            else if(lastElementType.equals("street")) lastStreet.makeVisible();
            else if(lastElementType.equals("sign")) streets.get(lastSign[0]).getSign(lastSign[1]).makeVisible();
            slow = false;
            makeVisible();
            slow = true;
        }else{
            for(Street i: streets.values()){
                i.makeVisible();
                for(String j: i.signsKeys()) i.getSign(j).makeVisible();
            }
            for(Location i: locations.values())  i.makeVisible();
        }
        ok = true;
        isVisible = true;
    }
    
    /**
    * Make invisible the objects on the simulator
    * 7th mini-cicle : make visible / make invisible
    */
    public void makeInvisible(){
        for(Location i: locations.values()) i.makeInvisible();
        for(Street i: streets.values()){
            i.makeInvisible();
            for(String j: i.signsKeys()) i.getSign(j).makeInvisible();
        }
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
     * Return if the town is visible
     */
    public boolean isVisible(){
        return isVisible;
    }
    
    public Location getLastLocation(){
        return lastLocation;
    }
    
    /*
      * Usability requirement 4: Raise an error
      * @param text dispay in the error window
      */
    private void raiseError(String text){
        if(isVisible){
            if(!simulating){
                Toolkit.getDefaultToolkit().beep(); 
                JOptionPane.showMessageDialog(null, text,"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        ok = false;
    }
    
    /*
     * Checks if the given location is overlaping with another one 
     * @param a the another location
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
     * records all all visible actions performed in the simulator   
     * @param message the last visible action that the simulator did
     * @param object the type of element of that action
     */
    private void visibleAction(String message, String object){
        String key = "";
        if (object.equals("location")){
            int[] coord = lastLocation.getLocation();
            key = lastLocation.getColor() + " " + coord[0] + " " + coord[1];
        }
        else if(object.equals("street")){
            key = lastStreet.getLocation1().getColor() + " " + lastStreet.getLocation2().getColor();
        }
        else if(object.equals("sign")){
            key = streets.get(lastSign[0]).getLocation1().getColor() + " " + streets.get(lastSign[0]).getLocation2().getColor();
        }
        
        if(ok){
            if(actionsIterator<actions.size() - 1 && !unReAction) {
                actions.set(actionsIterator,message + key);
                for(int i = actionsIterator + 1; i < actions.size(); i++) actions.remove(i);
            }
            else{
                actions.add(message + key);actionsIterator++;
            }
        }    
    }
    
    /* Make a minimum spanning tree to find the unnecesary streets
     * mst uses a prim algoritms to make the minimun spanning tree
     */
    private void mst(){
        double[][] edges = new double[locations.size()][locations.size()];        
        ArrayList<String> vertex = new ArrayList<String>(); 
        for(String i: locations.keySet()){vertex.add(i);}
        for(int i=0;i<edges.length;i++) {for(int j=0; j<edges.length;j++) {edges[i][j]= Double.MAX_VALUE;}}
        for(String i: streets.keySet()){
            int a = vertex.indexOf(streets.get(i).getLocation1().getColor()); int b = vertex.indexOf(streets.get(i).getLocation2().getColor());
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
                if(!(lastStreet==streets.get(identifier))) streets.get(identifier).changeColor();
            }
            catch(Exception e){}
        }
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
    
    public void test(){
        addLocation("blue",200,100);
        addLocation("red",100,100);
        addLocation("green",100,200); 
        addStreet("blue","red");
        addStreet("red","green");
        addStreet("blue","green");
        addSign("blue","red");
        addSign("bouncy","red","green");
        addSign("fixed","blue","green");
        addSign("red","blue");
    } 
}


