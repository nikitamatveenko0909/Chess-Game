import java.util.LinkedList;

public class Piece {
    int xp;
    int yp;
    int x;
    int y;
    int lastXCoordinate;
    int lastYCoordinate;
    boolean isWhite;
    boolean firstTurn = true;
    LinkedList<Piece> ps;
    String name;

    public Piece(int xp, int yp, boolean isWhite, String name, LinkedList<Piece> ps) {
        this.xp = xp;
        this.yp = yp;
        x = xp * 64;
        y = yp * 64;
        this.lastXCoordinate = lastXCoordinate;
        this.lastYCoordinate = lastYCoordinate;
        this.isWhite = isWhite;
        this.firstTurn = firstTurn;
        this.name = name;
        this.ps = ps;
        ps.add(this);
    }


    public void move(int xp, int yp){
        if(ChessGame.getPiece(xp*64, yp*64) != null){
            if(ChessGame.getPiece(xp*64, yp*64).isWhite != isWhite){
                ChessGame.getPiece(xp * 64, yp * 64).kill();
            }
            else{
                x = this.xp * 64;
                y = this.yp * 64;
                return;
            }
        }
        this.xp = xp;
        this.yp = yp;
        x = xp * 64;
        y = yp * 64;
        this.firstTurn = false;
    }

    public void kill(){
        ps.remove(this);
    }
}
