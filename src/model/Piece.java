package model;

import java.util.ArrayList;

public abstract class Piece {

    private int x;
    private int y;
    private Point point; // OU COMMENCE LA PIECE
    private int state;
    private String ch;
    private int color;

    private boolean[][] pieceValue; // FORME DE LA PIECE EN FONCTION DES VALEURS BOOL DU TABLEAU

    public Piece(int x, int y, Point point, String ch, int color){
        this.x = x;
        this.y = y;
        this.point = point;
        this.state = 0; // ETAT INIT
        this.pieceValue = new boolean[x][y];

        // INIT placeValue
        for (int i = 0; i < this.x; i++){
            for(int j = 0; j < this.y; j++){
                this.pieceValue[i][j] = false; // par defaut, la piece est vide -> pas de forme
            }
        }

        this.ch = ch;
        this.color = color;
    }

    // GETTER
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public Point getPoint() {return point; }
    public int getState(){return this.state;}
    public boolean[][] getPieceValue(){return this.pieceValue;}
    public String getCh() {
        return this.ch;
    }
    public int getColor() { return this.color; }

    // SETTER
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}
    public void setPoint(Point point) {this.point = point;}
    public void setState(int state){this.state = state;}
    public void setPieceValue(boolean [][] pieceValue){this.pieceValue = pieceValue;}
    public void setCh(String ch) {
        this.ch = ch;
    }
    public void setColor(int color) { this.color = color; }

    /**
     * Permet d'obtenir la valeur X actuelle par rapport a l'etat de la piece
     * @return la valeur actuelle
     */
    public int getCurrentX() {
        if(this.state%2 == 0) { // CAR MEME HAUTEUR SI ROTATE A 180 deg
            return this.x;
        }
        else
            return this.y;
    }

    /**
     * Permet d'obtenir la valeur X actuelle par rapport a l'etat de la piece
     * @return la valeur actuelle
     */
    public int getCurrentY() {
        if(this.state%2 == 0) // CAR MEME LARGEUR SI ROTATE A 180 deg
            return this.y;
        else
            return this.x;
    }

    /**
     * Permet d'obtenir le point actuel en fonction de l'etat actuelle de la piece
     * @param b la grille du jeu
     * @return une instance du point actuel
     */
    public Point getCurrentPoint(Board b){
        if(this.state%2 == 0 || this.x == this.y) { // CAR MEME LARGEUR SI ROTATE A 180 deg
            if(this.getPoint().getX() + this.x >= b.getNbRow())
                this.getPoint().setX(b.getNbRow() - this.x);

            if(this.getPoint().getY() + this.y >= b.getNbCol())
                this.getPoint().setY(b.getNbCol() - this.y);
            return this.getPoint();
        }
        else {
            int x_tmp = this.point.getX() - ((this.y-this.x)/2);
            int y_tmp = this.point.getY() - ((this.x-this.y)/2);
            if(x_tmp < 0){
                x_tmp = 0;
            }
            if(x_tmp + this.getCurrentX() >= b.getNbRow()) {
                x_tmp = b.getNbRow() - (this.getCurrentX());
            }
            if(y_tmp < 0){
                y_tmp = 0;
            }
            if(y_tmp + this.getCurrentY() >= b.getNbCol()) {
                y_tmp = b.getNbCol() - (this.getCurrentY());
            }
            return new Point(x_tmp, y_tmp);
        }
    }

    /**
     * Permet d'obtenir la forme actuelle de la piece
     * @return un tableau de booleens representant la piece
     */
    public boolean[][] getCurrentPieceValue(){
        boolean[][] tmp;

        switch (this.state){
            case 0:
                return this.getPieceValue();
            case 1:
                tmp = new boolean[this.y][this.x];
                for(int i = 0; i < this.x; i++){
                    for (int j = 0; j < this.y; j++){
                        tmp[this.y - (this.y - j)][this.x - i - 1] = this.pieceValue[i][j];
                    }
                }
                return tmp;
            case 2:
                tmp = new boolean[this.x][this.y];
                for(int i = 0; i < this.x; i++){
                    for (int j = 0; j < this.y; j++){
                        tmp[this.x - i - 1][this.y - j - 1] = this.pieceValue[i][j];
                    }
                }
                return tmp;
            case 3:
                tmp = new boolean[this.y][this.x];
                for(int i = 0; i < this.x; i++){
                    for (int j = 0; j < this.y; j++){
                        tmp[this.y - j - 1][this.x - (this.x - i)] = this.pieceValue[i][j];
                    }
                }
                return tmp;
            default:
                return null;
        }
    }

    /**
     * Change l'etat de la piece pour indiquer la rotation de celle-ci
     * @param dir la direction de la rotation
     * @param b la grille du jeu
     */
    public void rotate(int dir, Board b) {
        //System.out.println(state);
        Point current = this.getCurrentPoint(b);
        int tmp = this.getState();

        clearPiece(b, current);

        if (dir == 1) { // RIGHT
            if(state == 3)
                this.state = 0;
            else
                this.state++;
        }
        else { // LEFT
            if(this.state == 0)
                this.state = 3;
            else
                this.state--;
        }

        if(this.isCollision(b)){
            this.state = tmp;
            //System.out.println(state);
        } else {
            b.initBoard();
        }
        b.putPiecesOnBoard();
    }

    /**
     * Efface la piece de la grille lors d'un changement d'etat ou de position
     * @param b la grille du jeu
     * @param current le point actuel par ou commence la piece a effacer
     */
    private void clearPiece(Board b, Point current) {
        for (int i = current.getX(); i < current.getX() + this.getCurrentX(); i++){
            for (int j = current.getY(); j < current.getY() + this.getCurrentY(); j++){
                //System.out.print("pieceValue="+this.getCurrentPieceValue()[i - current.getX()][j - current.getY()] + " ");
                if (this.getCurrentPieceValue()[i - current.getX()][j - current.getY()]) {
                    //System.out.println("i : " + i + "; j : " + j);
                    Point p = b.getPuzzle().get(j + (i * b.getNbRow()));
                    p.setSet(false);
                    p.setCh(".");
                }
                //System.out.print("board=>"+b.getPuzzle().get(j + (i * b.getNbRow())).getSet() + " ");
            }
        }
    }


    /**
     * Verifie si il y a une collision avec une eutre piece lors d'une rotation ou d'un deplacement
     * @param b la grille du jeu
     * @return true si il y a collision
     */
    public boolean isCollision(Board b){
        Point current = this.getCurrentPoint(b);
        //System.out.println(current);
        for (int i = current.getX(); i < current.getX() + this.getCurrentX(); i++){
            for (int j = current.getY(); j < current.getY() + this.getCurrentY(); j++){
                //System.out.println(j + " + " + i + " * " + b.getNbRow());
                //System.out.println("i = " + current.getX() + "; j = " + current.getY());
                //System.out.println(this.getCurrentPieceValue()[i - current.getX()][j - current.getY()] + " && " + b.getPuzzle().get(j + (i * b.getNbRow())).getCh());
                if(this.getCurrentPieceValue()[i - current.getX()][j - current.getY()] && b.getPuzzle().get(j + (i * b.getNbRow())).getSet()){
                    //System.out.println("COLLISION");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Deplace une piece en fontion d'une direction indiquee
     * @param b la grille du jeu
     * @param dir la direction lors du deplacement
     */
    public boolean move(Board b, String dir){
        Point current = this.getCurrentPoint(b);
        Point clone = this.point.clone();
        boolean moved = true;
        // On efface la piece de la grille
        clearPiece(b, current);

        //System.out.println(current);
        switch (dir){
            case "up":
                if(current.getX() - 1 >= 0)
                    this.getPoint().setX(this.point.getX() - 1);
                break;
            case "down":
                if(current.getX() + this.getCurrentX() < b.getNbRow())
                    this.getPoint().setX(this.point.getX() + 1);
                break;
            case "right":
                if(current.getY() + this.getCurrentY() < b.getNbCol())
                    this.getPoint().setY(this.point.getY() + 1);
                break;
            case "left":
                if(current.getY() - 1 >= 0)
                    this.getPoint().setY(this.point.getY() - 1);
                break;
        }

        //System.out.println(current);

        if(this.isCollision(b)){
            this.setPoint(clone);
            moved = false;
        } else {
            b.initBoard();
            b.setCurrentMoves(b.getCurrentMoves() + 1);
        }
        b.putPiecesOnBoard();
        return moved;
    }

    public ArrayList<Move> getValidMoves(Board b){
        ArrayList<Move> validMoves = new ArrayList<>();
        Point tmp = this.point.clone();
        if(this.move(b, "up")){
            validMoves.add(new Move(this, "up"));
        }
        if(this.move(b, "down")){
            validMoves.add(new Move(this, "down"));
        }
        if(this.move(b, "left")){
            validMoves.add(new Move(this, "left"));
        }
        if(this.move(b, "right")){
            validMoves.add(new Move(this, "right"));
        }
        this.point = tmp;
        b.initBoard();
        b.putPiecesOnBoard();
        return validMoves;
    }

    /**
     * Permet d'obtenir la forme d'une instance d'une sous classe de piece
     * @return la forme de la piece
     */
    public abstract String getForm();

    @Override
    public String toString() {
        return this.getForm() + "("+this.getCh()+") => Press " + this.getCh() + "; ";
    }
}
