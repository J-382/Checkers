package town;


/**
 * A street that place all posible signs
 * 
 * @author Angie Medina - Jose Perez 
 * @version 4/10/2020
 */
public class Prudent extends Street
{
    /**
     * Constructor for objects of class Silent
     */
    public Prudent(Location location1, Location location2)
    {
        super(location1,location2);
    }
    
    @Override
    public void addSign(String signType, String identifier){
        String auxIdentifier = identifier.split("-")[1]+"-"+identifier.split("-")[0];
        super.addSign(signType,identifier);
        if(!super.containsSign(auxIdentifier)) super.addSign(signType,auxIdentifier);
    }
    
    @Override
    public String getType(){
        return "prudent";
    }
}
