package town;


/**
 * A class with all town exceptions
 * 
 * @author Angie Medina - Jose Perez
 * @version 04/10/2020
 */
public class TownException extends Exception{
    static public final String ADD_TO_SILENTSTREET = "A silent street can't have signs";
    static public final String DEL_FIXEDSIGN = "A fixed sign can't be deleted";
    static public final String ADD_TO_ISOLATEDLOCATION = "An isolated location can't have streets";
    static public final String INVALID_TYPE = "Invalid type of ";
    
    public static final String WRONG_LOCATION_TYPE = "That type is not a valid Location type.";
    public static final String WRONG_STREET_TYPE = "That type is not a valid Street type.";
    public static final String WRONG_SIGN_TYPE = "That type is not a valid Sign type.";
    public static final String COLOR_UNAVAILABLE = "The entered color is not available.";
    public static final String EXISTING_LOCATION ="There's already a location with such color";
    public static final String LOCATION_COLLISION ="Can't place a new location here.";
    public static final String LOCATION_NOT_FOUND ="There isn't a location with such color.";
    public static final String EXISTING_STREET ="There's already an identical street.";
    public static final String LOCATION_NO_STREET ="That location type can't have streets.";
    public static final String STREET_NOT_FOUND = "There isn't a street who connects those locations.";
    public static final String EXISTING_SIGN = "There's already an identical sign.";
    public static final String STREET_NO_SIGN = "That street type can't have signs.";
    /**
     * Genera una excepcion con el mensaje dado
       */
    public TownException(String message){
        super(message);
    }
}