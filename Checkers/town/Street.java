package town;

import Shapes.*;
import java.awt.*;
import java.awt.geom.*;
import java.math.*;
import java.util.*;

/**
 * Pretends to simulate a street
 * 
 * @author Angie Medina - Jose Perez
 * @version 4/10/2020
 */

public class Street{
    private String color;
    private Location location1,location2;
    private Line street;
    private HashMap<String,Sign> signs;
    /**
     * Constructor of the street given certain information
     * @param location1 one of the location who's connected to the street
     * @param location2 one of the location who's connected to the street
     */
    public Street(Location location1, Location location2){
        this.location1 = location1;
        this.location2 = location2;
        street = new Line(location1.getLocation()[0],location1.getLocation()[1],location2.getLocation()[0],location2.getLocation()[1]);
        signs = new HashMap<String,Sign>();
        color = "gold";
        changeColor("gold");
    }

    /**
     * Make this line visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        street.makeVisible();
    }
    
    /**
     * Make this line invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        street.makeInvisible();
    }

    /**
     * Change the color. 
     * @param newColor written in RGB code
     */
    public void changeColor(String newColor){
        color = newColor;
        street.changeColor(newColor);
    }
    
    public void changeColor(){
        street.changeColor("black");
    }
    
    /**
     * Returns the first location's color
     * @return A string with the RGB first location's color
     */
    public Location getLocation1(){
        return location1;
    }
       
    /**
     * Returns the second location's color
     * @return A string with the RGB second location's color
     */
    public Location getLocation2(){
        return location2;
    }
    
    /**
     * Returns the length of the street
     * @returns double with the length of the street
       */
    public double getLength(){
        return street.length;
    }
    
    /**
     * Returns the sign associated to the given key
     * @returns Sign sign associated to the given key
       */
    public Sign getSign(String key){
        return signs.get(key);
    }
    
    /**
     * Returns a boolean that indicates if the given key is associated with a signal of the street
     * @returns boolean indicates if the given key is asociated with a signal of the street
       */
    public boolean containsSign(String key){
        return signs.containsKey(key);
    }
    
    /**
     * Returns an ArrayList with the identifiers of the street's signs
     * @returns ArrayList with the identifiers of the street's signs
     */
    public ArrayList<String> signsKeys(){
        ArrayList<String> aux = new ArrayList<String>(signs.keySet());
        return aux;
    }
    
    /**
     * Add a new sign with the given specification
     * @param signType type of the new sign
     * @param identifier identifier of the new sign
     */
    public Sign addSign(String signType, String identifier){
        /*TownException Exc;
        if(signType.equals("normal")) signs.put(identifier,new Sign(identifier,this));
        else if(signType.equals("bouncy")) signs.put(identifier,new Bouncy(identifier,this));
        else if(signType.equals("fixed")) signs.put(identifier, new Fixed(identifier, this));
        else Exc = new TownException(TownException.INVALID_TYPE+"sign");*/
        Sign lastSign;
        if(signType.equals("fixed")) lastSign =  new Fixed(identifier, this);
        else if(signType.equals("bouncy")) lastSign = new Bouncy(identifier, this);
        else{lastSign = new Sign(identifier, this);}
        lastSign.changeColorIni();
        signs.put(identifier, lastSign);
        return lastSign;
    }
    
    /**
     * Delete the sign associated with the given identifier
     * @param identifier A string with the identifier associated with the sign to remove
     * @returns boolean indicates if the sign was deleted
     */
    public boolean removeSign(String identifier){
        boolean removed;
        removed = signs.get(identifier).remove();
        if(removed) signs.remove(identifier);
        else if(signs.get(identifier).getType().equals("bouncy")) signs.remove(identifier);
        return removed;
    }
    
    /**
     * Return if the street can have signs
     * @return true if the street can have signs
     *          false otherwise
     */
    public boolean canHaveSigns(){
        return true;
    }
    
    /**
     * Returns the street's type
     * @return String contains the street's type
       */
    public String getType(){
        return "normal";
    }
}
