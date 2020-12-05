package controller;

import model.Board;
import model.Move;
import model.MoveAndScore;
import model.Piece;
import save.Storage;
import solver.Solver;
import view.SavesView;
import view.ViewComponents;
import view.ViewFrame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class MouseController implements MouseListener {

    private ViewFrame frame;
    private Board b;
    private Piece p;
    private String val; // valeur du bouton

    public MouseController(ViewFrame frame, Board b, Piece p){
        this.frame = frame;
        this.b = b;
        this.p = p;
    }

    public MouseController(ViewFrame frame, Board b, String val){
        this.frame = frame;
        this.b = b;
        this.val = val;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(p != null){
            b.setCurrentPiece(p);
            frame.setMessage(p.getForm());
        }
        else if(val != null){
            if(val.equals("save")){
                b.save();
            }
            if(val.equals("score")){
                b.setScore(b.eval());
            }
            if(val.equals("new")){
                String player = b.getPlayer();
                b = Board.getRandomBoard(b.getNbRow(), b.getNbCol(), 10);
                b.setPlayer(player);
                //System.out.println(b.getScore());
            }
            if (val.equals("charge")){
                SavesView sv = new SavesView(b.getPlayer(), frame);
            }
            if(val.equals("solver")){
                Solver.setNbMoves(b.getCurrentMoves());
                MoveAndScore move = Solver.solve(2, b, new MoveAndScore(new Move(null, ""), 0, 0));
                move.getPiece().move(b, move.getTypeMove());
                frame.setMessage(move.getTypeMove(), move.getPiece().getForm() + " " + ViewComponents.getStringColor(move.getPiece().getColor()));
            }
        }
        //System.out.println(b.getCurrentPiece());
        frame.setBoard(b);
        frame.modelUpdated();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
