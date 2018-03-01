/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;




/**
 *
 * @author 2208sptheodorou
 */
public abstract class Bateau implements Movable{
    
    protected int portee;//portée du tir
    protected int pv;    //points de vie
    protected int pm;    //points de mouvement
    protected TypeB type;
    protected Direction dir;
    

    public Bateau() {
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
        setX(x); setY(y);
    }

    @Override
    public int getX() {
        return this.getX();
    }

    @Override
    public int getY() {
        return this.getY();
    }

    @Override
    public void setX(int x) {
        this.setX(x);
    }

    @Override
    public void setY(int y) {
        this.setY(y);
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
        List<Position> zoneTir = new LinkedList();
        for(int i = getX()-getPortee(); i <= getX()+getPortee() ;++i){      //les cases à gauche du bateau
            for(int j = getY()-getPortee(); j <= getY()-getPortee(); ++j){  //les cases à droite
                Position pos = new Position(i,j);
                zoneTir.add(pos);
            }
        } return zoneTir;
    }
    
    public void touché() {};
        
//    public void choixDir() {
//        Vue v = new Vue();
//        do {
//            dir = v.saisirDir();
//            switch (dir) {
//                case HAUT:
//                    goUp();
//                    break;
//                case BAS:
//                    goDown();
//                    break;
//                case GAUCHE:
//                    goLeft();
//                    break;
//                case DROITE:
//                    goRight();
//                    break;
//            }
//        } while (dir != Direction.QUITTER);
//    }
//
//    public void goUp() {
//        this.setX(this.getX() - this.getPm());
//    }
//
//    public void goDown() {
//        this.setX(this.getX() + this.getPm());
//    }
//
//    public void goLeft() {
//        this.setY(this.getY() - this.getPm());
//    }
//
//    public void goRight() {
//        this.setY(this.getY() + this.getPm());
//    }

    

    
    
    
    
}
