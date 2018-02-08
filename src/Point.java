/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author as
 */
public class Point implements Translatable {
    private double x, y;
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    @Override
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    @Override
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    @Override
    public void setY(double y) {
        this.y = y;
    }
    
    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }
}
