/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


/**
 *
 * @author Spy
 */
public enum Direction {
    HAUT("en haut"),
    BAS("en bas"),
    GAUCHE("à gauche"),
    DROITE("à droite"), 
    QUITTER("abandonner");
    private final String msg;
    
    public int numero() {
        return ordinal() + 1;
    }
    
    Direction(String s){
        this.msg = s;
    }
    
    @Override
    public String toString(){
        return msg;
    }
    
//    pour choisir dans la liste de direction
    public static Direction getByInt(int choix) throws IndexOutOfBoundsException {
        if (choix < 1 || choix > Direction.values().length) {
            throw new IndexOutOfBoundsException();
        }
        return Direction.values()[choix - 1];
    }
    
    
}
