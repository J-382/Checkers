package shapes;

import java.awt.*;
import java.awt.geom.*;
import java.math.*;

/**
 * A triangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author Angie Medina - Jose Perez
 * @version 27/08/2020
 * 
 */  

public class Line extends Figure{
    private int x1,x2,y1,y2;
    public final double length;
    private boolean isTransparent;
    /**
     * Constructor of the line given certain information
     * @param x1 the first position in the x axis
     * @param y1 the first position in the y axis
     * @param x2 the second position in the x axis
     * @param y2 the second position in the y axis
     */
    public Line(int x1, int y1, int x2, int y2){
        super((x1 + x2)/2, (y1 + y2)/2, "0 0 0", new Line2D.Double(x1, y1, x2, y2));
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        //color = "0 0 0";
        //isVisible = false;
        length = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }
    
    /**
     * Makes the line transparent
     */
    public void makeTransparent(){
        isTransparent = true;
        draw();
    }
}
