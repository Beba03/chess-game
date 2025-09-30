package Pieces;

import Core.Square;
import Core.Color;

public class King extends Piece {

    public King(Color color) {
        super(color);
        type = Type.King;
    }

    @Override
    public boolean isValidMove(Square CURRENT_SQUARE, Square DESTINATION_SQUARE) {
        if (move1(CURRENT_SQUARE,DESTINATION_SQUARE) || move2(CURRENT_SQUARE,DESTINATION_SQUARE)) {
            return true;
        }
        return false;
    }

    public boolean move1(Square CURRENT_SQUARE,Square DESTINATION_SQUARE) {
        if (Math.abs(CURRENT_SQUARE.getColumn() - DESTINATION_SQUARE.getColumn()) > 1 ||
                Math.abs(CURRENT_SQUARE.getRow() - DESTINATION_SQUARE.getRow()) > 1) {
            return false;
        }
        return true;
    }
    
    public boolean move2(Square CURRENT_SQUARE,Square DESTINATION_SQUARE) {
        if (CURRENT_SQUARE.getRow() == DESTINATION_SQUARE.getRow() &&
                Math.abs(CURRENT_SQUARE.getColumn() - DESTINATION_SQUARE.getColumn()) == 2 && isMoved()==false) {
            return true;
        }
        return false;
    }
    
    @Override
    public boolean canEat(Square CURRENT_SQUARE, Square DESTINATION_SQUARE) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
