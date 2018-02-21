package Model;

import static Model.TypeBateau.GRAND;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Spy
 */
public abstract class Bateau implements Movable {
    private int portee;
    private int pv;
    private static int id;
    private Pos pos;
    private static TypeBateau type;
    private MouvementsBateau mouvmt;
    
    public Bateau(){
        
    }
    
    public abstract void touche();
    
    public abstract boolean coule();
    
    public abstract void randomPortee();
    
    public int getPortee(){
        return this.portee;
    }
    
    public void setPortee(int nouvP){
        this.portee = nouvP;
    }
    
    public int getPv(){
        return this.pv;
    }
    
    public void setPv(int nouvPv){
        this.pv = nouvPv;  
    }
    
    public int getId(){
        return this.id;
    }
    
    public void setId(int nouvId){
        this.id = nouvId;
    }
    
    public Pos getPos(){
        return pos;
    }
    
    public void setPos(Pos nouvPos){
        this.pos = nouvPos;
    }
    
    public static String getType(){
        return type.name();
    }
    
    @Override 
    public void setX(int x){
        pos.setPosC(x);
    }
    
    @Override 
    public void  setY(int y){
        pos.setPosL(y);
    }   
    
    @Override 
    public int getX(){
        return pos.getPosC();
    }   
    
    @Override 
    public int getY(){
        return pos.getPosL();
    }  
    
    @Override
    public boolean equals(Object o){
        return(
                o instanceof Bateau &&
                o.equals(id) && 
                o.equals(pos)
              );
    }

    //pas de equals sans hashCode :)
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + getId();
        hash = 97 * hash + getPos();
        return hash;
    }
    
}
