package view;

import model.Board;
import model.Piece;

import javax.swing.*;
import java.awt.*;

public class ViewComponents extends JPanel {

    private ViewFrame frame;
    private Piece p;

    public ViewComponents(ViewFrame frame, Piece p){
        this.frame = frame;
        this.p = p;
    }

    public static Color getColor(int color){
        switch (color){
            case 0:
                return Color.YELLOW;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.RED;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.MAGENTA;
            default:
                return null;
        }
    }

    public static String getStringColor(int color){
        switch (color){
            case 0:
                return "jaune";
            case 1:
                return "bleu";
            case 2:
                return "rouge";
            case 3:
                return "vert";
            case 4:
                return "magenta";
            default:
                return null;
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(0, 0, ViewFrame.HEIGHT/frame.getBoardHeight(), ViewFrame.WIDTH/frame.getBoardWidth());
        if(p != null){
            g.setColor(this.getColor(this.p.getColor()));
            g.fillRect(1, 1, ViewFrame.HEIGHT/frame.getBoardHeight(), ViewFrame.WIDTH/frame.getBoardWidth());
        }

        //this.repaint();
    }
}
