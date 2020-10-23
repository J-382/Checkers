package shapes;

import java.awt.*;

/**
 * A triangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0  (15 July 2000)
 */

public class Triangle extends Figure{
    private int height;
    private int width;
    
    /**
     * Create a new triangle at default position with default color.
     */
    public Triangle(){
        super(140, 15, "green", new Polygon(new int[] {140, 140 + (40/2), 140 - (40/2)},
            new int[] {15, 15 + 30, 15 + 30}, 3));
        height = 30;
        width = 40;
    }

    /**
     * Change the size to the new size
     * @param newHeight the new height in pixels. newHeight must be >=0.
     * @param newWidht the new width in pixels. newWidht must be >=0.
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }
}
