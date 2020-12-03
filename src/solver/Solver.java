package solver;

import model.*;

import java.util.ArrayList;

public class Solver {

    private static int nbMoves = 0;

    public static void setNbMoves(int newNbMoves){
        nbMoves = newNbMoves;
    }

    public static MoveAndScore solve(int depth, Board board, MoveAndScore bestScore){
        boolean firstMove = false;
        MoveAndScore best = new MoveAndScore(new Move(null, ""), 0);
        if(depth != 0){
            for(Piece piece : new ArrayList<>(board.getPieces())){
                for(Move move : piece.getValidMoves(board)){
                    //cpt ++;
                    if(bestScore.getTypeMove().equals("")){
                        firstMove = true;
                        bestScore.setTypeMove(move.getTypeMove());
                        bestScore.setPiece(piece);
                    }

                    Point tmp = piece.getPoint().clone();
                    piece.move(board, move.getTypeMove());

                    for (Point p : board.getPuzzle()) {
                        System.out.print(" " + p.getCh() + " ");
                        if (p.getY() == board.getNbCol() - 1) {
                            System.out.println();
                        }
                    }

                    System.out.println();
                    System.out.println("#######################################");
                    System.out.println();

                    int currentScore = board.eval();

                    if(currentScore > bestScore.getScore()){
                        bestScore.setScore(currentScore);
                    }
                    MoveAndScore nextMoveAndScore = solve(depth - 1, board, bestScore);
                    if(nextMoveAndScore.getScore() > bestScore.getScore()){
                        bestScore.setScore(nextMoveAndScore.getScore());
                    }
                    piece.setPoint(tmp);
                    board.initBoard();
                    board.putPiecesOnBoard();
                    if(firstMove) {
                        if (bestScore.getScore() > best.getScore()) {
                            best.setScore(bestScore.getScore());
                            best.setPiece(bestScore.getPiece());
                            best.setTypeMove(bestScore.getTypeMove());
                        }
                        //best = bestScore;
                        bestScore.setTypeMove("");
                    }
                }
                if(firstMove){
                    if (bestScore.getScore() > best.getScore()) {
                        best.setScore(bestScore.getScore());
                        best.setPiece(bestScore.getPiece());
                        best.setTypeMove(bestScore.getTypeMove());
                    }
                    bestScore.setPiece(null);
                }
            }
        }
        if(firstMove) {
            board.setCurrentMoves(nbMoves);
            return best;
        }
        else
            return bestScore;
    }
}
