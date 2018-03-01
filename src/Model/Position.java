/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

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

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int ligne) {
        this.posX = ligne;
    }

    public void setPosY(int col) {
        this.posY = col;
    }
    
    
    
    
}
