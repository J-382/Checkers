package town;


/**
 * A sign that cannot be removed
 * 
 * @author Angie Medina - Jose Perez 
 * @version 4/10/2020
 */
public class Fixed extends Sign{
    /**
     * Constructor for objects of class Fixed
     */
    public Fixed(String name, Street street){
        super(name, street);
    }
    
    @Override
    public boolean remove(){
        return false;
    }
    
    @Override
    public boolean canBeRemoved(){
        return false;
    }
    
    /**
      * Changes the sign's color to the standard.
      */
    public void changeColor(){
        colorSign = "pearly purple";
        sign.changeColor(colorSign);        
    }
}
