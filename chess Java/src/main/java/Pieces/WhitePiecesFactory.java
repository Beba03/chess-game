package Pieces;

import Core.Color;

public class WhitePiecesFactory implements IPieceFactory {

    @Override
    public Piece getPawn() {
        return new Pawn(Color.White);
    }

    @Override
    public Piece getRook() {
        return new Rook(Color.White);
    }

    @Override
    public Piece getKing() {
        return new King(Color.White);
    }

    @Override
    public Piece getQueen() {
        return new Queen(Color.White);
    }

    @Override
    public Piece getKnight() {
        return new Knight(Color.White);
    }

    @Override
    public Piece getBishop() {
        return new Bishop(Color.White);
    }

}
