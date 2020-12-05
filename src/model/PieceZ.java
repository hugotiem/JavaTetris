package model;

public class PieceZ extends Piece {

    public PieceZ(int x, int y, Point point, String ch, int color) {
        super(x, y, point, ch, color);

        boolean[][] tmp = this.getPieceValue();
        int center = this.getX()/2; // CENTRE DU Z

        for(int i = 0; i < this.getX(); i++){
            if(i < center)
                tmp[i][0] = true;
            if(i == center){
                for(int j = 0; j < this.getY(); j++){
                    tmp[i][j] = true;
                }
            }
            if(i > center)
                tmp[i][this.getY() - 1] = true;
        }
    }

    @Override
    public String getForm() {
        return "Z";
    }

}
