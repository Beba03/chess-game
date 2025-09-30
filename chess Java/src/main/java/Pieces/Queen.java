package Pieces;

import Core.Square;
import Core.Color;

public class Queen extends Piece {
    
    public Queen(Color color) {
        super(color);
        type = Type.Queen;
    }

    @Override
    public boolean isValidMove(Square CURRENT_SQUARE, Square DESTINATION_SQUARE) {
        if (move_like_Bishop(CURRENT_SQUARE,DESTINATION_SQUARE) || move_Horizontally(CURRENT_SQUARE,DESTINATION_SQUARE)
                || move_Vertically(CURRENT_SQUARE,DESTINATION_SQUARE)) {
            return true;
        }
        return false;
    }

    public boolean move_like_Bishop(Square CURRENT_SQUARE,Square DESTINATION_SQUARE) {
        if (Math.abs(CURRENT_SQUARE.getColumn() - DESTINATION_SQUARE.getColumn())
                == Math.abs(CURRENT_SQUARE.getRow() - DESTINATION_SQUARE.getRow())) {
            return true;
        }
        return false;
    }

    public boolean move_Horizontally(Square CURRENT_SQUARE,Square DESTINATION_SQUARE) {
        if (CURRENT_SQUARE.getRow() != DESTINATION_SQUARE.getRow()) {
            return false;
        } else if (DESTINATION_SQUARE.getColumn() == CURRENT_SQUARE.getColumn()) {
            return false;
        }
        return true;
    }

    public boolean move_Vertically(Square CURRENT_SQUARE,Square DESTINATION_SQUARE) {
        if (CURRENT_SQUARE.getColumn() != DESTINATION_SQUARE.getColumn()) {
            return false;
        } else if (DESTINATION_SQUARE.getRow() == CURRENT_SQUARE.getRow()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canEat(Square CURRENT_SQUARE, Square DESTINATION_SQUARE) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
