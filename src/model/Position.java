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
public class Position {
    
    private int posX;
    private int posY;

    public Position(int ligne, int col) {
        this.posX = ligne;
        this.posY = col;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public void setPosX(int ligne) {
        this.posX = ligne;
    }

    public void setPosY(int col) {
        this.posY = col;
    }
    
    
    
    
}
