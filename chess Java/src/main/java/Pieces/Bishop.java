package Pieces;

import Core.Square;
import Core.Color;

public class Bishop extends Piece {

    public Bishop(Color color) {
        super(color);
        type = Type.Bishop;
    }

    @Override
    public boolean isValidMove(Square CURRENT_SQUARE, Square DESTINATION_SQUARE) {
        if (move(CURRENT_SQUARE,DESTINATION_SQUARE)) {
            return true;
        }
        return false;
    }

    public boolean move(Square CURRENT_SQUARE,Square DESTINATION_SQUARE) {
        if (Math.abs(CURRENT_SQUARE.getColumn() - DESTINATION_SQUARE.getColumn())
                == Math.abs(CURRENT_SQUARE.getRow() - DESTINATION_SQUARE.getRow())) {
            return true;
        }
        return false;
    }
    
    @Override
    public boolean canEat(Square CURRENT_SQUARE, Square DESTINATION_SQUARE) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
