package Pieces;

public interface IPieceFactory {
    
    public Piece getPawn();
    public Piece getRook();
    public Piece getKing();
    public Piece getQueen();
    public Piece getKnight();
    public Piece getBishop();
}
