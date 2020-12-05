package model;

public class Move {
    private Piece piece;
    private String typeMove;

    public Move(Piece piece, String typeMove) {
        this.piece = piece;
        this.typeMove = typeMove;
    }

    public Piece getPiece(){ return this.piece; }
    public String getTypeMove(){ return this.typeMove; }

    public void setPiece(Piece piece) { this.piece = piece; }
    public void setTypeMove(String typeMove) { this.typeMove = typeMove; }
}
