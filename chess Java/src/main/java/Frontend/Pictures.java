package Frontend;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Pictures {
    
    protected static final ImageIcon white_pawn= new ImageIcon("PiecesImages\\WhitePawn.png"); 
    protected static final ImageIcon white_bishop= new ImageIcon("PiecesImages\\WhiteBishop.png");
    protected static final ImageIcon white_knight= new ImageIcon("PiecesImages\\WhiteKnight.png");
    protected static final ImageIcon white_rook= new ImageIcon("PiecesImages\\WhiteRook.png");
    protected static final ImageIcon white_queen= new ImageIcon("PiecesImages\\WhiteQueen.png");
    protected static final ImageIcon white_king= new ImageIcon("PiecesImages\\WhiteKing.png");
    
    protected static final ImageIcon black_pawn= new ImageIcon("PiecesImages\\BlackPawn.png");
    protected static final ImageIcon black_bishop= new ImageIcon("PiecesImages\\BlackBishop.png");
    protected static final ImageIcon black_knight= new ImageIcon("PiecesImages\\BlackKnight.png");
    protected static final ImageIcon black_rook= new ImageIcon("PiecesImages\\BlackRook.png");
    protected static final ImageIcon black_queen= new ImageIcon("PiecesImages\\BlackQueen.png");
    protected static final ImageIcon black_king= new ImageIcon("PiecesImages\\BlackKing.png");
    protected static final Image[] imgs = {white_rook.getImage(), white_knight.getImage(), white_bishop.getImage(), white_queen.getImage(),white_king.getImage(), white_pawn.getImage(), black_rook.getImage(), black_knight.getImage(), black_bishop.getImage(), black_queen.getImage(), black_king.getImage(), black_pawn.getImage()};
}