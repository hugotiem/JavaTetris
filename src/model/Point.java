package model;

public class Point {
    private int x;
    private int y;

    private boolean set;
    private String ch;

    public Point(int x, int y){
        this.x = x;
        this.y = y;

        this.set = false;
        this.ch = ".";
    }

    // GETTER
    public int getX() {return this.x;}
    public int getY() {return this.y;}
    public boolean getSet() {return set;}
    public String getCh() {return this.ch;}

    // SETTER
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public void setSet(boolean set) {this.set = set;}
    public void setCh(String ch) {this.ch = ch;}

    @Override
    public Point clone() {
        return new Point(this.x, this.y);
    }

    @Override
    public String toString(){
        return "["+this.x+", "+this.y+"]";
    }

}
