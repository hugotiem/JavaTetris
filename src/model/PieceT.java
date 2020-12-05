package model;

public class PieceT extends Piece {

    public PieceT(int x, int y, Point point, String ch, int color) {
        super(x, y, point, ch, color);

        boolean[][] tmp = this.getPieceValue(); // recupere la representation de la piece

        // DESSINE LE T
        for(int i = 0; i < this.getX(); i++){
            for(int j = 0; j < this.getY(); j++){
                if(i == 0 || j == (this.getY()/2))
                    tmp[i][j] = true;
            }
        }
    }

    @Override
    public String getForm() {
        return "T";
    }
}
