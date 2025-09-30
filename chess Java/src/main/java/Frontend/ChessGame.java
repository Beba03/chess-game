package Frontend;

import Pieces.Piece;
import Core.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChessGame {

    public static final Chess Game = Chess.getInstance();
    public static Square SQUARE_1, SQUARE_2;
    public static boolean SQUARE_1_PRESSED = false, Expect_Promotion = false;
    public static ArrayList<Move> validMoves = new ArrayList<>();
    public static String PROMOTE_TO = null;
    public static Image[] Imgs;

    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        /*Scaling images to fit 80 by 80 Square */
        Imgs = new Image[12];
        int ind = 0;
        for (Image img : Pictures.imgs) {Imgs[ind++] = img.getScaledInstance(80,80,Image.SCALE_SMOOTH);}
        
        JPanel jp = new JPanel() {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2D = (Graphics2D) g;
                Color LIGHT_GREEN = new Color(230, 235, 208);
                Color DARK_GREEN = new Color(115, 148, 85);
                Color Red = Color.decode("#c1121f");

                //painting the alternating squares 
                drawSquares(g2D,this,LIGHT_GREEN,DARK_GREEN); 
                
                //g2D.fillRect( (7-col)*80  , (7-row)*80, 81, 80);
                //Color.decode("#f4f680")bbcc44
                //Highlighting valid-moves
                validMoves=Game.check(validMoves);
                if (SQUARE_1_PRESSED && !validMoves.isEmpty()) {
                    for (Move square : validMoves) {
                        int row = square.getNext_square().getRow();
                        int col = square.getNext_square().getColumn();
                        g2D.setColor(Color.decode("#bbcc44"));
                        //g2D.se
                        if (Game.getPlayer_Turn() == Core.Color.White) {
                            g2D.fillOval((col * 80)+10, (row * 80)+10, 60, 60);
                        } else {
                            g2D.fillOval( ((7-col)*80)+10 , ((7-row)*80)+10, 60, 60);
                        }
                    }
                }

                //KINGS IN CHECK/GAME ENDED
                if (Game.getPlayer_Turn() == Core.Color.White) {
                    if (Game.getGameStatus() == GameStatus.WHITE_IN_CHECK || Game.getGameStatus() == GameStatus.BLACK_WON) {
                        g2D.setColor(Red);
                        Square s = Game.getKing(Core.Color.White);
                        g2D.fillRect(s.getColumn() * 80, s.getRow() * 80, 81, 80);
                    }
                    if (Game.getGameStatus() == GameStatus.BLACK_IN_CHECK || Game.getGameStatus() == GameStatus.WHITE_WON) {
                        g2D.setColor(Red);
                        Square s = Game.getKing(Core.Color.Black);
                        g2D.fillRect((s.getColumn()) * 80, (s.getRow()) * 80, 81, 80);
                    }
                }
                if (Game.getPlayer_Turn() == Core.Color.Black) {
                    if (Game.getGameStatus() == GameStatus.WHITE_IN_CHECK || Game.getGameStatus() == GameStatus.BLACK_WON) {
                        g2D.setColor(Red);
                        Square s = Game.getKing(Core.Color.White);
                        g2D.fillRect((7 - s.getColumn()) * 80, (7 - s.getRow()) * 80, 81, 80);
                    }
                    if (Game.getGameStatus() == GameStatus.BLACK_IN_CHECK || Game.getGameStatus() == GameStatus.WHITE_WON) {
                        g2D.setColor(Red);
                        Square s = Game.getKing(Core.Color.Black);
                        g2D.fillRect((7 - s.getColumn()) * 80, (7 - s.getRow()) * 80, 81, 80);
                    }
                }
                
                //drawing the pieces images on squares
                drawPieces(g2D,this);
            }
        };
        frame.add(jp, BorderLayout.CENTER);

        frame.addMouseListener(new MouseListener() {     
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Game.isGameOver()) {return;}
                int mousey, mousex;
                if (Game.getPlayer_Turn() == Core.Color.White) {
                    mousex = (e.getY() / 80) - 1;
                    mousey = (e.getX() / 80) - 1;
                } else {
                    mousex = (7 - e.getY() / 80) + 1;
                    mousey = (7 - e.getX() / 80) + 1;
                }
                Square Clicked_Square = Game.getGameBoard().getSquare(mousex, mousey);

                if (!SQUARE_1_PRESSED) {
                    SQUARE_1 = Clicked_Square;

                    if (SQUARE_1.getPiece() == null || SQUARE_1.getPiece().getColor() != Game.getPlayer_Turn()) {
                        SQUARE_1 = null;
                    } else {
                        if (SQUARE_1.getPiece().getType() == Pieces.Type.Pawn && SQUARE_1.getRow() == 1 && SQUARE_1.getPiece().getColor() == Core.Color.White && Game.getPlayer_Turn() == Core.Color.White) {
                            SQUARE_1_PRESSED = true;
                            validMoves = Game.getAllValidMovesFromSquare(SQUARE_1);
                            SQUARE_1_PRESSED = true;
                            Expect_Promotion = true;
                        } else if (SQUARE_1.getPiece().getType() == Pieces.Type.Pawn && SQUARE_1.getRow() == 6 && SQUARE_1.getPiece().getColor() == Core.Color.Black && Game.getPlayer_Turn() == Core.Color.Black) {
                            SQUARE_1_PRESSED = true;
                            validMoves = Game.getAllValidMovesFromSquare(SQUARE_1);
                            SQUARE_1_PRESSED = true;
                            Expect_Promotion = true;
                        } else {
                            if (isInCheck()) {
                                if (!Contains1(SQUARE_1)) {
                                    SQUARE_1 = null;
                                    frame.repaint();
                                    return;
                                } else {
                                    SQUARE_1_PRESSED = true;
                                    validMoves = Game.getSaving_moves_from_square(SQUARE_1);
                                    frame.repaint();
                                    return;
                                }
                            }
                            SQUARE_1_PRESSED = true;
                            validMoves = Game.getAllValidMovesFromSquare(SQUARE_1);
                            //validMoves = Game.check(validMoves);
                        }
                    }
                } else {
                    SQUARE_2 = Clicked_Square;
                    if (!Contains2(SQUARE_2)) {
                    } else {
                        if (Expect_Promotion) {
                            Promotion_Frame(frame, SQUARE_1, SQUARE_2);
                            frame.setEnabled(false);
                            Expect_Promotion = false;
                            SQUARE_2 = null;
                            SQUARE_1 = null;
                            SQUARE_1_PRESSED = false;
                            validMoves.clear();
                            return;
                        }
                        Game.getMoves(new Move(SQUARE_1, SQUARE_2), PROMOTE_TO);
                    }
                    Expect_Promotion = false;
                    SQUARE_2 = null;
                    SQUARE_1 = null;
                    SQUARE_1_PRESSED = false;
                    validMoves.clear();
                }
                
                if(!ChessGame.Game.getMoves().isEmpty())
                    frame.Undo.setEnabled(true);
                frame.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        frame.setVisible(true);
    } 
    
    public static void drawSquares(Graphics2D g2D, JPanel jp, Color C1, Color C2) {
        boolean green = true;
        g2D.setColor(C1);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (green) {
                    g2D.setColor(C1);
                } else {
                    g2D.setColor(C2);
                }
                g2D.fillRect(i * 80, j * 80, 80, 80);
                green = !green;
            }
            green = !green;
        }
    }
    
    public static void drawPieces(Graphics2D g2D, JPanel jp) {
        int index;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                Square sq = Game.getGameBoard().getSquare(i, j);
                Piece p = sq.getPiece();
                if (p == null) {
                    continue;
                }
                if (p.getColor() == Core.Color.White) {
                    index = 0;
                } else {
                    index = 6;
                }
                if (null != p.getType()) {
                    switch (p.getType()) {
                        case Rook ->
                            index += 0;
                        case Knight ->
                            index += 1;
                        case Bishop ->
                            index += 2;
                        case Queen ->
                            index += 3;
                        case King ->
                            index += 4;
                        case Pawn ->
                            index += 5;
                        default -> {
                        }
                    }
                }
                if (Game.getPlayer_Turn() == Core.Color.White) {
                    g2D.drawImage(Imgs[index], sq.getColumn() * 80, sq.getRow() * 80, jp);
                } else {
                    g2D.drawImage(Imgs[index], (7 - sq.getColumn()) * 80, (7 - sq.getRow()) * 80, jp);
                }
            }
        }
    }
    
    public static boolean Contains1(Square s){
        for (Move move : Game.getSaving_moves()) {
            if (move.getCurrent_square() == s) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean Contains2(Square s){
        for (Move move : validMoves) {
            if (move.getNext_square() == s) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isInCheck(){
        if (Game.getGameStatus() == GameStatus.WHITE_IN_CHECK) {
            return true;
        }
        if (Game.getGameStatus() == GameStatus.BLACK_IN_CHECK) {
            return true;
        }

        return false;
    }
    
    public static void Promotion_Frame(MyFrame frame,Square s1,Square s2) {
        JFrame jf2 = new JFrame();
        jf2.setBounds(378, 82, 75, 75 * 4);
        jf2.setUndecorated(true);
        JPanel jp2 = new JPanel() {
            @Override
            public void paint(Graphics g) {
                int index;
                if (Game.getPlayer_Turn() == Core.Color.White) {
                    index = 0;
                } else {
                    index = 6;
                }
                int j = 0;
                for (int i = index; i < index + 4; i++) {
                    g.setColor(Color.decode("#302e2b"));
                    g.fillRect(0, 75 * j, 75, 75);
                    g.drawImage(Imgs[i], 0, 75 * j, this);
                    j++;
                }
            }
        };
        jf2.add(jp2);

        jf2.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                jf2.setVisible(false);
                if (e.getY() > 0 && e.getY() < 75) {
                    PROMOTE_TO = "r";
                    Game.getMoves(new Move(s1, s2), PROMOTE_TO);
                    PROMOTE_TO = null;
                    frame.repaint();
                    frame.setEnabled(true);
                    frame.toFront();
                    jf2.dispose();
                    
                } else if (e.getY() > 75 && e.getY() < 75 * 2) {
                    PROMOTE_TO="k";
                    Game.getMoves(new Move(s1, s2), PROMOTE_TO);
                    PROMOTE_TO = null;
                    frame.repaint();
                    frame.setEnabled(true);
                    frame.toFront();
                    jf2.dispose();
                    
                } else if (e.getY() > 75 * 2 && e.getY() < 75 * 3) {
                    PROMOTE_TO="B";
                    Game.getMoves(new Move(s1, s2), PROMOTE_TO);
                    PROMOTE_TO = null;
                    frame.repaint();
                    frame.setEnabled(true);
                    frame.toFront();
                    jf2.dispose();
                    
                } else if (e.getY() > 75 * 3 && e.getY() < 75 * 4) {
                    PROMOTE_TO="Q";
                    Game.getMoves(new Move(s1, s2), PROMOTE_TO);
                    PROMOTE_TO = null;
                    frame.repaint();
                    frame.setEnabled(true);
                    frame.toFront();
                    jf2.dispose();
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        jf2.setVisible(true);
    }
}
