import factory.EazyPieceFactory;
import factory.HardPieceFactory;
import factory.PieceFactory;
import model.*;
import view.*;

import save.Storage;

import java.io.IOException;
import java.util.*;

public class Main {

    private static int cpt = 0;

    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);
        System.out.println("Voulez vous jouer avec l'interface graphique ? (y|n)");
        String rep = scan.nextLine();

        if(rep.equals("y")){
            HomeView view = new HomeView();
        } else {
            Board b = Board.getRandomBoard(20, 20, 10);
            ConsoleView cv = new ConsoleView(b);
            cv.draw();
            //MoveAndScore sol = solve(4, b, new MoveAndScore(new Move(null, ""), 0));
            //System.out.println(sol.getScore());
            System.out.println(cpt);
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


}
