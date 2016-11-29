/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Nasan
 */
public class Sudoku {

    /**
     * @param args the command line arguments
     */
    private int[][] grid = {
        {3, 0, 6, 5, 0, 8, 4, 0, 0},
        {5, 2, 0, 0, 0, 0, 0, 0, 0},
        {0, 8, 7, 0, 0, 0, 0, 3, 1},
        {0, 0, 3, 0, 1, 0, 0, 8, 0},
        {9, 0, 0, 8, 6, 3, 0, 0, 5},
        {0, 5, 0, 0, 9, 0, 6, 0, 0},
        {1, 3, 0, 0, 0, 0, 2, 5, 0},
        {0, 0, 0, 0, 0, 0, 0, 7, 4},
        {0, 0, 5, 2, 0, 6, 3, 0, 0}};

    private List<Position> caseToFill = new ArrayList<Position>();
    private boolean inCol[][] = new boolean[9][9];
    private boolean inRow[][] = new boolean[9][9];
    private boolean inBox[][][] = new boolean[9][9][9];

    public static void main(String[] args) {
        // TODO code application logic here
        Sudoku a = new Sudoku();
        boolean b = a.solveSudoku();
        a.printGrid();
    }

    private boolean checkEmptyCase() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    caseToFill.add(getPosition(row, col));
                } else {
                    int num = grid[row][col];
                    if (isSafe(row, col, num)) {
                        inCol[col][num - 1] = true;
                        inRow[row][num - 1] = true;
                        inBox[row - row % 3][col - col % 3][num - 1] = true;
                    } else {
                        return false;
                    }
                }
            }
        }
        //sort list of position
        Collections.sort(caseToFill, (a, b) -> Integer.compare(a.getValue(), b.getValue()));
        return true;
    }

    private Position getPosition(int row, int col) {
        int rv = 0;
        int cv = 0;
        int bv = 0;
        for (int i = 3; i < 9; i++) {
            if (grid[row][i] == 0) {
                rv++;
            }
        }
        for (int i = 3; i < 9; i++) {
            if (grid[i][col] == 0) {
                cv++;
            }
        }
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i + startRow][j + startCol] == 0) {
                    bv++;
                }
            }
        }
        return new Position(row, col, rv + bv + cv);
    }

    boolean isSafe(int row, int col, int num) {
        return !inCol[col][num - 1] && !inRow[row][num - 1] && !inBox[row - row % 3][col - col % 3][num - 1];
        // return !usedInRow(row, num) && !usedInCol(col, num) && !usedInBox(row - row % 3, col - col % 3, num);
    }

    public boolean solveSudoku() {
        return checkEmptyCase() && solveSudoku(0);
    }

    boolean solveSudoku(int pos) {
        if (caseToFill.size() == pos) {
            return true;
        }
        Position p = caseToFill.get(pos);
        int row = p.getRow();
        int col = p.getCol();
        for (int num = 1; num <= 9; num++) {
            // if looks promising
            if (isSafe(row, col, num)) {
                // make tentative assignment
                grid[row][col] = num;
                inCol[col][num - 1] = true;
                inRow[row][num - 1] = true;
                inBox[row - row % 3][col - col % 3][num - 1] = true;
                // return, if success, yay!
                if (solveSudoku(pos + 1)) {
                    return true;
                }

                // failure, unmake & try again
                grid[row][col] = 0;
                inCol[col][num - 1] = false;
                inRow[row][num - 1] = false;
                inBox[row - row % 3][col - col % 3][num - 1] = false;
            }
        }
        return false; // this triggers backtracking

    }

    public void printGrid() {
        for (int row = 0; row < grid[0].length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                System.out.print(grid[row][col]);
            }
            System.out.println();
        }
    }

}
