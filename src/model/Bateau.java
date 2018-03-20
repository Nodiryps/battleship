/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;




/**
 *
 * @author 2208sptheodorou
 */
public abstract class Bateau implements Positionnable{
    
    Position posBat = new Position(0,0);
    protected int portee;//portée du tir
    protected int pv;    //points de vie
    protected int pm;    //points de mouvement
    protected TypeB type;
    protected Direction dir;
    
    public Bateau() {
    }
    
    public Position getPosBat(){
        return this.posBat;
    }

    public int getPortee() {
        return portee;
    }

    public int getPv() {
        return pv;
    }

    public int getPm() {
        return pm;
    }
    
    public String getTypeB() {
        return type.name();
    }

    public void setPortee(int portee) {
        this.portee = portee;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public void setPos(int x, int y) {
        this.posBat.setPosX(x);
        this.posBat.setPosY(y);
    }

    @Override
    public int getX() {
        return this.posBat.getPosX();
    }

    @Override
    public int getY() {
        return this.posBat.getPosY();
    }

    @Override
    public void setX(int x) {
        this.posBat.setPosX(x);
    }

    @Override
    public void setY(int y) {
        this.posBat.setPosY(y);
    }
    
    public void randomPortee(){
        Random rand = new Random();
        double d = rand.nextDouble();
        if(d <= 0.5){
            setPortee(2);
        }
        else if(d <= 0.3){
            setPortee(1);
        }
        else if(d <= 0.2){
            setPortee(0);
        }
    }
    
    public List porteeTir(){
        List<Position> zoneTir = new LinkedList<>();
        for(int i = getX()-getPortee(); i <= getX()+getPortee() ;++i){      //les cases à gauche du bateau
            for(int j = getY()-getPortee(); j <= getY()-getPortee(); ++j){  //les cases à droite
                Position pos = new Position(i,j);
                zoneTir.add(pos);
            }
        } return zoneTir;
    }
    
    public void touché() {};

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.posBat);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Bateau){
            Bateau b = (Bateau) obj;
            return b.posBat == ((Bateau) obj).posBat;
        }
        return false;
    }


    

    
    
    
    
}
