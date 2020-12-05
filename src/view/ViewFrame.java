package view;

import controller.KeyController;
import controller.MouseController;
import model.Board;
import model.Piece;
import model.Point;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewFrame extends JFrame implements ViewMethods {

    public static final int HEIGHT = 600;
    public static final int WIDTH = 600;

    private Board b;
    private final JPanel content;
    private String message;
    private KeyController keyController;

    //private KeyController keyCtrl;
    //private MouseController mouseCtrl;

    public ViewFrame(Board b){
        super("Jeu d'assemblage de formes");
        this.b = b;
        this.content = new JPanel();
        //this.keyCtrl = keyCtrl;
        //this.mouseCtrl = mouseCtrl;
        this.message = "";

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        this.setVisible(true);


        content.setLayout(new GridLayout(this.getBoardHeight(), this.getBoardWidth()));
    }

    public int getBoardHeight(){
        return this.b.getNbRow();
    }

    public int getBoardWidth(){
        return this.b.getNbCol();
    }

    public void setMessage(String message){
        this.message = "    Piece selectionée : " + message;
    }

    public void setMessage(String message, String prefix){
        this.message = prefix + " " + message;
    }

    @Override
    public void setBoard(Board b){
        this.b = b;
    }

    /**
     * Affiche graphiquement une representation de la grille permetant au joueur d'avoir un meilleur visuel de la partie en cours
     */
    @Override
    public void draw(){
        this.keyController = new KeyController(b, this);
        this.addKeyListener(keyController);

        //System.out.println("Nouvel affichage !");

        ArrayList<Point> puzzle = b.getPuzzle();

        for (int i = 0; i < b.getNbRow(); i++){
            for (int j = 0; j < b.getNbCol(); j++){
                Point current = puzzle.get(j + (i * b.getNbRow()));
                Piece p =  b.getPieceByCh(current.getCh());
                ViewComponents viewCpts = new ViewComponents(this, p);
                viewCpts.setPreferredSize(new Dimension(WIDTH/this.getBoardWidth(), HEIGHT/this.getBoardHeight()));
                viewCpts.addMouseListener(new MouseController(this, this.b, p));
                content.add(viewCpts);
            }
        }

        // AFFICHE LA PIECE SELECTIONEE
        JLabel label;
        if(b.gameOver()){
            label = new JLabel("Le nombre de deplacements autorises a ete atteint !");
        } else {
            label = new JLabel(message);
        }
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setPreferredSize(new Dimension(WIDTH, 50));

        // AFFICHE SCORE + BOUTONS
        JPanel toolbar = new JPanel();
        toolbar.setPreferredSize(new Dimension(200, HEIGHT));

        //SCORE
        JLabel score = new JLabel("Score : " + b.getScore());
        score.setFont(new Font("Arial", Font.BOLD, 25));

        // BUTTONS

        Dimension dim  = new Dimension(180, 30);

        JButton displayScore = new JButton("Afficher le score");
        displayScore.setPreferredSize(dim);
        displayScore.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        displayScore.setFocusable(false);
        displayScore.addMouseListener(new MouseController(this, this.b, "score"));

        JButton save = new JButton("Sauvegarder la partie");
        save.setPreferredSize(dim);
        save.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        save.setFocusable(false);
        save.addMouseListener(new MouseController(this, this.b, "save"));

        JButton newGame = new JButton("Nouvelle partie");
        newGame.setPreferredSize(dim);
        newGame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        newGame.setFocusable(false);
        newGame.addMouseListener(new MouseController(this, this.b, "new"));

        JButton chargeGame = new JButton("Charger une partie");
        chargeGame.setPreferredSize(dim);
        chargeGame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        chargeGame.setFocusable(false);
        chargeGame.addMouseListener(new MouseController(this, this.b, "charge"));

        JButton solver = new JButton("Activer Solver");
        solver.setPreferredSize(dim);
        solver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        solver.setFocusable(false);
        solver.addMouseListener(new MouseController(this, this.b, "solver"));

        JTextArea moves = new JTextArea("nombre de deplacement(s)\n autorise(s) : " + (b.getMaxMoves() - b.getCurrentMoves()));
        moves.setEditable(false);
        moves.setFocusable(false);
        moves.setOpaque(false);

        toolbar.add(score);
        toolbar.add(displayScore);
        toolbar.add(save);
        toolbar.add(newGame);
        toolbar.add(chargeGame);
        toolbar.add(solver);
        toolbar.add(moves);

        this.getContentPane().add(content);
        this.getContentPane().add(label, BorderLayout.SOUTH);
        this.getContentPane().add(toolbar, BorderLayout.EAST);
        this.pack();
    }

    /**
     * indique a la vue un changement du model
     */
    @Override
    public void modelUpdated(){
        //System.out.println("MISE A JOUR");
        this.content.removeAll();
        this.getContentPane().removeAll();
        this.removeKeyListener(keyController);
        this.draw();
    }
}
