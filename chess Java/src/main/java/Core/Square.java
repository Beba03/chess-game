package Core;

import Pieces.Piece;

public class Square {

    private final int row;
    private final int column;
    private boolean occupied;
    private Piece piece = null;

    public Square(int row, int column) {
        this.row = row;
        this.column = column;
        occupied = false;
    }

    public Square(int row, int column, Piece piece) {
        this.row = row;
        this.column = column;
        occupied = true;
        this.piece = piece;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
        if (occupied == false) {
            this.setPiece(null);
        }
    }

    public void printSquare() {
        Columns col = null;
        switch (this.column) {
            case 0 ->
                col = Columns.A;
            case 1 ->
                col = Columns.B;
            case 2 ->
                col = Columns.C;
            case 3 ->
                col = Columns.D;
            case 4 ->
                col = Columns.E;
            case 5 ->
                col = Columns.F;
            case 6 ->
                col = Columns.G;
            case 7 ->
                col = Columns.H;
        }

        if (isOccupied()) {
            System.out.print(col + "" + (8 - this.row) + "(" + piece.getColor() + " " + piece.getType() + ") ");
        } else {
            System.out.print(col + "" + (8 - this.row) + "             ");
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        if (piece != null) {
            setOccupied(true);
        }
    }

}
