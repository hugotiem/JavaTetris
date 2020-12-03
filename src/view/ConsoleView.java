package view;

import model.*;
import save.Storage;
import solver.Solver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleView implements ViewMethods {

    private final String[] moves = new String[]{"up", "down", "right", "left"};

    private Board b;
    private Scanner scan;

    public ConsoleView(Board b){
        this.b = b;
        this.scan = new Scanner(System.in);
    }

    @Override
    public void setBoard(Board b){
        this.b = b;
    }

    @Override
    public void draw(){
        System.out.println("Quel est votre nom :");
        String name = scan.nextLine();
        b.setPlayer(name);
        List<String> movesList = new ArrayList<>();
        movesList = Arrays.asList(moves);
        String action = "";// = scan.nextLine();
        System.out.println("########## DEBUT DE LA PARTIE ##########");
        while (!action.equals("exit")) {
            System.out.println();
            for (Point p : b.getPuzzle()) {
                System.out.print(" " + p.getCh() + " ");
                if (p.getY() == b.getNbCol() - 1) {
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println("Votre score : " + b.getScore() + "      Nombres de coups autorisee(s) : " + (b.getMaxMoves() - b.getCurrentMoves()));
            System.out.println("CHOISISSEZ LA PIECE EN PRESSANT LA LETTRE QUI LA REPRESENTE :");
            for (Piece p : b.getPieces()) {
                System.out.print(p + "\n");
            }
            System.out.println("afficher le nouveau score => score;");
            System.out.println("Nouvelle partie => new;");
            System.out.println("Sauvegarder la partie => save;");
            System.out.println("Charger une partie => charge;");
            System.out.println("Solver => solve");
            System.out.println("Exit => exit ;");

            action = scan.nextLine();
            Piece currentPiece = b.getPieceByCh(action);
            if (!b.gameOver()){
                if (currentPiece != null) {
                    b.setCurrentPiece(currentPiece);
                    System.out.println("CHOISIR L'ACTION A FAIRE :\n" +
                            "Deplacer vers le haut => \"up\"; \n" +
                            "Deplacer vers le bas => \"down\"; \n" +
                            "Deplacer vers la droite => \"right\"; \n" +
                            "Deplacer vers la gauche => \"left\"; \n" +
                            "Rotation vers la droite => \"r\"; \n" +
                            "Rotation vers la gauche => \"l\"; \n");

                    action = scan.nextLine();

                    switch (action) {
                        case "r":
                            b.getCurrentPiece().rotate(1, b);
                            break;
                        case "l":
                            b.getCurrentPiece().rotate(-1, b);
                            break;
                        default:
                            if (movesList.contains(action)) {
                                b.getCurrentPiece().move(b, action);
                            } else if (action.equals("exit"))         // _|
                                continue;                           //  | AU CAS OU LE JOUEUR DECIDE DE FAIRE LES COMMANDES A CE MOMENT LA
                            else if (!commandTraitment(action)) {    // _|
                                System.out.println("L'action '" + action + "' ne correspond a aucune action.");
                            }
                    }
                } else if (!action.equals("exit") && !commandTraitment(action)) {
                    System.out.println("Auncune piece ne correspond a ce caractere : " + action);
                }
            } else {
                System.out.println("Le nombre de coups autorises a ete atteint !");
            }
            //break;
        }
    }

    /**
     * Traite les actions qui ne servent pas a jouer
     * @param action L'action envoye par le joueur
     * @return true si l'action correspond a l'une des commandes
     */
    public boolean commandTraitment(String action){
        if (action.equals("save")) {
            b.save();
            return true;
        }else if (action.equals("charge")){
            this.chargeGame();
            return true;
        }else if (action.equals("new")){
            String player = b.getPlayer();
            b = Board.getRandomBoard(b.getNbRow(), b.getNbCol(), 10);
            b.setPlayer(player);
            return true;
        }else if(action.equals("score")){
            b.setScore(b.eval());
            return true;
        } else if(action.equals("solve")){
            Solver.setNbMoves(b.getCurrentMoves());
            MoveAndScore move = Solver.solve(2, b, new MoveAndScore(new Move(null, ""), 0));
            System.out.println(move.getTypeMove());
            System.out.println(move.getScore());
            System.out.println(move.getPiece());
            return true;
        }
        return false;
    }

    /**
     * Permet de charger une partie
     */
    public void chargeGame(){
        ArrayList<String> saves = Storage.getSaves(b.getPlayer());
        System.out.println("Choisissez la partie a charger :");

        for (int i = 0; i < saves.size(); i++){
            System.out.println(saves.get(i) + " => press " + (i+1));
        }

        int save = scan.nextInt();

        try {
            System.out.println("Chargement de la partie");
            ArrayList<String> data = Storage.getData(saves.get(save - 1));
            b = Board.restaureBoardFromData(data);
            //this.setBoard(b);
            this.modelUpdated();
        } catch (IOException ex){
            System.err.println("ERROR 1" + ex.getMessage());
        } catch (Exception ex1){
            System.err.println("ERROR 2"+ ex1.getMessage());
        }
        System.out.println("Partie Chargee !");
    }

    @Override
    public void modelUpdated(){
        b.putPiecesOnBoard();
        scan = new Scanner(System.in);
    }
}
