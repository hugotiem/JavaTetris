package view;

import model.Board;
import model.Piece;
import save.Storage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class SavesView extends JFrame implements ActionListener {

    private final ViewFrame frame;

    public SavesView(String player, ViewFrame frame){
        super("Charger une partie");

        this.frame = frame;

        ArrayList<String> saves = Storage.getSaves(player);

        JLabel label = new JLabel("Choisir la partie à charger :");
        label.setFont(new Font("Arial", Font.BOLD, 25));
        label.setPreferredSize(new Dimension(400, 100));

        JPanel content = new JPanel();

        for(String save: saves){
            JButton button = new JButton(save);
            button.addActionListener(this);
            button.setPreferredSize(new Dimension(120, 30));
            content.add(button);
        }

        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

        this.getContentPane().add(label, BorderLayout.NORTH);
        this.getContentPane().add(content, BorderLayout.CENTER);

        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton but = (JButton) e.getSource();
        ArrayList<String> data = new ArrayList<>();
        Board b;
        try {
            System.out.println("Chargement de la partie");
            data = Storage.getData(but.getText());
            b = Board.restaureBoardFromData(data);
            b.putPiecesOnBoard();
            this.frame.setBoard(b);
            this.frame.modelUpdated();
        } catch (IOException ex){
            System.err.println("ERROR 1" + ex.getMessage());
        } catch (Exception ex1){
            System.err.println("ERROR 2"+ ex1.getMessage());
        }
        this.dispose();
    }
}
