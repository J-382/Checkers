package shapes;

import java.awt.*;
import java.awt.geom.*;

/**
 * A figure that can be manipulated and that draws itself on a canvas.
 *
 * @author Angie Medina - Jose Perez
 * @version 02/10/20
 */
public abstract class Figure{
    protected int xPosition;
    protected int yPosition;
    protected String color;
    protected boolean isVisible;
    protected Shape figure;

    public Figure(int x,int y,String color, Shape figure){
        xPosition = x;
        yPosition = y;
        this.color = color;
        isVisible = false;
        this.figure = figure;
    }
    
    /**
     * Make this figure visible. If it was already visible, do nothing.
     */
    public  void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make this figure invisible. If it was already invisible, do nothing.
     */
    public void  makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /**
     * Move the figure a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the figure a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the figure a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the figure a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the figure horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the figure vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the figure horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the figure vertically
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        }else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }
    
    /**
     * Change the color. 
     * @param newColor written in RGB code
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }
    
     /**
     * Returns the figure's current color
     * @return A string with the figure's current color.
     */
    public String getColor(){
        return color;
    }
    
    /**
     * Draw the figure with current specifications on screen.
     */
    public void draw(){     
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, figure);
            canvas.wait(10);
        }
    }
    
    /**
     * Erase the figure on screen.
     */
    public void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}
