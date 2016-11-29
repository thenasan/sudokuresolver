/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sudoku;

/**
 *
 * @author Nasan
 */
public class Position {
    int row;
    int col;
    int value;
    public Position(int row,int col,int value){
        this.row=row;
        this.col=col;
        this.value=value;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getValue() {
        return value;
    }
    
    
}
