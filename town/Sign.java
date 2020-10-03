package town;

import Shapes.*;
import java.util.*;
/**
 * Pretends to simulate a sign
 * 
 * @author Angie Medina - Jose Perez
 * @version 27/08/2020
 */
public class Sign
{
    // instance variables - replace the example below with your own
    private int x,y;
    private Rectangle square;
    private Circle sign,frame;
    private Location locationA,locationB;
    private String colorSign;

    /**
     * Constructor for objects of class Sign
     */
    public Sign(Location locationA, Location locationB)
    {
        // initialise instance variables
        int[] a,b;
        this.locationA = locationA;
        this.locationB = locationB;
        a = locationA.getLocation();
        b = locationB.getLocation();
        //
        double m = (double) (a[1]-b[1]);
        m /= (a[0]-b[0]);
        if(m==0){
            x = a[0] + (a[0]>b[0]?-17:17);
            y = a[1];
        }
        else if(m==Double.POSITIVE_INFINITY || m==Double.NEGATIVE_INFINITY){
            x = a[0];
            y = a[1] + (a[1]>b[1]?-17:17);
        }
        else{
            int aux;
            x = -1;
            if(Math.sqrt((a[1]-b[1])*(a[1]-b[1])+(a[0]-b[0])*(a[0]-b[0]))>19){
                for(int i=1; i<15;i++){
                    aux = (int) a[0]+(a[0]>b[0]?-i:i);
                    y = (int) (m*(aux-a[0])+a[1]);
                    if(Math.sqrt((a[1]-y)*(a[1]-y)+(a[0]-aux)*(a[0]-aux))>=19){
                        x=aux;
                        break;
                    }
                }
            }
            x = (int) x==-1?a[0]+(a[0]>b[0]?-17:17):x;
            y = (int) (m*(x-a[0])+a[1]);
        }
        //
        colorSign = "gold";
        square = new Rectangle(x-5,y-5);
        square.changeColor("zero");
        sign = new Circle(x-3,y-3,colorSign);
        frame = new Circle(x-4,y-4,"white");
        changeSize(10);
        makeInvisible();
    }
    
    /**
      * Makes the sign invisible. If it's invisible already, do nothing;
      */
    public void makeInvisible(){
        square.makeInvisible();
        frame.makeInvisible();
        sign.makeInvisible();
    }

    /**
      * Makes the sign visible. If it's visible already, do nothing;
      */    
    public void makeVisible(){
        square.makeVisible();
        frame.makeVisible();
        sign.makeVisible();
    }
    
    /**
     * Returns a tuple with the sign's coordinates.
     * @return A tuple with the sign's coordinates.
     */
    public int[] getCoord(){
        int[] coor = {x,y};
        return coor;
    }
    
    public void makeTransparent(){
        sign.changeColor(sign.getColor()+"-");
        frame.changeColor(frame.getColor()+"-");
        square.changeColor(square.getColor()+"-");
    }
    
    public void makeOpaque(){
        sign.changeColor(sign.getColor().replace("-",""));
        frame.changeColor(frame.getColor().replace("-",""));
        square.changeColor(square.getColor().replace("-",""));
    }
    
    /**
     * Change the sign's size to the new size
     * @param newSize the new height in pixels. newSize must be >=0.
     */
    public void changeSize(int newSize){
        sign.changeSize(newSize-4);
        frame.changeSize(newSize-2);
        square.changeSize(newSize,newSize);
    }
    
    /**
      * Changes the sign's color.
      */
    public void changeColor(){
        colorSign ="wine";
        sign.changeColor(colorSign);        
    }
    
    public ArrayList<Location> getLocations(){
        ArrayList<Location> locations =  new ArrayList<>();
        locations.add(locationA); locations.add(locationB);
        return locations;
    }
}
