package town;


/**
 * A location that inverts its position
 * 
 * @author Angie Medina - Jose Perez 
 * @version 4/10/2020
 */
public class Reverse extends Location
{
    /**
     * Constructor of the location given certain information
     * @param color written in RGBa format
     * @param x position on the x axis
     * @param y position on the y axis
     */
    public Reverse(String color, int x, int y){
        super(color,y,x);
    }
    
    @Override
    public void changeFrame(){
        frame.changeColor("green sheen");
    }
    
    @Override
    public String getType(){
        return "reverse";
    }
}
