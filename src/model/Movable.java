/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author 2208sptheodorou
 */
interface Movable extends Positionnable {
    
    default void moveTo(int x, int y){
        setX(getX() + x);
        setY(getY() + y);
    }
}
