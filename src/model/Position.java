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
    
    private int posY;
    private int posX;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.posX;
        hash = 79 * hash + this.posY;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (this.posY != other.posY) {
            return false;
        }
        if (this.posX != other.posX) {
            return false;
        }
        return true;
    }
    
    
    
    
}
