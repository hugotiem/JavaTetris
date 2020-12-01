package factory;

import model.Piece;
import model.PieceT;
import model.PieceZ;
import model.Point;

import java.util.ArrayList;
import java.util.Random;

public class HardPieceFactory implements PieceFactory {

    private Random rand;

    /**
     * permet de "fabriquer" une instance d'une sous classe de Piece
     * facilement et rapidement (design pattern)
     * Les pieces retournees ont des exigeances lors de leur creation
     * -> x ne doit pas etre pair pour Z et y de meme pour T
     * @return une instance d'une sous classe de Piece
     */

    @Override
    public Piece instance(){
        rand = new Random();
        int x;

        if(rand.nextInt(2) == 0)
            x = 3;
        else
            x = 5;

        int y = 3 + rand.nextInt((6 - 3));
        String ch = RawMaterial.getCh();
        int color = rand.nextInt(5);

        String[] forms = new String[]{"T", "Z"};
        String form = forms[rand.nextInt(2)];

        switch (form) {
            case "T":
                //System.out.println("x : " + x + ", y : " + y);
                return new PieceT(y, x, new Point(0, 0), ch, color);
            default:
                return new PieceZ(x, y, new Point(0, 0), ch, color);
        }
    }

}
