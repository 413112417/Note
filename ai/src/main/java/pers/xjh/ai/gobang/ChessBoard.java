package pers.xjh.ai.gobang;

/**
 * 五子棋棋盘
 * Created by XJH on 2017/6/20.
 */

public class ChessBoard {

    /**
     * 棋子
     */
    public enum Piece {
        WHITE, BLACK
    }

    private Piece[][] chessBoard;

    public ChessBoard() {
        this.chessBoard = new Piece[15][15];
    }

    public ChessBoard(Piece[][] chessBoard) {
        this.chessBoard = chessBoard;
    }

    public Piece[][] getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(Piece[][] chessBoard) {
        this.chessBoard = chessBoard;
    }

    /**
     * 落子
     */
    public void move(int x, int y, Piece piece) {
        chessBoard[x][y] = piece;
    }
}
