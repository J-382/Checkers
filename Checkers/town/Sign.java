package town;

import Shapes.*;
import java.util.*;
/**
 * Pretends to simulate a sign
 * 
 * @author Angie Medina - Jose Perez
 * @version 27/08/2020
 */
public class Sign{
    private int x,y;
    private Rectangle square;
    private Circle sign,frame;
    private String colorSign;
    protected final Street street;
    private String name;
    /**
     * Constructor for objects of class Sign
     */
    
    public Sign(String name, Street street){
        this.name = name;
        int[] a,b;
        if(name.split("-")[0].equals(street.getLocation1().getColor())){
            a = street.getLocation1().getLocation();
            b = street.getLocation2().getLocation();
        }
        else {
            b = street.getLocation1().getLocation();
            a = street.getLocation2().getLocation();
        }
        this.street = street;
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
        colorSign = "wine";
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
    
    /**
     * Makes the sign transparent
     */
    public void makeTransparent(){
        sign.changeColor(sign.getColor()+"-");
        frame.changeColor(frame.getColor()+"-");
        square.changeColor(square.getColor()+"-");
    }
    
    /**
     * Makes the sign opaque
     */
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
      * Changes the sign's color to the standard.
      */
    public void changeColor(){
        colorSign = "wine";
        sign.changeColor(colorSign);        
    }
    
    /**
     * Changes the sign's color to the inicial color.
     */
    public void changeColorIni(){
        colorSign = "gold";
        sign.changeColor(colorSign);
    }
    
    /**
     * Remove the sign for the town
     * @returns boolean indicates if the operation was successful
     */
    public boolean remove(){
        makeInvisible();
        return true;
    }
    
    /**
     * Returns the sign's identifier
     * @returns String contains the sign's identifier
     */
    public String getName(){
        return name;
    }
    
    /**
     * Returns if the sign can be removed
     */
    public boolean canBeRemoved(){
        return true;
    }
    
    /**
     * Returns the sign's type
     * @returns String contains the sign's type
     */
    public String getType(){
        return "normal";
    }
}
