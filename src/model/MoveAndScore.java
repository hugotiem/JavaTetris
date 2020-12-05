package model;

public class MoveAndScore  extends Move {

    private int score;
    private int depth; // pronfondeur du meilleur score

    public MoveAndScore(Move move, int score, int depth) {
        super(move.getPiece(), move.getTypeMove());
        this.score = score;
        this.depth = depth;
    }

    public int getScore(){ return this.score; }

    public int getDepth() {
        return depth;
    }

    public void setScore(int score) { this.score = score; }

    public void setDepth(int deptn) {
        this.depth = deptn;
    }
}
