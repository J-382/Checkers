package town;


/**
 * A location that inverts its positions
 * 
 * @author Angie Medina - Jose Perez
 * @version 04/10/20
 */
public class Reverse extends Location{
    /**
     * Constructor for objects of class Reverse
     */
    public Reverse(String color, int x, int y){
        super(color, y, x);
    }
    
    @Override
    public void changeFrame(){
        frame.changeColor("green sheen");
    }
}
