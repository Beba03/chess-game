package Pieces;

import Core.Square;
import Core.Color;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color);
        type = Type.Knight;
    }

    @Override
    public boolean isValidMove(Square CURRENT_SQUARE, Square DESTINATION_SQUARE) {
        if (move(CURRENT_SQUARE,DESTINATION_SQUARE)) {
            return true;
        }
        return false;
    }

    public boolean move(Square CURRENT_SQUARE,Square DESTINATION_SQUARE) {
        int current_Column = CURRENT_SQUARE.getColumn();
        int next_Column = DESTINATION_SQUARE.getColumn();
        int current_Row = CURRENT_SQUARE.getRow();
        int next_Row = DESTINATION_SQUARE.getRow();
        if (current_Row + 1 == next_Row && current_Column + 2 == next_Column) {
            return true;
        }
        if (current_Row - 1 == next_Row && current_Column + 2 == next_Column) {
            return true;
        }       
        if (current_Row + 1 == next_Row && current_Column - 2 == next_Column) {
            return true;
        }
        if (current_Row - 1 == next_Row && current_Column - 2 == next_Column) {
            return true;
        }
        if (current_Row + 2 == next_Row && current_Column - 1 == next_Column) {
            return true;
        }
        if (current_Row + 2 == next_Row && current_Column + 1 == next_Column) {
            return true;
        }
        if (current_Row - 2 == next_Row && current_Column - 1 == next_Column) {
            return true;
        }
        if (current_Row - 2 == next_Row && current_Column + 1 == next_Column) {
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean canEat(Square CURRENT_SQUARE, Square DESTINATION_SQUARE) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
