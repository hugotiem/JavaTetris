package view;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeView extends JFrame implements ActionListener {

    private JTextField txt;

    public HomeView(){
        super("Accueil");

        JLabel title = new JLabel("     Bienvenue dans le Jeu d'Assemblage");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setPreferredSize(new Dimension(400, 100));

        JPanel content = new JPanel();
        content.setPreferredSize(new Dimension(500, 100));

        JLabel label = new JLabel("Votre nom :");

        txt = new JTextField(20);

        JButton button = new JButton("Jouer");
        button.addActionListener(this);

        content.add(label);
        content.add(txt);
        content.add(button);

        //JPanel pan = (JPanel) this.getContentPane();

        //this.getContentPane().add(txt);

        this.setLocationRelativeTo(null);
        //this.setSize(200, 200);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        this.getContentPane().add(title, BorderLayout.NORTH);
        this.getContentPane().add(content, BorderLayout.CENTER);
        //this.getContentPane().add(label);
        //this

        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Board b = Board.getRandomBoard(20, 20, 10);
        System.out.println("name : "+this.txt.getText());
        b.setPlayer(this.txt.getText());
        ViewFrame view = new ViewFrame(b);
        view.draw();
        this.dispose();
    }
}
