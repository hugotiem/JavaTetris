package solver;

import model.*;

import java.util.ArrayList;

public class Solver {

    private static int nbMoves = 0;
    private static int cpt = 0;

    public static void setNbMoves(int newNbMoves){
        nbMoves = newNbMoves;
    }

    public static MoveAndScore solve(int depth, Board board, MoveAndScore bestScore){
        boolean firstMove = false;
        MoveAndScore best = new MoveAndScore(new Move(null, ""), 0, 0);
        if(depth != 0){
            for(Piece piece : new ArrayList<>(board.getPieces())){
                for(Move move : piece.getValidMoves(board)){
                    cpt ++;
                    System.out.println(cpt);
                    if(bestScore.getTypeMove().equals("") && bestScore.getPiece() == null){
                        //System.out.println("##init##");
                        firstMove = true;
                        bestScore.setTypeMove(move.getTypeMove());
                        bestScore.setPiece(piece);
                    }


                    Point tmp = piece.getPoint().clone();
                    piece.move(board, move.getTypeMove());

//                    for (Point p : board.getPuzzle()) {
//                        System.out.print(" " + p.getCh() + " ");
//                        if (p.getY() == board.getNbCol() - 1) {
//                            System.out.println();
//                        }
//                    }
//
//                    System.out.println();
//                    System.out.println("#######################################");
//                    System.out.println();

                    int currentScore = board.eval();

                    if((currentScore > bestScore.getScore()) || (currentScore == bestScore.getScore() && depth > bestScore.getDepth())){
                        bestScore.setScore(currentScore);
                        bestScore.setDepth(depth);
                    }
                    MoveAndScore nextMoveAndScore = solve(depth - 1, board, bestScore);
                    if((currentScore > bestScore.getScore()) || (currentScore == bestScore.getScore() && depth > bestScore.getDepth())){
                        bestScore.setScore(nextMoveAndScore.getScore());
                        bestScore.setDepth(nextMoveAndScore.getDepth());
                    }
                    piece.setPoint(tmp);
                    board.initBoard();
                    board.putPiecesOnBoard();

//                    System.out.println("##################");
//                    System.out.println(bestScore.getTypeMove());
//                    System.out.println(bestScore.getPiece());
//                    System.out.println(bestScore.getScore());
//                    System.out.println("##################");

                    if(firstMove) {
                        System.out.println("first move");
                        if (bestScore.getScore() > best.getScore() || (bestScore.getScore() == best.getScore() && bestScore.getDepth() > best.getDepth())) {
                            best.setScore(bestScore.getScore());
                            best.setPiece(bestScore.getPiece());
                            best.setTypeMove(bestScore.getTypeMove());
                            best.setDepth(bestScore.getDepth());
                            System.out.println("test : "+best.getTypeMove());
                        }
                        //best = bestScore;
                        bestScore.setTypeMove("");
                    }
                }
                if(firstMove){
                    if (bestScore.getScore() > best.getScore() || (bestScore.getScore() == best.getScore() && bestScore.getDepth() > best.getDepth())) {
                        best.setScore(bestScore.getScore());
                        best.setPiece(bestScore.getPiece());
                        best.setTypeMove(bestScore.getTypeMove());
                        best.setDepth(bestScore.getDepth());
                    }
                    bestScore.setPiece(null);
                }
            }
        }
        if(firstMove) {
            System.out.println(cpt);
            board.setCurrentMoves(nbMoves);
            return best;
        }
        else
            return bestScore;
    }
}
