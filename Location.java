import java.util.Random;
import java.awt.*;
import java.util.PriorityQueue;
/**
 * Pretends to simulate a location
 * 
 * @author Angie Medina - Jose Perez
 * @version 27/08/2020
 */
public class Location
{
    // instance variables - replace the example below with your own
    private int x,y;
    private String color;
    private Circle location,frame;
    /**
     * Constructor of the location given certain information
     * @param color written in RGBa format
     * @param x position on the x axis
     * @param y position on the y axis
     */
    public Location(String color, int x, int y)
    {
        // initialise instance variables
        location = new Circle(x-10,y-10,color);
        frame = new Circle(x-12,y-12,"gold");
        frame.changeSize(24);
        this.color = color;
        this.x = x;
        this.y = y;
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
    
    public void changeFrame(){
        frame.changeColor("black");
    }
    
    public Shape getShape(){
        return frame.getShape();
    }
}
