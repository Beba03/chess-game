package Pieces;

import Core.Color;
import Core.Square;

public abstract class Piece {
    
    private final Color color;
    private boolean moved;  
    protected Type type;

    public Piece(Color color) {
        this.moved = false;
        this.color = color;
    }
    
    public abstract boolean isValidMove(Square CURRENT_SQUARE, Square DESTINATION_SQUARE);

    public abstract boolean canEat(Square CURRENT_SQUARE, Square DESTINATION_SQUARE);
    
    public Color getColor() {
        return color;
    }
    
    public Type getType() {
        return type;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
    
}
