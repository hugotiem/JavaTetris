package controller;

import model.Board;
import model.Piece;
import view.ViewFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyController implements KeyListener {

    private final Board board;
    private final ViewFrame frame;

    public KeyController(Board b, ViewFrame frame){
        this.board = b;
        this.frame = frame;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Piece current = this.board.getCurrentPiece();
        if(!board.gameOver()){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    current.move(this.board, "right");
                    break;
                case KeyEvent.VK_LEFT:
                    current.move(this.board, "left");
                    break;
                case KeyEvent.VK_UP:
                    current.move(this.board, "up");
                    break;
                case KeyEvent.VK_DOWN:
                    current.move(this.board, "down");
                    break;
                case KeyEvent.VK_R:
                    current.rotate(1, this.board);
                    break;
                case KeyEvent.VK_L:
                    current.rotate(-1, this.board);
                    break;
            }
            this.frame.setBoard(this.board);
            this.frame.modelUpdated();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
