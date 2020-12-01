package model;

import factory.EazyPieceFactory;
import factory.HardPieceFactory;
import factory.PieceFactory;
import factory.RawMaterial;
import save.Storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Board {

    private final int nbRow;
    private final int nbCol;

    private String player;

    private ArrayList<Point> puzzle;
    private ArrayList<Piece> pieces;

    private Piece currentPiece;

    private int maxMoves;
    private int currentMoves;
    private int score;

    public Board(int nbRow, int nbCol){
        this.nbRow = nbRow;
        this.nbCol = nbCol;
        this.puzzle = new ArrayList<>();
        this.pieces = new ArrayList<>();
        this.score = 0;
        this.maxMoves = 100;
        this.currentMoves = 0;

        this.initBoard();
    }

    // GETTER
    public int getNbRow(){ return this.nbRow; }
    public int getNbCol(){ return this.nbCol; }
    public String getPlayer() { return this.player; }
    public ArrayList<Point> getPuzzle() { return this.puzzle; }
    public ArrayList<Piece> getPieces() { return this.pieces; }
    public Piece getCurrentPiece() { return this.currentPiece; }
    public int getMaxMoves() { return this.maxMoves; }
    public int getCurrentMoves() { return this.currentMoves; }
    public int getScore() { return this.score; }

    // SETTER
    public void setPlayer(String player) { this.player = player; }
    public void setPieces(ArrayList<Piece> pieces) { this.pieces = pieces; }
    public void setCurrentPiece(Piece p) { this.currentPiece = p; }
    public void setCurrentMoves(int currentMoves) { this.currentMoves = currentMoves; }
    public void setScore(int score) { this.score = score; }

    public void initBoard(){
        this.puzzle = new ArrayList<>();
        for(int i = 0; i < nbRow; i++){
            for (int j = 0; j < nbCol; j++){
                puzzle.add(new Point(i, j));
            }
        }
    }

    /**
     * Ajoute une piece a l'ArrayList pieces
     * @param p la piece ajoute
     */
    public void add(Piece p){
        this.pieces.add(p);
    }

    /**
     * Genere une grille de facon aleatoire
     * @param row le nb de lignes de la grille
     * @param col le nb de colonnes de la grille
     * @param diff la difficulte de la partie
     * @return une instance de la grille
     */
    public static Board getRandomBoard(int row, int col, int diff){
        Board b = new Board(row, col);
        Random rand = new Random();

        ArrayList<PieceFactory> factories = new ArrayList<>();

        RawMaterial.emptyList(); // On vide la liste des caracteres deja utilisees dans les matieres premieres de Factory

        EazyPieceFactory eazyPieceFactory = new EazyPieceFactory();
        HardPieceFactory hardPieceFactory = new HardPieceFactory();
        factories.add(eazyPieceFactory);
        factories.add(hardPieceFactory);

        for (int i = 0; i < diff; i++){
            Piece p = factories.get(i%2).instance();
            p.setState(rand.nextInt(3));
            p.setPoint(new Point(rand.nextInt(b.getNbRow() - p.getX()), rand.nextInt(b.getNbCol() - p.getY())));
            while (p.isCollision(b)) {
                p.setPoint(new Point(rand.nextInt(b.getNbRow() - p.getX()), rand.nextInt(b.getNbCol() - p.getY())));
            }
            b.add(p);
            b.putPiecesOnBoard();
        }

        return b;
    }

    /**
     * Permet d'obtenir une piece en fonction du caractere qui le represente
     * @param ch le caractere representant une piece
     * @return la piece representee par ce caractere
     */
    public Piece getPieceByCh(String ch){
        for(Piece p: this.pieces){
            if(p.getCh().equals(ch)){
                return p;
            }
        }
        return null;
    }

    /**
     * calcul le score du joueur
     * @return le score calcule
     */
    public int eval (){
        int [][] matrix = this.toMatrix();
        return this.getMaxArea(matrix);
    }

    /**
     * RENVOIE L'AIRE LA PLUS GRANDE PARMIS TOUS LES RECTANGLES DE LA GRILLE
     * @param matrix une representation matricielle de la grille
     * @return l'aire max entre tous les rectangles
     */
    private int getMaxArea(int[][] matrix){
        int max = 0;

        for (int i = 0; i < this.nbRow; i++){
            for (int j = 0; j < this.nbCol; j++){
                if(j + 1 < this.nbCol) {
                    if (matrix[i][j] == 1) {
                        int area = this.getDim(matrix, i, j);
                        /*int area = this.getArea(dim);*/
                        if (area > max)
                            max = area;

                    }
                }
            }
        }
        return max;
    }

    /**
     * RENVOIE LES DIMENSIONS D UN RECTANGLE DEPUIS LE POINT [i, j]
     * @param matrix une representation matricielle de la grille
     * @param i l'index ligne de depart
     * @param j l'index colonne de depart
     * @return la plus grande aire trouvee a partir du point
     */
    private int getDim(int[][] matrix, int i, int j){
        int longueur = 1;
        int largeur = 1;

        int area = 0;

        while (matrix[i][j + longueur] != 0)
            longueur++;

        HashMap<Integer, Integer> recs = new HashMap<>();
        ArrayList<Integer> keysToRemove = new ArrayList<>();

        while (matrix[i + largeur][j] != 0){
            recs.put(largeur + 1, 1);
            largeur++;
        }

        largeur = 1;

        for (int k = 1; k < longueur; k++){
            while (matrix[i + largeur][j+k] != 0)
                largeur++;

            for (Map.Entry<Integer, Integer> rec: recs.entrySet()){
                if(rec.getKey() <= largeur)
                    rec.setValue(rec.getValue() + 1);
                else
                    keysToRemove.add(rec.getKey());
            }
            for (int l: keysToRemove){
                int a = this.getArea(new int[]{l, recs.get(l)});
                if(a > area)
                    area = a;
                recs.remove(l);
            }
            if(!recs.containsKey(largeur))
                recs.put(largeur, 1);

            keysToRemove = new ArrayList<>();
            largeur = 1;
        }

        for (Map.Entry<Integer, Integer> rec: recs.entrySet()){
            int a = this.getArea(new int[]{rec.getKey(), rec.getValue()});
            if (a > area)
                area = a;
        }

        return area;
    }

    /**
     * RENVOIE L'AIRE D'UN RECTANGE DE DIMENSIONS dim
     * @param dim les dimensions du rectangle
     * @return l'aire du rectangle
     */
    private int getArea(int[] dim){
        return dim[0]*dim[1];
    }

    /**
     * CONVERTI L'ARRAYLIST puzzle EN MATRICE BINAIRE
     * @return une matrice de la grille
     */
    public int[][] toMatrix(){
        int[][] matrix = new int[this.getNbRow()][this.getNbCol()];
        for (int i = 0; i < this.getNbRow(); i++){
            for (int j = 0; j < this.getNbCol(); j++){
                if(this.getPuzzle().get(j + (i * this.getNbRow())).getSet()){
                    matrix[i][j] = 1;
                } else
                    matrix[i][j] = 0;
            }
        }
        return matrix;
    }

    /**
     * Sauvegarde la partie
     */
    public void save(){
        ArrayList<String> data = this.toData();
        try {
            Storage.save(data);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde");
        }
    }

    /**
     * Converti les donnees de la partie en une liste
     * @return la liste des donnees
     */
    public ArrayList<String> toData(){
        ArrayList<String> data = new ArrayList<>();
        data.add(this.player);
        data.add(""+this.nbRow);
        data.add(""+this.nbCol);
        data.add(""+this.pieces.size());
        for (Piece p: this.pieces){
            data.add(p.getForm());
            data.add(""+p.getX());
            data.add(""+p.getY());
            data.add(p.getPoint().toString());
            data.add(""+p.getState());
            StringBuilder values;
            values = new StringBuilder();
            for (int i = 0; i < p.getX(); i++){
                for (int j = 0; j < p.getY(); j++){
                    values.append(p.getPieceValue()[i][j]).append(" ");
                }
            }
            data.add(values.toString());
            data.add(p.getCh());
            data.add(""+p.getColor());
        }
        data.add(""+this.score);
        System.out.println(this.currentMoves);
        data.add(""+this.currentMoves);

        return data;
    }

    /**
     * Restaure une partie a partir d'une liste de donnees
     * @param data la liste des donnees
     * @return une instance de la grille avec ces donnees
     * @throws Exception si il y a un probleme lors de la creation des pieces
     */
    public static Board restaureBoardFromData(ArrayList<String> data) throws Exception {

        Board b = new Board(Integer.parseInt(data.get(1)), Integer.parseInt(data.get(2)));
        b.setPlayer(data.get(0));
        ArrayList<Piece> pieces = new ArrayList<>();

        for (int i = 4; i < 4 + (Integer.parseInt(data.get(3)) * 8); i+=8){ // donnees pieces a partir de l'index 3; Infos sur 8 lignes; data.get(2) correspond au nb de pieces sauvegardees

            int x = Integer.parseInt(data.get(i+1));
            int y = Integer.parseInt(data.get(i+2));
            int p_x = Integer.parseInt(data.get(i+3).substring(1, data.get(i+3).indexOf(",")));
            int p_y = Integer.parseInt(data.get(i+3).substring(data.get(i+3).indexOf(" ")+1, data.get(i+3).indexOf("]")));
            int color = Integer.parseInt(data.get(i+7));

            Point point = new Point(p_x, p_y);
            String ch = data.get(i+6);

            Piece p;
            switch (data.get(i)) {
                case "T":
                    p = new PieceT(x, y, point, ch, color);
                    break;
                case "Z":
                    p = new PieceZ(x, y, point, ch, color);
                    break;
                case "Rec":
                    p = new PieceRec(x, y, point, ch, color);
                    break;
                case "L":
                    p = new PieceL(x, y, point, ch, color);
                    break;
                default:
                    throw new Exception("Erreur lors de la restitution des pieces. Certaines donnees doivent etre corrompues");
            }

            p.setState(Integer.parseInt(data.get(i+4)));
            String [] value = data.get(i+5).split(" ");

            boolean[][] pieceValue = new boolean[x][y];
            int k = 0;

            for (int j = 0; j < value.length; j++){
                if((j - (y * k)) == y)
                    k++;
                pieceValue[k][j - (y * k)] = Boolean.parseBoolean(value[j]);
            }

            p.setPieceValue(pieceValue);
            pieces.add(p);
        }
        b.setPieces(pieces);
        int next = 4 + (Integer.parseInt(data.get(3)) * 8);
        b.setScore(Integer.parseInt(data.get(next)));
        b.setCurrentMoves(Integer.parseInt(data.get(next + 1)));

        return b;
    }

    /**
     * Place les pieces sur la grille
     */
    public void putPiecesOnBoard(){
        for(Piece p: this.pieces){
            Point start = this.puzzle.get(p.getCurrentPoint(this).getX() + (p.getCurrentPoint(this).getY() * this.nbCol));
            //System.out.println(p.getCurrentPoint(this));
            for(int i = 0; i < p.getCurrentX(); i++){
                for(int j = 0; j < p.getCurrentY(); j++){
                    //System.out.println(i + " " + j);
                    int index = start.getX() + ((start.getY() + i) * (this.nbCol) + j); // affiche les Point p[start[0] + i, start[1] + j]
                    if(p.getCurrentPieceValue()[i][j]) {
                        this.puzzle.get(index).setSet((p.getCurrentPieceValue()[i][j])); //
                        this.puzzle.get(index).setCh((p.getCh()));
                    }
                }
            }
        }
    }

    public boolean gameOver(){
        if(this.currentMoves >= this.maxMoves){
            return true;
        }
        return false;
    }
}
