import java.awt.*;
import java.awt.geom.*;
import java.math.*;

/**
 * Pretends to simulate a street
 * 
 * @author Angie Medina - Jose Perez
 * @version 27/08/2020
 * 
 */

public class Line{
    private int x1,x2,y1,y2;
    private String color;
    private boolean isVisible;
    public final double length;
    private boolean isTransparent;
    /**
     * Constructor of the street given certain information
     * @param location1 one of the location who's connected to the street
     * @param location2 one of the location who's connected to the street
     */
    public Line(int x1, int y1, int x2, int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        color = "0 0 0";
        isVisible = false;
        length = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    /**
     * Make this line visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make this line invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }

    /**
     * Change the color. 
     * @param newColor written in RGB code
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }
    
    public void makeTransparent(){
        isTransparent = true;
        draw();
    }
    
    /*
     * Draw the line with current specifications on screen.
     */
    private void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, new Line2D.Double(x1, y1, x2, y2));
        }
    }

    /*
     * Erase the line on screen.
     */
    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}
