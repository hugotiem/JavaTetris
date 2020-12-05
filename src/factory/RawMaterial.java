package factory;

import model.Piece;

import java.util.ArrayList;
import java.util.Random;

public class RawMaterial {

    // STATIC ATTRIBUTS
    public static ArrayList<String> alreaduUsed; // chs deja utilisees
    public static String[] chs = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "r", "t", "z"};

    /**
     * Permet d'obtenir un caractere unique afin de representer une piece lors de
     * son affichage dans le terminal
     * @return ch le caractere tire aleatoirement
     */
    public static String getCh(){
        Random rand = new Random();
        String ch = chs[rand.nextInt(chs.length)];
        while(alreaduUsed.contains(ch)){
            ch = chs[rand.nextInt(chs.length)];
        }
        alreaduUsed.add(ch);
        return ch;
    }

    /**
     * vide la liste des caracteres deja utilisees, util lors de la generation
     * d'une nouvelle partie
     */
    public static void emptyList(){
        alreaduUsed = new ArrayList<>();
    }
}
