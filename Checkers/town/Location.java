package town;

import Shapes.*;
import java.util.Random;
import java.awt.*;
import java.util.PriorityQueue;
/**
 * Pretends to simulate a location
 * 
 * @author Angie Medina - Jose Perez
 * @version 27/08/2020
 */
public class Location{
    protected int x,y;
    protected String color;
    protected Circle location,frame;
    protected int numberStreets;        
    /**
     * Constructor of the location given certain information
     * @param color written in RGBa format
     * @param x position on the x axis
     * @param y position on the y axis
     */
    public Location(String color, int x, int y){
        location = new Circle(x-10,y-10,color);
        frame = new Circle(x-12,y-12,"gold");
        frame.changeSize(24);
        this.color = color;
        this.x = x;
        this.y = y;
        numberStreets = 0;
        makeInvisible();
    }
    
    /**
     * Gives the location's position in the simulator
     * @return coord current location's coordenates
     */
    public int[] getLocation(){
        int[] coord = {x,y} ;
        return coord;
    }
    
    /**
     * Make the location invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        frame.makeInvisible();
        location.makeInvisible();
    }
    
    /**
     * Make the location visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        frame.makeVisible();
        location.makeVisible();
    }
    
    /**
     * Returns the location's color
     * @return A string with the RGB location's color
     */
    public String getColor(){
        return color;
    }
    
    /**
     * Changes the location's frame color to the normal color
      */
    public void changeFrame(){
        frame.changeColor("black");
    }
    
    /**
     * Changes the location's frame color to the initial color
      */
    public void changeFrameIni(){
        frame.changeColor("gold");
    }
    
    /**
     * Returns the Shape of the location's frame
     */
    public Shape getShape(){
        return frame.getShape();
    }
    
    /**
     * Returns if the street can have streets
     */
    public boolean canHaveStreets(){
        return true;
    }
    
    /**
     * Returns the type of the location
      */
    public String getType(){
        return "normal";
    }
    
    /**
     * Returns the type of the streets allowed by this location
      */
    public String getAllowedStreetType(){
        return "anyone";
    }
    
    public void addStreet(){
        numberStreets++;
    }
}
