package Pieces;

import Core.Color;

public class BlackPiecesFactory implements IPieceFactory{

    @Override
    public Piece getPawn() {
        return new Pawn(Color.Black);
    }

    @Override
    public Piece getRook() {
        return new Rook(Color.Black);
    }

    @Override
    public Piece getKing() {
        return new King(Color.Black);
    }

    @Override
    public Piece getQueen() {
        return new Queen(Color.Black);
    }

    @Override
    public Piece getKnight() {
        return new Knight(Color.Black);
    }

    @Override
    public Piece getBishop() {
        return new Bishop(Color.Black);
    }
}
