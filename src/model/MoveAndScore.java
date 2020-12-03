package model;

public class MoveAndScore  extends Move {

    private int score;

    public MoveAndScore(Move move, int score) {
        super(move.getPiece(), move.getTypeMove());
        this.score = score;
    }

    public int getScore(){ return this.score; }

    public void setScore(int score) { this.score = score; }
}
