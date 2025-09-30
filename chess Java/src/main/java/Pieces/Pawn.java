package Pieces;

import Core.Square;
import Core.Color;

public class Pawn extends Piece {
    
    public Pawn(Color color) {
        super(color);
        type = Type.Pawn;
    }

    @Override
    public boolean isValidMove(Square CURRENT_SQUARE, Square DESTINATION_SQUARE) {
        if (move1(CURRENT_SQUARE,DESTINATION_SQUARE) || move2(CURRENT_SQUARE,DESTINATION_SQUARE)
                || eat_Right(CURRENT_SQUARE,DESTINATION_SQUARE) || eat_Left(CURRENT_SQUARE,DESTINATION_SQUARE)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canEat(Square CURRENT_SQUARE, Square DESTINATION_SQUARE) {
        if (eat_Right(CURRENT_SQUARE,DESTINATION_SQUARE) || eat_Left(CURRENT_SQUARE,DESTINATION_SQUARE)) {
            return true;
        }
        return false;
    }
    
    public boolean move1(Square CURRENT_SQUARE,Square DESTINATION_SQUARE) {
        if (CURRENT_SQUARE.getColumn() != DESTINATION_SQUARE.getColumn()) {
            return false;
        } else if (DESTINATION_SQUARE.getRow() - CURRENT_SQUARE.getRow() == -1 && getColor() == Color.White) {
            return true;
        } else if (DESTINATION_SQUARE.getRow() - CURRENT_SQUARE.getRow() == 1 && getColor() == Color.Black) {
            return true;
        }
        return false;
    }

    public boolean move2(Square CURRENT_SQUARE,Square DESTINATION_SQUARE) {
        if (CURRENT_SQUARE.getColumn() != DESTINATION_SQUARE.getColumn() || isMoved()) {
            return false;
        } else if (DESTINATION_SQUARE.getRow() - CURRENT_SQUARE.getRow() == -2 && isMoved() == false && getColor() == Color.White) {
            return true;
        } else if (DESTINATION_SQUARE.getRow() - CURRENT_SQUARE.getRow() == 2 && isMoved() == false && getColor() == Color.Black) {
            return true;
        }
        return false;
    }

    public boolean eat_Right(Square CURRENT_SQUARE,Square DESTINATION_SQUARE) {
        if (Math.abs(DESTINATION_SQUARE.getColumn() - CURRENT_SQUARE.getColumn()) != 1) {
            return false;
        } else if (DESTINATION_SQUARE.getRow() - CURRENT_SQUARE.getRow() == -1
                && DESTINATION_SQUARE.getColumn() - CURRENT_SQUARE.getColumn() == 1 && getColor() == Color.White) {
            return true;
        } else if (DESTINATION_SQUARE.getRow() - CURRENT_SQUARE.getRow() == 1
                && DESTINATION_SQUARE.getColumn() - CURRENT_SQUARE.getColumn() == -1 && getColor() == Color.Black) {
            return true;
        }
        return false;
    }
    
    public boolean eat_Left(Square CURRENT_SQUARE,Square DESTINATION_SQUARE) {
        if (Math.abs(DESTINATION_SQUARE.getColumn() - CURRENT_SQUARE.getColumn()) != 1) {
            return false;
        } else if (DESTINATION_SQUARE.getRow() - CURRENT_SQUARE.getRow() == -1
                && DESTINATION_SQUARE.getColumn() - CURRENT_SQUARE.getColumn() == -1 && getColor() == Color.White) {
            return true;
        } else if (DESTINATION_SQUARE.getRow() - CURRENT_SQUARE.getRow() == 1
                && DESTINATION_SQUARE.getColumn() - CURRENT_SQUARE.getColumn() == 1 && getColor() == Color.Black) {
            return true;
        }
        return false;
    }

}
