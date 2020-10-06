package town;


/**
 * A street that place all posible signs
 * 
 * @author Angie Medina - Jose Perez 
 * @version 4/10/2020
 */
public class Prudent extends Street{
    /**
     * Constructor for objects of class Silent
     */
    public Prudent(Location location1, Location location2){
        super(location1,location2);
    }
    
    @Override
    public Sign addSign(String signType, String identifier){
        Sign newSign;
        String reversedIdentifier = identifier.split("-")[1]+"-"+identifier.split("-")[0];
        newSign = super.addSign(signType,identifier);
        if(!super.containsSign(reversedIdentifier)) {
            newSign.changeColor();
            newSign = super.addSign(signType,reversedIdentifier);
        }
        return newSign;
    }
    
    @Override
    public String getType(){
        return "prudent";
    }
}
