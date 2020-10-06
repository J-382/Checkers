package town;


/**
 * A location that don't allow streets to be added to it
 * 
 * @author Angie Medina - Jose Perez
 * @version 05/10/20 
 */
public class Isolated extends Location
{
    /**
     * Constructor of the location given certain information
     * @param color written in RGBa format
     * @param x position on the x axis
     * @param y position on the y axis
     */
    public Isolated(String color, int x, int y){
        super(color,x,y);
    }
    
    @Override
    public String getType(){
        return "isolated";
    }
    
    @Override
    public void changeFrame(){
        frame.changeColor("pearly purple");
    }
    
    @Override
    public boolean canHaveStreets(){
        return false;
    }
}
