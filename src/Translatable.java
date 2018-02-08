/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */




/**
 *
 * @author as
 */
public interface Translatable extends Positionnable {
    default void translate(double dirX, double dirY){
        setX(getX() + dirX);
        setY(getY() + dirY);
    }
}
