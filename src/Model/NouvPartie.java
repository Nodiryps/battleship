/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static java.lang.Character.getNumericValue;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Spy
 */
public class NouvPartie {

    Scanner insert = new Scanner(System.in);

    private Gameboard gb;
    private final int nbJ;
    private List<Armee> listArmee = new LinkedList<>();
    

    public NouvPartie(int size) {

        this.gb = new Gameboard(size);
        this.nbJ = 2;
        creationArmees();
        this.gb.nouvMer(listArmee);

    }

    private void creationArmees() {
        for (int i = 1; i <= nbJ; ++i) {
            System.out.println("Nom Joueur " + i + ": ");
            listArmee.add(new Armee());
        }
    }

    public List<Armee> getListArmees() {
        return listArmee;
    }
    
    //    récupéation pos insert par le user
    public Position selectBat(String s) {
        return stringToPos(s);
    }

    private Position stringToPos(String s) {
        Position p = new Position(0, 0);

        char[] toCharArray = s.toCharArray();            //découpe le String "B5" en tab de char
        char x = toCharArray[0];
        char y = toCharArray[1];                         //x == 'B' et y == '5'
        int i = getNumericValue(y);                      //converti '5' en 5

        for (int j = 0; j < gb.getTAILLE(); ++j) {
            for (int k = 0; k < gb.getTAILLE(); ++k) {
                if (gb.getAXE_X()[j] == x && gb.getAXE_Y()[k] == i) {
                    p.setPosX(j);
                    p.setPosY(k);
                }
            }
        }
        return p;
    }

    //    récupération pos au format "B5" par exemple
    public String convertPosToStr(int x, int y) {
        return posToString(x, y);
    }

    private String posToString(int x, int y) {
        Position p = new Position(0,0);

        x = p.getPosX(); y = p.getPosY();
        String res = "";
        for (int i = 0; i < gb.getTAILLE(); ++i) {
            for (int j = 0; j < gb.getTAILLE(); ++j) {
                if (gb.getAXE_X()[i] == x && gb.getAXE_Y()[j] == y) {
                    res += x + "" + y;
                }
            }
        }
        return res;
    }

    public boolean posValide(String s) {
        Position p = stringToPos(s);
        return p.getPosX() >=0 && p.getPosX() < gb.getTAILLE() && p.getPosY() >=0 && p.getPosY() < gb.getTAILLE();
    }
    
    public void tir(Armee a) {
        
        Position batChoisi = selectBat(insert.nextLine());
        List<Position> zoneTir = null;

        for (Bateau b : a.getListBat()) {
            Position p = new Position(b.getX(), b.getY());//choppe la pos des bat de la liste
            if (batChoisi.equals(p)) {                    //choppe le bateau choisi
                b.randomPortee();                         //set la portée
                if (b.getPortee() != 0) {
                    zoneTir = b.porteeTir();
                    for(Position p2 : zoneTir) {
                        ////////////////////////////////////p2 => circulaire
                       for(Armee ar: this.listArmee){
                           if(!ar.getNom().equals(a.getNom())){
                                for(Bateau ba : ar.getListBat()){
                                    ba.touché();
                                }
                            }
                       }
                    }

                }
                else
                    System.out.println("plouf! °~°");
            }
        }
    }

//    public static void main(String[] args) {
//        NouvPartie c = new NouvPartie();
//        Vue v = new Vue();
//        v.afficheMer(c.getGB());
//    }

    
}
