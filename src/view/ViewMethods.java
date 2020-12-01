package view;

import model.Board;

public interface ViewMethods {

    public void setBoard(Board b);
    public void draw();
    public void modelUpdated();
}
