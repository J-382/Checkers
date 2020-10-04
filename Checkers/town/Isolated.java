package town;


/**
 * Write a description of class Isolated here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Isolated extends Location{
    /**
     * Constructor for objects of class Isolated
     */
    public Isolated(String color, int x, int y){
        super(color, x, y);
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
