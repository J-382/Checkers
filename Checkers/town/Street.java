package town;

import Shapes.*;
import java.awt.*;
import java.awt.geom.*;
import java.math.*;

/**
 * Pretends to simulate a street
 * 
 * @author Angie Medina - Jose Perez
 * @version 27/08/2020
 * 
 */

public class Street{
    private String color;
    private Location location1,location2;
    private Line street;
    /**
     * Constructor of the street given certain information
     * @param location1 one of the location who's connected to the street
     * @param location2 one of the location who's connected to the street
     */
    public Street(Location location1, Location location2){
        this.location1 = location1;
        this.location2 = location2;
        street = new Line(location1.getLocation()[0],location1.getLocation()[1],location2.getLocation()[0],location2.getLocation()[1]);
        color = "black";
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

    /**
     * Returns the first location's color
     * @return A string with the RGB first location's color
     */
    public String getLocation1(){
        return location1.getColor();
    }
       
    /**
     * Returns the second location's color
     * @return A string with the RGB second location's color
     */
    public String getLocation2(){
        return location2.getColor();
    }
    
    public double getLength(){
        return street.length;
    }
}
