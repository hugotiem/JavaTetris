package model;

public class PieceL extends Piece {

    public PieceL(int x, int y, Point point, String ch, int color) {
        super(x, y, point, ch, color);

        boolean[][] tmp = this.getPieceValue();

        for(int i = 0; i < this.getX(); i++) {
            tmp[i][0] = true;
        }
        for (int i = 0; i < this.getY(); i++){
            tmp[this.getX() - 1][i] = true;
        }
    }

    @Override
    public String getForm() {
        return "L";
    }
}
