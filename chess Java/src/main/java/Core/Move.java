package Core;

import Pieces.Piece;

public class Move {
    private final Square current_square;
    private Square eatenPiece_square;
    private Piece eaten_Piece;
    private final Square next_square;
    private Piece promoted_pawn;
    private boolean promotion;
    private boolean enpassant;
    private boolean castle;
    private Square rook_previous_square;
    private Square rook_next_square;
    private GameStatus gameStatus;
    private boolean change_movement;

    public Move(Square current_square, Square next_square) {
        this.current_square = current_square;
        this.next_square = next_square;
        this.eatenPiece_square =null;
        this.eaten_Piece = null;
        promotion=false;
        enpassant=false;
        castle=false;
        rook_previous_square=null;
        rook_next_square=null;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Piece getEaten_Piece() {
        return eaten_Piece;
    }

    public void setEaten_Piece(Piece eaten_Piece) {
        this.eaten_Piece = eaten_Piece;
    }

    public Square getCurrent_square() {
        return current_square;
    }

    public Square getNext_square() {
        return next_square;
    }

    public Square getEatenPiece_square() {
        return eatenPiece_square;
    }

    public void setEatenPiece_square(Square eatenPiece_square) {
        this.eatenPiece_square = eatenPiece_square;
    }

    public boolean isEnpassant() {
        return enpassant;
    }

    public void setEnpassant(boolean enpassant) {
        this.enpassant = enpassant;
    }

    public boolean isCastle() {
        return castle;
    }

    public void setCastle(boolean castle) {
        this.castle = castle;
    }

    public Square getRook_previous_square() {
        return rook_previous_square;
    }

    public void setRook_previous_square(Square rook_previous_square) {
        this.rook_previous_square = rook_previous_square;
    }

    public Square getRook_next_square() {
        return rook_next_square;
    }

    public void setRook_next_square(Square rook_next_square) {
        this.rook_next_square = rook_next_square;
    }

    public boolean isPromotion() {
        return promotion;
    }

    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }

    public boolean isChange_movement() {
        return change_movement;
    }

    public void setChange_movement(boolean change_pawn_movement) {
        this.change_movement = change_pawn_movement;
    }

    void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Piece getPromoted_pawn() {
        return promoted_pawn;
    }

    public void setPromoted_pawn(Piece promoted_pawn) {
        this.promoted_pawn = promoted_pawn;
    }

    
}
