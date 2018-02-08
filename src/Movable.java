/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Spy
 */
public interface Movable {
//    public void moveTo(Position pos);
    
    char getX();
    char getY();
//    void setX(char g, char d);
//    void setY(char h, char b);
    void setX(double x);
    void setY(double y);
}
