package factory;

import model.Piece;

public interface PieceFactory {

    /**
     * Fabrique les pieces de la partie
     * @return une instance de Piece
     */
    public Piece instance();
}
