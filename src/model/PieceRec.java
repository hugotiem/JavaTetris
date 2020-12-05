package model;

public class PieceRec extends Piece {

    public PieceRec(int x, int y, Point point, String ch, int color) {
        super(x, y, point, ch, color);

        boolean[][] tmp = this.getPieceValue();

        for (int i = 0; i < this.getX(); i++){
            for (int j = 0; j < this.getY(); j++){
                tmp[i][j] = true;
            }
        }
    }

    @Override
    public String getForm() {
        return "Rec";
    }
}
