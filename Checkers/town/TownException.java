package town;


/**
 * Exceptions that might raise in the Town class
 *
 * @author Angie Medina - Jose Perez
 * @version 04/10/20
 */
public class TownException extends Exception{
    public static final String WRONG_LOCATION_TYPE = "That type is not a valid Location type.";
    public static final String WRONG_STREET_TYPE = "That type is not a valid Street type.";
    public static final String WRONG_SIGN_TYPE = "That type is not a valid Sign type.";
    public static final String COLOR_UNAVAILABLE = "The entered color is not available.";
    public static final String EXISTING_LOCATION ="There's already a location with such color";
    public static final String LOCATION_COLLISION ="Can't place a new location here.";
    public static final String LOCATION_NOT_FOUND ="There isn't a location with such color.";
    public static final String EXISTING_STREET ="There's already an identical street.";
    public static final String LOCATION_NO_STREET ="That type of location can't have streets.";
    public TownException(String message){
        super(message);
    }
}
