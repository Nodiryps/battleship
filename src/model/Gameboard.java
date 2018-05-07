/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;



/**
 *
 * @author 2208sptheodorou
 */
public class Gameboard {
    
    private static final char[] AXE_X = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private static final int[] AXE_Y = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26};
    private final int TAILLE;
    private Case[][] mer;
    private Map<String, Position> mapPositions = new HashMap<>();
    private Set<Position> setPosOccupados = new HashSet<>(); //enregistre les pos occupées
    
    
    public Gameboard(int size) {
        this.TAILLE = size;
        this.mer = new Case[TAILLE][TAILLE];
    }

    public char[] getAXE_X() {
        return AXE_X;
    }

    public int[] getAXE_Y() {
        return AXE_Y;
    }
    
    public int getTAILLE() {
        return TAILLE;
    }

    public Case[][] getMer() {
        return mer;
    }

    public Set<Position> getSetPosOccupados() {
        return setPosOccupados;
    }

    public boolean setPosOccupadosContains(Position p){
        return setPosOccupados.contains(p);
    }

    public boolean mapPositionsContains(Position p){
        return mapPositions.containsValue(p);
    }
    
    public Set<String> mapPositionsKeySet(){
        return mapPositions.keySet();
    }
    
    public void mapPositionsPut(String s, Position p) {
        mapPositions.put(s, p);
    }
    
    public Position getPosFromMapPositions(String s) {
        return mapPositions.get(s);
    }
    
    public void setPosOccupadosClear() {
        setPosOccupados = new HashSet<>();
    }
    
    public void setPosOccupadosAdd(Position p){
        setPosOccupados.add(p);
    }

    public Set<Position> getPosOccupados() {
        return setPosOccupados;
    }

    public void setMer(Case[][] m) {
        this.mer = m;
    }
    
    

}
