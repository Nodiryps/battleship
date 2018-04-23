/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
    protected int maxPV; //Maximum de point de vie
    protected int pm;    //points de mouvement
    protected TypeB type;
    protected Direction dir;
   
    public Bateau(TypeB type,int ptVie,int ptVieMax,int ptMouvmt){
        this.pm = ptMouvmt;
        this.pv = ptVie;
        this.maxPV = ptVieMax;
        this.type = type;
    }
    
    public Position getXY() {
        return this.posBat;
    }
    
    @Override
    public int getX() {
        return this.posBat.getPosX();
    }

    @Override
    public int getY() {
        return this.posBat.getPosY();
    }

    public int getPortee() {
        return portee;
    }

    public int getPv() {
        return pv;
    }
    public int getMaxPv() {
        return maxPV;
    }
    public int getPm() {
        return pm;
    }
    
    public TypeB getTypeB() {
        return type;
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
    
    public void touché() {
        --this.pv;
    };

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
