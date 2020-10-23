package town;

import java.util.Random;

/**
 * A location that do whatever it want
 * 
 * @author Angie Medina - Jose Perez
 * @version 11/10/20 
 */
public class Locked extends Location
{
    private int maxNumberStreets;
    private String allowedStreetType;
    /**
     * Constructor of the location given certain information
     * @param color written in RGBa format
     * @param x position on the x axis
     * @param y position on the y axis
     */
    public Locked(String color, int x, int y){
        super(color,x,y);
        Random r = new Random();
        maxNumberStreets = r.nextInt(9) + 1;
        allowedStreetType = new String[] {"silent","prudent","normal"}[r.nextInt(3)];
    }
    
    @Override
    public String getType(){
        return "locked";
    }
    
    @Override
    public void changeFrame(){
        frame.changeColor("nice green");
    }
    
    @Override
    public boolean canHaveStreets(){
        return numberStreets<maxNumberStreets;
    }
    
    @Override
    public String getAllowedStreetType(){
        return allowedStreetType;
    }
    
    public int getMaxNumberStreets(){
        return maxNumberStreets;
    }
}
