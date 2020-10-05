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
        String aux = super.getName().split("-")[1] + "-" + super.getName().split("-")[0];
        boolean bounce = false;
        super.makeInvisible();
        if(!street.containsSign(aux)) {
            street.addSign("bouncy",aux);
            bounce = true;
        }
        return !bounce;
    }
    
    @Override
    public String getType(){
        return "bouncy";
    }
}
