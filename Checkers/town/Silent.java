package town;


/**
 * A street that don't allow sings to be added to it
 * 
 * @author Angie Medina - Jose Perez
 * @version 4/10/2020
 */
public class Silent extends Street
{
    /**
     * Constructor for objects of class Silent
     */
    public Silent(Location location1, Location location2)
    {
        super(location1,location2);
    }
    
    @Override
    public boolean canHaveSigns(){
        return false;
    }
    
    @Override
    public String getType(){
        return "silent";
    }
     
    public void changeColor(){
        super.changeColor("pearly purple");
    }
}
