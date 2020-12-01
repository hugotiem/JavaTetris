package factory;

import model.*;

import java.util.Random;

public class EazyPieceFactory implements PieceFactory {

    private Random rand;

    /**
     * permet de "fabriquer" une instance d'une sous classe de Piece
     * facilement et rapidement (design pattern)
     * Les pieces retournees n'ont aucunes exigeances lors de leur creations;
     * @return une instance d'une sous classe de Piece
     */


    @Override
    public Piece instance() {
        rand = new Random();

        int x = 3 + rand.nextInt((6 - 3));
        int y = 3 + rand.nextInt((6 - 3));

        int color = rand.nextInt(5);
        String[] forms = new String[]{"Rec", "L"};
        String form = forms[rand.nextInt(2)];

        String ch = RawMaterial.getCh();

        switch (form) {
            case "Rec":
                return new PieceRec(x, y, new Point(0, 0), ch, color);
            default:
                return new PieceL(x, y, new Point(0, 0), ch, color);
        }
    }
}
