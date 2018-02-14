package Model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Spy
 */
public class Bateau implements Movable {
    private int portee;
    private int pv;
    private int id;
    private Pos pos;
    private TypeBateau type;
    private MouvementsBateau mouvmt;
    
    public Bateau(int portee, int vie, int id, Pos pos, TypeBateau type, MouvementsBateau m){
        this.portee = portee;
        this.pv = vie;
        this.id = id;
        this.pos = pos;
        this.type = type;
        this.mouvmt = m;
    }
    
    public int getPortee(){
        return this.portee;
    }
    
    public void setPortee(int nouvPortee){
        this.portee = nouvPortee;
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
    
    public String getType(){
        return type.name();
    }
//    
//    public String getMouvmt(){
//        return mouvmt.name();
//    }
    
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
}
