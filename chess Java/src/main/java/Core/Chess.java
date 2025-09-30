package Core;

import Pieces.*;
import java.util.ArrayList;
import java.util.Stack;

public class Chess {

    private static Chess instance;
    private final Game_Board game_Board;
    private Color player_Turn;
    private GameStatus gameStatus;
    private boolean enpassant;
    private boolean castling;
    private Move move;
    private final Stack<Move> Moves;
    private final ArrayList<Move> saving_moves;

    private Chess() {
        game_Board = new Game_Board();
        player_Turn = Color.White;
        gameStatus = GameStatus.IN_PROGRESS;
        enpassant = false;
        castling = false;
        Moves = new Stack<>();
        saving_moves = new ArrayList<>();
    }

    public static Chess getInstance() {
        if (instance == null) {
            instance = new Chess();
        }
        return instance;
    }

    public Color getPlayer_Turn() {
        return player_Turn;
    }

    public Game_Board getGameBoard() {
        return game_Board;
    }

    public Stack<Move> getMoves() {
        return Moves;
    }
    
    public ArrayList<Move> getSaving_moves_from_square(Square s) {
        ArrayList<Move> x = new ArrayList<>();
        for (Move move1 : saving_moves) {
            if (move1.getCurrent_square() == s) {
                x.add(move1);
            }
        }
        return x;
    }

    public ArrayList<Move> getSaving_moves() {
        return saving_moves;
    }

    public void printBoard() {
        game_Board.printGameBoard();
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void getMoves(Move move, String PROMOTE_TO) {
        if (isGameOver()) {
            System.out.println("Game already ended");
            return;
        }
        play(move.getCurrent_square(), move.getNext_square(), PROMOTE_TO);
    }

    private void play(Square current_Square, Square next_Square, String PROMOTE_TO) {
        if (current_Square.isOccupied() == false) {
            System.out.println("Invalid move");
            return;
        }
        if (current_Square.getPiece().getColor() != player_Turn) {
            System.out.println("Invalid move");
            return;
        }
        if (!isValidMove(current_Square, next_Square)) {
            System.out.println("Invalid move");
            return;
        }
        this.move = new Move(current_Square, next_Square);
        this.move.setGameStatus(GameStatus.IN_PROGRESS);
        movePiece(current_Square, next_Square);

        if (PROMOTE_TO != null) {
            this.move.setPromotion(true);
            this.move.setPromoted_pawn(next_Square.getPiece());
            promote_Pawn(next_Square, PROMOTE_TO);
        }
        updateGame(next_Square);
    }

    private void movePiece(Square current_Square, Square next_Square) {
        if (enpassant) {
            enpassantMove(current_Square, next_Square);
            return;
        }

        if (castling) {
            castlingtMove(current_Square, next_Square);
            return;
        }

        if (next_Square.isOccupied()) {
            this.move.setEaten_Piece(next_Square.getPiece());
            this.move.setEatenPiece_square(next_Square);
            System.out.println("Captured " + next_Square.getPiece().getType());
        }

        next_Square.setPiece(current_Square.getPiece());
        current_Square.setOccupied(false);

    }

    private void updateGame(Square next_Square) {
        Piece moved_piece = next_Square.getPiece();
        this.move.setGameStatus(gameStatus);

        if (moved_piece.getType() == Type.Pawn || moved_piece.getType() == Type.King || moved_piece.getType() == Type.Rook) {
            if (!moved_piece.isMoved()) {
                next_Square.getPiece().setMoved(true);
                this.move.setChange_movement(true);
            }
        }

        if (player_Turn == Color.White) {
            player_Turn = Color.Black;
        } else {
            player_Turn = Color.White;
        }

        if (player_Turn == Color.White && !isWhite_InCheck()) {
            if (!canWhite_escape()) {
                gameStatus = GameStatus.STALEMATE;
                System.out.println("Stalemate");
                Moves.push(this.move);
                return;
            }
        }

        if (player_Turn == Color.Black && !isBlack_InCheck()) {
            if (!canBlack_escape()) {
                gameStatus = GameStatus.STALEMATE;
                System.out.println("Stalemate");
                Moves.push(this.move);
                return;
            }
        }

        if (isInsufficient_Material()) {
            System.out.println("Insufficient Material");
            gameStatus = GameStatus.INSUFFICIENT_MATERIAL;
            Moves.push(this.move);
            return;
        }

        if (player_Turn == Color.White && isWhite_InCheck()) {
            if (!canWhite_escape()) {
                gameStatus = GameStatus.BLACK_WON;
                System.out.println("Black won");
                Moves.push(this.move);
                return;
            } else {
                System.out.println(player_Turn + " in check");
                gameStatus = GameStatus.WHITE_IN_CHECK;
                Moves.push(this.move);
                return;
            }
        }

        if (player_Turn == Color.Black && isBlack_InCheck()) {
            if (!canBlack_escape()) {
                gameStatus = GameStatus.WHITE_WON;
                System.out.println("White won");
                Moves.push(this.move);
                return;
            } else {
                System.out.println(player_Turn + " in check");
                gameStatus = GameStatus.BLACK_IN_CHECK;
                Moves.push(this.move);
                return;
            }
        }

        gameStatus = GameStatus.IN_PROGRESS;
        Moves.push(this.move);
    }

    private void reverseUpdate(Move moves) {
        if (moves.isChange_movement()) {
            moves.getCurrent_square().getPiece().setMoved(false);
        }

        if (player_Turn == Color.White) {
            player_Turn = Color.Black;
        } else {
            player_Turn = Color.White;
        }

        if (player_Turn == Color.White && isWhite_InCheck()) {
            if (!canWhite_escape()) {
                gameStatus = GameStatus.BLACK_WON;
            } else {
                gameStatus = GameStatus.WHITE_IN_CHECK;
            }
        }
        if (player_Turn == Color.Black && isBlack_InCheck()) {
            if (!canBlack_escape()) {
                gameStatus = GameStatus.WHITE_WON;
            } else {
                gameStatus = GameStatus.BLACK_IN_CHECK;
            }
        }

        gameStatus = moves.getGameStatus();
    }

    private boolean isValidMove(Square current_Square, Square next_Square) {
        if (current_Square == next_Square || current_Square.isOccupied() == false) {
            return false;
        }
        if (current_Square.getPiece().isValidMove(current_Square, next_Square) == false) {
            return false;
        }
        if (!canMove(current_Square, next_Square)) {
            return false;
        }
        if (current_Square.getPiece().getType() == Type.King && Math.abs(current_Square.getColumn() - next_Square.getColumn()) == 2) {
            if (!checkCastling(current_Square, next_Square)) {
                return false;
            }
        }

        return true;
    }

    private boolean canMove(Square current_Square, Square next_Square) {
        if (next_Square.isOccupied()) {
            if (current_Square.getPiece().getColor() == next_Square.getPiece().getColor()) {
                return false;
            }
            if (current_Square.getPiece().getType() == Type.Pawn && !current_Square.getPiece().canEat(current_Square, next_Square)) {
                return false;
            }
            if (current_Square.getPiece().getType() == Type.Pawn
                    && Math.abs(next_Square.getRow() - current_Square.getRow()) == 2) {
                return false;
            }
            if (current_Square.getPiece().getType() != Type.Knight) {
                ArrayList<Square> squares = game_Board.get_Squares_between(current_Square, next_Square);
                for (Square square1 : squares) {
                    if (square1.isOccupied()) {
                        return false;
                    }
                }
            }
        } else ///////////////////////If square is not occuppied///////////////////////
        {
            if (checkEnpassant(current_Square, next_Square)) {
                return true;
            }
            if (current_Square.getPiece().getType() != Type.Knight) {
                ArrayList<Square> squares = game_Board.get_Squares_between(current_Square, next_Square);
                for (Square square1 : squares) {
                    if (square1.isOccupied()) {
                        return false;
                    }
                }
            }
            if (checkCastling(current_Square, next_Square)) {
                return true;
            }
            if (current_Square.getPiece().getType() == Type.Pawn && current_Square.getPiece().canEat(current_Square, next_Square)) {
                return false;
            }
        }

        return true;
    }

    private boolean checkEnpassant(Square current_Square, Square next_Square) {
        enpassant = false;
        if (current_Square.getPiece().getType() == Type.Pawn && (Math.abs(next_Square.getColumn() - current_Square.getColumn()) == 1)) {
            Square pawn_square = game_Board.getSquare(current_Square.getRow(), next_Square.getColumn());
            if (pawn_square.isOccupied() == true) {
                if (pawn_square.getPiece().getType() == Type.Pawn) {
                    if (pawn_square.getPiece().getColor() != player_Turn && !Moves.empty()) {
                        if (Moves.peek().getNext_square() == pawn_square
                                && Math.abs(Moves.peek().getNext_square().getRow() - Moves.peek().getCurrent_square().getRow()) == 2) {
                            enpassant = true;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void enpassantMove(Square current_Square, Square next_Square) {
        System.out.println("Enpassant");
        Square pawn_square = game_Board.getSquare(current_Square.getRow(), next_Square.getColumn());

        this.move.setEaten_Piece(pawn_square.getPiece());
        this.move.setEatenPiece_square(pawn_square);
        this.move.setEnpassant(true);

        next_Square.setPiece(current_Square.getPiece());
        current_Square.setOccupied(false);
        pawn_square.setOccupied(false);

        System.out.println("Captured Pawn");
        enpassant = false;
    }

    private boolean checkCastling(Square current_Square, Square next_Square) {
        castling = false;
        ArrayList<Square> squares = game_Board.get_Squares_between(current_Square, next_Square);
        for (Square square1 : squares) {
            if (square1.isOccupied()) {
                return false;
            }
        }
        if (current_Square.getPiece().getType() == Type.King && Math.abs(current_Square.getColumn() - next_Square.getColumn()) == 2) {
            if (current_Square.getPiece().isMoved() == false) {
                if (game_Board.getSquare(current_Square.getRow(), next_Square.getColumn() + 1).isOccupied()) {
                    Square rook_square = game_Board.getSquare(current_Square.getRow(), next_Square.getColumn() + 1);
                    if (rook_square.getPiece().getType() == Type.Rook) {
                        if (rook_square.getPiece().isMoved() == false) {
                            squares = game_Board.get_Squares_between(current_Square, rook_square);
                            for (Square square1 : squares) {
                                if (square1.isOccupied()) {
                                    return false;
                                }
                            }
                            castling = true;
                            return true;
                        }
                    }
                } else if (game_Board.getSquare(current_Square.getRow(), next_Square.getColumn() - 2).isOccupied()) {
                    Square rook_square = game_Board.getSquare(current_Square.getRow(), next_Square.getColumn() - 2);
                    if (rook_square.getPiece().getType() == Type.Rook) {
                        if (rook_square.getPiece().isMoved() == false) {
                            squares = game_Board.get_Squares_between(current_Square, rook_square);
                            for (Square square1 : squares) {
                                if (square1.isOccupied()) {
                                    return false;
                                }
                            }
                            castling = true;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void castlingtMove(Square current_Square, Square next_Square) {
        Square rook_square;
        Square rook_next_square;
        if (game_Board.getSquare(current_Square.getRow(), next_Square.getColumn() + 1).isOccupied()) {
            rook_square = game_Board.getSquare(current_Square.getRow(), next_Square.getColumn() + 1);
            rook_next_square = game_Board.getSquare(current_Square.getRow(), next_Square.getColumn() - 1);

            next_Square.setPiece(current_Square.getPiece());
            current_Square.setOccupied(false);
            rook_next_square.setPiece(rook_square.getPiece());
            rook_square.setOccupied(false);
        } else {
            rook_square = game_Board.getSquare(current_Square.getRow(), next_Square.getColumn() - 2);
            rook_next_square = game_Board.getSquare(current_Square.getRow(), next_Square.getColumn() + 1);

            next_Square.setPiece(current_Square.getPiece());
            current_Square.setOccupied(false);
            rook_next_square.setPiece(rook_square.getPiece());
            rook_square.setOccupied(false);
        }

        this.move.setCastle(true);
        this.move.setRook_previous_square(rook_square);
        this.move.setRook_next_square(rook_next_square);

        System.out.println("Castle");
        castling = false;
    }

    private void promote_Pawn(Square current_Square, String PROMOTE_TO) {
        switch (PROMOTE_TO) {
            case "K", "k" -> {
                current_Square.setPiece(new Knight(current_Square.getPiece().getColor()));
            }
            case "B", "b" -> {
                current_Square.setPiece(new Bishop(current_Square.getPiece().getColor()));
            }
            case "R", "r" -> {
                current_Square.setPiece(new Rook(current_Square.getPiece().getColor()));
            }
            case "Q", "q" -> {
                current_Square.setPiece(new Queen(current_Square.getPiece().getColor()));
            }
            default -> {
                return;
            }
        }
    }

    public ArrayList<Move> getAllValidMovesFromSquare(Square current_Square) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Square next_Square = game_Board.getSquare(row, column);
                if (isValidMove(current_Square, next_Square)) {
                    moves.add(new Move(current_Square, next_Square));
                }
            }
        }
        return moves;
    }

    public ArrayList<Move> check(ArrayList<Move> moves) {
        ArrayList<Move> movee=new ArrayList<>();
        movee.addAll(moves);
        Piece p2;
        Square pawn_square;
        for (Move move1 : moves) {
            int f=0;
            p2 = null;
            pawn_square = null;
            Square current_Square =move1.getCurrent_square();
            Square next_Square =move1.getNext_square();

            if (next_Square.isOccupied()) {
                p2 = next_Square.getPiece();
            }

            /*if (checkEnpassant(current_Square, next_Square)) {
                pawn_square = game_Board.getSquare(current_Square.getRow(), next_Square.getColumn());
                p2 = pawn_square.getPiece();
                next_Square.setPiece(current_Square.getPiece());
                current_Square.setOccupied(false);
                pawn_square.setOccupied(false);
            } else{*/
                next_Square.setPiece(current_Square.getPiece());
                current_Square.setOccupied(false);
            //}

            if (player_Turn == Color.White && isWhite_InCheck()) {
                if (enpassant) {
                    reverseEnpassant(current_Square, next_Square, pawn_square, p2);
                    enpassant = false;
                } else {
                    current_Square.setPiece(next_Square.getPiece());
                    next_Square.setOccupied(false);
                    if (p2 != null) {
                        next_Square.setPiece(p2);
                    }
                }
                f=1;
                movee.remove(move1);
            }

            if (player_Turn == Color.Black && isBlack_InCheck()) {
                if (enpassant) {
                    reverseEnpassant(current_Square, next_Square, pawn_square, p2);
                    enpassant = false;
                } else {
                    current_Square.setPiece(next_Square.getPiece());
                    next_Square.setOccupied(false);
                    if (p2 != null) {
                        next_Square.setPiece(p2);
                    }
                }
                f=1;
                movee.remove(move1);
            }

            if (f == 0) {
                if (enpassant) {
                    reverseEnpassant(current_Square, next_Square, pawn_square, p2);
                    enpassant = false;
                } else {
                    current_Square.setPiece(next_Square.getPiece());
                    next_Square.setOccupied(false);
                    if (p2 != null) {
                        next_Square.setPiece(p2);
                    }
                }
            } 
        }
        enpassant = false;
        castling = false;
        return movee;
    }

    private boolean isWhite_InCheck() {
        Color my_color = Color.White, opp_color = Color.Black;
        Square my_King = getKing(my_color);
        Square b_square;
        ArrayList<Move> moves;

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                b_square = game_Board.getSquare(row, column);
                if (b_square.isOccupied()) {
                    if (b_square.getPiece().getColor() == opp_color) {
                        moves = getAllValidMovesFromSquare(b_square);
                        for (Move moves1 : moves) {
                            if (moves1.getNext_square() == my_King) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean isBlack_InCheck() {
        Color my_color = Color.Black, opp_color = Color.White;
        Square my_King = getKing(my_color);
        Square b_square;
        ArrayList<Move> moves;

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                b_square = game_Board.getSquare(row, column);
                if (b_square.isOccupied()) {
                    if (b_square.getPiece().getColor() == opp_color) {
                        moves = getAllValidMovesFromSquare(b_square);
                        for (Move moves1 : moves) {
                            if (moves1.getNext_square() == my_King) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean canWhite_escape() {
        boolean move_exists = false;
        Color my_color = Color.White;
        saving_moves.clear();

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Square current_square = game_Board.getSquare(row, column);
                if (current_square.getPiece() != null) {
                    if (current_square.getPiece().getColor() == my_color) {
                        ArrayList<Move> possible_moves = getAllValidMovesFromSquare(current_square);
                        for (Move moves : possible_moves) {
                            if (virtual_play(current_square, moves.getNext_square(), my_color)) {
                                saving_moves.add(new Move(current_square, moves.getNext_square()));
                                move_exists = true;
                            }
                        }
                    }
                }
            }
        }
        if (move_exists) {
            return true;
        }
        return false;
    }

    private boolean canBlack_escape() {
        boolean move_exists = false;
        Color my_color = Color.Black;
        saving_moves.clear();

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Square current_square = game_Board.getSquare(row, column);
                if (current_square.getPiece() != null) {
                    if (current_square.getPiece().getColor() == my_color) {
                        ArrayList<Move> possible_moves = getAllValidMovesFromSquare(current_square);
                        for (Move moves : possible_moves) {
                            if (virtual_play(current_square, moves.getNext_square(), my_color)) {
                                saving_moves.add(new Move(current_square, moves.getNext_square()));
                                move_exists = true;
                            }
                        }
                    }
                }
            }
        }
        if (move_exists) {
            return true;
        }
        return false;
    }

    public Square getKing(Color color) {
        Square Square = null;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Square = game_Board.getSquare(row, column);
                if (Square.isOccupied()) {
                    if (Square.getPiece().getType() == Type.King && Square.getPiece().getColor() == color) {
                        return Square;
                    }
                }
            }
        }
        return Square;
    }

    private boolean isInsufficient_Material() {
        ArrayList<Square> White_pieces = getLiving_Pieces(Color.White);
        ArrayList<Square> Black_pieces = getLiving_Pieces(Color.Black);
        boolean w = false;
        boolean b = false;
        if (White_pieces.size() == 1 && Black_pieces.size() == 1) {
            return true;
        }
        if (White_pieces.size() < 3) {
            for (Square piece : White_pieces) {
                if (piece.getPiece().getType() == Type.Knight || piece.getPiece().getType() == Type.Bishop) {
                    w = true;
                }
            }
        }
        if (Black_pieces.size() < 3) {
            for (Square piece : Black_pieces) {
                if (piece.getPiece().getType() == Type.Knight || piece.getPiece().getType() == Type.Bishop) {
                    b = true;
                }
            }
        }
        if (w && b) {
            return true;
        }

        return false;
    }

    private ArrayList<Square> getLiving_Pieces(Color my_color) {
        Square Square;
        ArrayList<Square> pieces = new ArrayList<>();

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Square = game_Board.getSquare(row, column);
                if (Square.isOccupied()) {
                    if (Square.getPiece().getColor() == my_color) {
                        pieces.add(Square);
                    }
                }
            }
        }
        return pieces;
    }

    private boolean virtual_play(Square current_Square, Square next_Square, Color my_color) {
        Piece p2 = null;
        Square pawn_square = null;
        Square rook_square = null;
        Square rook_next_square = null;

        if (next_Square.isOccupied()) {
            p2 = next_Square.getPiece();
        }

        if (isValidMove(current_Square, next_Square) && checkEnpassant(current_Square, next_Square)) {
            pawn_square = game_Board.getSquare(current_Square.getRow(), next_Square.getColumn());
            p2 = pawn_square.getPiece();
            next_Square.setPiece(current_Square.getPiece());
            current_Square.setOccupied(false);
            pawn_square.setOccupied(false);
        } else /*if (isValidMove(current_Square, next_Square) && checkCastling(current_Square, next_Square)) {
            if (game_Board.getSquare(current_Square.getRow(), next_Square.getColumn() + 1).isOccupied()) {
                rook_square = game_Board.getSquare(current_Square.getRow(), next_Square.getColumn() + 1);
                rook_next_square = game_Board.getSquare(current_Square.getRow(), next_Square.getColumn() - 1);

                next_Square.setPiece(current_Square.getPiece());
                current_Square.setOccupied(false);
                rook_next_square.setPiece(rook_square.getPiece());
                rook_square.setOccupied(false);
            } else {
                rook_square = game_Board.getSquare(current_Square.getRow(), next_Square.getColumn() - 2);
                rook_next_square = game_Board.getSquare(current_Square.getRow(), next_Square.getColumn() + 1);

                next_Square.setPiece(current_Square.getPiece());
                current_Square.setOccupied(false);
                rook_next_square.setPiece(rook_square.getPiece());
                rook_square.setOccupied(false);
            }
        }  else */ if (isValidMove(current_Square, next_Square)) {
            next_Square.setPiece(current_Square.getPiece());
            current_Square.setOccupied(false);
        } else {
            return false;
        }

        if (my_color == Color.White && isWhite_InCheck()) {
            if (enpassant) {
                reverseEnpassant(current_Square, next_Square, pawn_square, p2);
                enpassant = false;
            } else if (castling) {
                reverseCastling(current_Square, next_Square, rook_square, rook_next_square);
                castling = false;
            } else {
                current_Square.setPiece(next_Square.getPiece());
                next_Square.setOccupied(false);
                if (p2 != null) {
                    next_Square.setPiece(p2);
                }
            }
            return false;
        }

        if (my_color == Color.Black && isBlack_InCheck()) {
            if (enpassant) {
                reverseEnpassant(current_Square, next_Square, pawn_square, p2);
                enpassant = false;
            } else if (castling) {
                reverseCastling(current_Square, next_Square, rook_square, rook_next_square);
                castling = false;
            } else {
                current_Square.setPiece(next_Square.getPiece());
                next_Square.setOccupied(false);
                if (p2 != null) {
                    next_Square.setPiece(p2);
                }
            }
            return false;
        }

        if (enpassant) {
            reverseEnpassant(current_Square, next_Square, pawn_square, p2);
            enpassant = false;
        } else if (castling) {
            reverseCastling(current_Square, next_Square, rook_square, rook_next_square);
            castling = false;
        } else {
            current_Square.setPiece(next_Square.getPiece());
            next_Square.setOccupied(false);
            if (p2 != null) {
                next_Square.setPiece(p2);
            }
        }

        enpassant = false;
        castling = false;
        return true;
    }

    private void reverseEnpassant(Square current_Square, Square next_Square, Square pawn_square, Piece p) {
        current_Square.setPiece(next_Square.getPiece());
        next_Square.setOccupied(false);
        pawn_square.setPiece(p);
    }

    private void reverseCastling(Square current_Square, Square next_Square, Square rook_square, Square rook_next_square) {
        current_Square.setPiece(next_Square.getPiece());
        next_Square.setOccupied(false);
        rook_square.setPiece(rook_next_square.getPiece());
        rook_next_square.setOccupied(false);
    }

    private void reversePromotion(Move move) {
        move.getCurrent_square().setPiece(move.getPromoted_pawn());
        if (move.getEaten_Piece() == null) {
            move.getNext_square().setOccupied(false);
        } else {
            move.getNext_square().setPiece(move.getEaten_Piece());
        }
    }

    private void reverseMove(Move move) {
        move.getCurrent_square().setPiece(move.getNext_square().getPiece());
        if (move.getEaten_Piece() == null) {
            move.getNext_square().setOccupied(false);
        } else {
            move.getNext_square().setPiece(move.getEaten_Piece());
        }
    }
    
    public void undo() {
        if (!Moves.empty()) {
            Move move1 = Moves.pop();
            if (move1 == null) {
                return;
            }
            if (move1.isEnpassant()) {
                reverseEnpassant(move1.getCurrent_square(), move1.getNext_square(), move1.getEatenPiece_square(), move1.getEaten_Piece());
            } else if (move1.isCastle()) {
                reverseCastling(move1.getCurrent_square(), move1.getNext_square(), move1.getRook_previous_square(), move1.getRook_next_square());
            } else if (move1.isPromotion()) {
                reversePromotion(move1);
            } else {
                reverseMove(move1);
            }
            reverseUpdate(move1);
        }
    }

    public boolean isGameOver() {
        if (gameStatus == GameStatus.INSUFFICIENT_MATERIAL) {
            return true;
        }
        if (gameStatus == GameStatus.STALEMATE) {
            return true;
        }
        if (gameStatus == GameStatus.WHITE_WON) {
            return true;
        }
        if (gameStatus == GameStatus.BLACK_WON) {
            return true;
        }

        return false;
    }
}
