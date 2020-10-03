package Shapes;

import java.awt.*;
import java.awt.geom.*;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0.  (15 July 2000) 
 */

public class Circle extends Figure{

    public static double PI=3.1416;
    
    private int diameter;
    private Ellipse2D circle;
    
    /**
     * Create a new circle at default position with default color.
     */
    public Circle(int x, int y, String color){
        super(x, y, color);
        diameter = 20;
    }

    /**
     * Change the size.
     * @param newDiameter the new size (in pixels). Size must be >=0.
     */
    public void changeSize(int newDiameter){
        erase();
        diameter = newDiameter;
        draw();
    }
    
    /*
     * Draw the circle with current specifications on screen.
     */
    public void draw(){
        circle = new Ellipse2D.Double(xPosition, yPosition, diameter, diameter);
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, 
                new Ellipse2D.Double(xPosition, yPosition, 
                diameter, diameter));
            canvas.wait(10);
        }
    }
    
    public Shape getShape(){
        return (Shape) circle;
    }
}