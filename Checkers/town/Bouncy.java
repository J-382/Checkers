package town;


/**
 * A sign that jump when is going to be removed
 * 
 * @author Angie Medina - Jose Perez
 * @version 04/10/2020
 */
public class Bouncy extends Sign{
    /**
     * Constructor for objects of class Fixed
     */
    public Bouncy(String name, Street street){
        super(name,street);
    }
    
    @Override
    public boolean remove(){
        String reverseName = super.getName().split("-")[1] + "-" + super.getName().split("-")[0];
        boolean bounce = false;
        super.makeInvisible();
        if(!street.containsSign(reverseName)) {
            street.addSign("bouncy",reverseName);
            bounce = true;
        }
        return !bounce;
    }
    
    @Override
    public String getType(){
        return "bouncy";
    }
    
    /**
      * Changes the sign's color to the standard.
      */
    public void changeColor(){
        colorSign = "green sheen";
        sign.changeColor(colorSign);        
    }
}
