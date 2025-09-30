package Core;

import Pieces.*;
import java.util.ArrayList;

public class Game_Board {

    Square[][] square;

    public Game_Board() {
        WhitePiecesFactory wp = new WhitePiecesFactory();
        BlackPiecesFactory bp = new BlackPiecesFactory();
        square = new Square[][]{
            {new Square(0,0,bp.getRook()),new Square(0,1,bp.getKnight()),new Square(0,2,bp.getBishop()),new Square(0,3,bp.getQueen()),new Square(0,4,bp.getKing()),new Square(0,5,bp.getBishop()),new Square(0,6,bp.getKnight()),new Square(0,7,bp.getRook())},
            {new Square(1,0,bp.getPawn()),new Square(1,1,bp.getPawn()),new Square(1,2,bp.getPawn()),new Square(1,3,bp.getPawn()),new Square(1,4,bp.getPawn()),new Square(1,5,bp.getPawn()),new Square(1,6,bp.getPawn()),new Square(1,7,bp.getPawn())},
            {new Square(2,0),new Square(2,1),new Square(2,2),new Square(2,3),new Square(2,4),new Square(2,5),new Square(2,6),new Square(2,7)},
            {new Square(3,0),new Square(3,1),new Square(3,2),new Square(3,3),new Square(3,4),new Square(3,5),new Square(3,6),new Square(3,7)},
            {new Square(4,0),new Square(4,1),new Square(4,2),new Square(4,3),new Square(4,4),new Square(4,5),new Square(4,6),new Square(4,7)},
            {new Square(5,0),new Square(5,1),new Square(5,2),new Square(5,3),new Square(5,4),new Square(5,5),new Square(5,6),new Square(5,7)},
            {new Square(6,0,wp.getPawn()),new Square(6,1,wp.getPawn()),new Square(6,2,wp.getPawn()),new Square(6,3,wp.getPawn()),new Square(6,4,wp.getPawn()),new Square(6,5,wp.getPawn()),new Square(6,6,wp.getPawn()),new Square(6,7,wp.getPawn())},
            {new Square(7,0,wp.getRook()),new Square(7,1,wp.getKnight()),new Square(7,2,wp.getBishop()),new Square(7,3,wp.getQueen()),new Square(7,4,wp.getKing()),new Square(7,5,wp.getBishop()),new Square(7,6,wp.getKnight()),new Square(7,7,wp.getRook())}
        };
    }

    protected Square getSquare(String square_name){
        int column=getColumnIndex(square_name.charAt(0));
        int row=Character.getNumericValue(square_name.charAt(1));

        return square[8-row][column];
    }
    public Square getSquare(int row,int column){
        return square[row][column];
    }
    
    private int getColumnIndex(char column) {
        switch (column) {
            case 'a', 'A' -> {
                return Columns.A.Index;
            }
            case 'b', 'B' -> {
                return Columns.B.Index;
            }
            case 'c', 'C' -> {
                return Columns.C.Index;
            }
            case 'd', 'D' -> {
                return Columns.D.Index;
            }
            case 'e', 'E' -> {
                return Columns.E.Index;
            }
            case 'f', 'F' -> {
                return Columns.F.Index;
            }
            case 'g', 'G' -> {
                return Columns.G.Index;
            }
            case 'h', 'H' -> {
                return Columns.H.Index;               
            }
        }
        return -1;
    }
    
    protected ArrayList<Square> get_Squares_between(Square current_Square, Square next_Square) {
        ArrayList<Square> squares = new ArrayList<>();

        if (current_Square.getColumn() == next_Square.getColumn()) {
            int starting_row;
            int ending_row;
            int column = current_Square.getColumn();
            if (current_Square.getRow() < next_Square.getRow()) {
                starting_row = current_Square.getRow()+1;
                ending_row = next_Square.getRow();
            } else {
                ending_row = current_Square.getRow();
                starting_row = next_Square.getRow()+1;
            }
            for (int row = starting_row; row < ending_row; row++) {                
                squares.add(square[row][column]);
            }
            return squares;
        }

        if (current_Square.getRow() == next_Square.getRow()) {
            int starting_column;
            int ending_column;
            int row = current_Square.getRow();
            if (current_Square.getColumn() < next_Square.getColumn()) {
                starting_column = current_Square.getColumn()+1;
                ending_column = next_Square.getColumn();
            } else {
                ending_column = current_Square.getColumn();
                starting_column = next_Square.getColumn()+1;
            }
            for (int column = starting_column; column < ending_column; column++) {
                squares.add(square[row][column]);
            }
            return squares;
        }

        int starting_row;
        int ending_row;
        int starting_column;
        int ending_column;
        int f=0;        
        if (current_Square.getRow() < next_Square.getRow()) {
            f=f+1;
            starting_row = current_Square.getRow()+1;
            ending_row = next_Square.getRow();
            } else {
            f=f+2;
            ending_row = current_Square.getRow();
                starting_row = next_Square.getRow()+1;
            }
        if (current_Square.getColumn() < next_Square.getColumn()) {
            f = f+3;
            starting_column = current_Square.getColumn() + 1;
            ending_column = next_Square.getColumn() - 1;
        } else {
            f = f+4;
            ending_column = current_Square.getColumn() - 1;
            starting_column = next_Square.getColumn() + 1;
        }
        if (f == 5) {
            for (int row = starting_row, column = ending_column; row < ending_row; row++, column--) {
                squares.add(square[row][column]);
            }
        } else {
            for (int row = starting_row, column = starting_column; row < ending_row; row++, column++) {
                squares.add(square[row][column]);
            }
        }
        return squares;
    }
    
    public void printGameBoard() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                square[row][column].printSquare();
            }
            System.out.println();
        }
    }

}
