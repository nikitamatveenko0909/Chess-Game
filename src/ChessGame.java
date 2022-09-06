import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class ChessGame {
    public static LinkedList<Piece> ps = new LinkedList<>();
    public static Piece selectedPiece = null;
    public static void main(String[] args) throws IOException {

        BufferedImage all = ImageIO.read(new File("D:\\Jobb\\Chess Game\\src\\img\\chess.png"));
        Image imgs[] = new Image[12];
        int ind = 0;
        for (int y=0; y<400; y+=200){
            for(int x = 0; x < 1200; x+=200){
                imgs[ind]=all.getSubimage(x,y,200,200).getScaledInstance(64,64, BufferedImage.SCALE_SMOOTH);
                ind++;
            }
        }
        Piece brook=new Piece(0, 0, false, "rook", ps);
        Piece bkinght=new Piece(1, 0, false, "knight", ps);
        Piece bbishop=new Piece(2, 0, false, "bishop", ps);
        Piece bqueen=new Piece(3, 0, false, "queen", ps);
        Piece bking=new Piece(4, 0, false, "king", ps);
        Piece bbishop2=new Piece(5, 0, false, "bishop", ps);
        Piece bkight2=new Piece(6, 0, false, "knight", ps);
        Piece brook2=new Piece(7, 0, false, "rook", ps);
        Piece bpawn1=new Piece(1, 1, false, "pawn", ps);
        Piece bpawn2=new Piece(2, 1, false, "pawn", ps);
        Piece bpawn3=new Piece(3, 1, false, "pawn", ps);
        Piece bpawn4=new Piece(4, 1, false, "pawn", ps);
        Piece bpawn5=new Piece(5, 1, false, "pawn", ps);
        Piece bpawn6=new Piece(6, 1, false, "pawn", ps);
        Piece bpawn7=new Piece(7, 1, false, "pawn", ps);
        Piece bpawn8=new Piece(0, 1, false, "pawn", ps);

        Piece wrook=new Piece(0, 7, true, "rook", ps);
        Piece wkinght=new Piece(1, 7, true, "knight", ps);
        Piece wbishop=new Piece(2, 7, true, "bishop", ps);
        Piece wqueen=new Piece(3, 7, true, "queen", ps);
        Piece wking=new Piece(4, 7, true, "king", ps);
        Piece wbishop2=new Piece(5, 7, true, "bishop", ps);
        Piece wkight2=new Piece(6, 7, true, "knight", ps);
        Piece wrook2=new Piece(7, 7, true, "rook", ps);
        Piece wpawn1=new Piece(1, 6, true, "pawn", ps);
        Piece wpawn2=new Piece(2, 6, true, "pawn", ps);
        Piece wpawn3=new Piece(3, 6, true, "pawn", ps);
        Piece wpawn4=new Piece(4, 6, true, "pawn", ps);
        Piece wpawn5=new Piece(5, 6, true, "pawn", ps);
        Piece wpawn6=new Piece(6, 6, true, "pawn", ps);
        Piece wpawn7=new Piece(7, 6, true, "pawn", ps);
        Piece wpawn8=new Piece(0, 6, true, "pawn", ps);

        JFrame frame = new JFrame();
        frame.setBounds(10, 10, 512, 512);
        frame.setUndecorated(true);
        JPanel pn = new JPanel(){
            @Override
            public void paint(Graphics g){
                boolean white = true;
                for(int y=0; y<8;y++){
                    for(int x=0; x<8; x++){
                        if(white){
                            g.setColor(new Color(235, 235, 208));
                        }
                        else{
                            g.setColor(new Color(119, 148, 85));
                        }
                        g.fillRect(x*64, y*64, 64 ,64);
                        white = !white;
                    }
                    white = !white;
                }
                for (Piece p: ps){
                    int ind = 0;
                    if(p.name.equalsIgnoreCase("king")){
                        ind = 0;
                    }
                    else if(p.name.equalsIgnoreCase("queen")){
                        ind = 1;
                    }
                    else if(p.name.equalsIgnoreCase("bishop")){
                        ind = 2;
                    }
                    else if(p.name.equalsIgnoreCase("knight")){
                        ind = 3;
                    }
                    else if(p.name.equalsIgnoreCase("rook")){
                        ind = 4;
                    }
                    else if(p.name.equalsIgnoreCase("pawn")){
                        ind = 5;
                    }
                    if(!p.isWhite){
                        ind+=6;
                    }
                    g.drawImage(imgs[ind], p.x, p.y, this);
                }
            }
        };
        frame.add(pn);
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                selectedPiece = getPiece(e.getX(), e.getY());
                if(selectedPiece != null){
                    selectedPiece.lastXCoordinate = (e.getX()) / 64;
                    selectedPiece.lastYCoordinate = (e.getY()) / 64;
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(selectedPiece != null){
                    if(isMoveLegal(selectedPiece, e.getX(), e.getY())){
                        selectedPiece.move(e.getX()/64, e.getY()/64);
                        frame.repaint();
                    }
                    else{
                        selectedPiece.move(selectedPiece.lastXCoordinate, selectedPiece.lastYCoordinate);
                        frame.repaint();
                    }
                }


            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(selectedPiece != null){
                    selectedPiece.x = e.getX() -32;
                    selectedPiece.y = e.getY() -32;
                    frame.repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }

    public static Piece getPiece (int x, int y){
        int xp=x/64;
        int yp=y/64;
        for(Piece p: ps){
            if(p.xp == xp && p.yp == yp){
                return p;
            }
        }
        return null;
    }

    public static boolean isMoveLegal(Piece piece, int x, int y){
        int xp=x/64;
        int yp=y/64;
        boolean result = false;
        Piece pieceOnSelectedPlace = getPiece(x, y);
        boolean isTargetEnemy = pieceOnSelectedPlace != null && piece.isWhite != getPiece(x, y).isWhite;

        switch(piece.name){
            case "pawn":
                if(xp - piece.xp == 0 || isTargetEnemy){
                    if(piece.firstTurn){
                        if((yp - piece.yp) == 1 || (yp - piece.yp) == -1 || (yp - piece.yp) == 2 || (yp - piece.yp) == -2){
                            result = true;
                        }
                    }
                    else{
                        if((yp - piece.yp) == 1 || (yp - piece.yp) == -1){
                            result = true;
                        }
                    }
                }

                break;
            case "rook":
                if((xp - piece.xp) == 0 || (yp - piece.yp) == 0){
                    boolean legal = true;
                    boolean isMoveVertical = (xp - piece.xp) == 0;
                    boolean isMoveHorizontal = (yp - piece.yp) == 0;
                    boolean isMoveDown = (yp - piece.yp) > -1 && (xp - piece.xp) == 0;
                    boolean isMoveRight = (xp - piece.xp) > -1 && (yp - piece.yp) == 0;
                    if(isMoveDown && isMoveVertical){
                        for(int newY = piece.yp; newY<yp-1; newY++){
                            if(getPiece(x, (newY + 1) * 64) != null){
                                legal = false;
                                break;
                            }
                        }
                    }else if(!isMoveDown && isMoveVertical) {
                        for(int newY = piece.yp; newY>yp+1; newY--){
                            if(getPiece(x, (newY - 1) * 64) != null){
                                legal = false;
                                break;
                            }
                        }
                    }
                    else if(isMoveRight &&  isMoveHorizontal){
                        for(int newX = piece.xp; newX<xp-1; newX++){
                            if(getPiece((newX + 1) * 64, y) != null){
                                legal = false;
                                break;
                            }
                        }
                    }else if (!isMoveRight && isMoveHorizontal){
                        for(int newX = piece.xp; newX>xp+1; newX--){
                            if(getPiece((newX - 1) * 64, y) != null){
                                System.out.println(getPiece((newX - 1) * 64, y).name);
                                legal = false;
                                break;
                            }
                        }
                    }

                    result = legal;
                }
                break;

            case "knight":
                if((((yp - piece.yp) == 2 || (yp - piece.yp) == -2) && ((xp - piece.xp) == 1 || (xp -piece.xp) == -1)) ||(((yp - piece.yp) == 1 || (yp - piece.yp) == -1) && ((xp - piece.xp) == 2 || (xp -piece.xp) == -2))){
                    result = true;
                }
                break;

            case "bishop":
                if((xp - piece.xp) == (yp - piece.yp) || (piece.yp - yp) == (xp - piece.xp)){
                    boolean legal = true;
                    boolean isMoveTopRight = (((xp - piece.xp) == Math.abs(piece.yp - yp)) && (piece.yp - yp) >= 1);
                    boolean isMoveDownRight = (((xp - piece.xp) == Math.abs(piece.yp - yp)) && (piece.yp - yp) <= -1);
                    boolean isMoveTopLeft = (((piece.xp - xp) == (piece.yp - yp)) && (piece.yp - yp) >= 1);
                    boolean isMoveBottomLeft = (((xp - piece.xp) == (piece.yp - yp)) && (piece.yp - yp) <= -1);
                    System.out.println(isMoveTopLeft);
                    if(isMoveTopRight){
                        for(int newY = piece.yp; newY>yp+1; newY--){
                            if(getPiece(x, (newY - 1) * 64) != null){
                                legal = false;
                                break;
                            }
                        }
                        for(int newX = piece.xp; newX<xp-1; newX++){
                            if(getPiece((newX + 1) * 64, y) != null){
                                legal = false;
                                break;
                            }
                        }
                    }else if(isMoveDownRight){
                        for(int newY = piece.yp; newY<yp-1; newY++){
                            if(getPiece(x, (newY + 1) * 64) != null){
                                legal = false;
                                break;
                            }
                        }
                        for(int newX = piece.xp; newX<xp-1; newX++){
                            if(getPiece((newX + 1) * 64, y) != null){
                                legal = false;
                                break;
                            }
                        }

                    }else if(isMoveTopLeft){
                        for(int newY = piece.yp; newY>yp+1; newY--){
                            System.out.println(newY);
                            if(getPiece(x, (newY - 1) * 64) != null){
                                legal = false;
                                break;
                            }
                        }
                        for(int newX = piece.xp; newX>xp+1; newX--){
                            System.out.println(newX);
                            if(getPiece((newX - 1) * 64, y) != null){
                                System.out.println(getPiece((newX - 1) * 64, y).name);
                                legal = false;
                                break;
                            }
                        }

                    }else if(isMoveBottomLeft){
                        for(int newY = piece.yp; newY<yp-1; newY++){
                            if(getPiece(x, (newY + 1) * 64) != null){
                                legal = false;
                                break;
                            }
                        }
                        for(int newX = piece.xp; newX>xp+1; newX--){
                            if(getPiece((newX - 1) * 64, y) != null){
                                System.out.println(getPiece((newX - 1) * 64, y).name);
                                legal = false;
                                break;
                            }
                        }
                    }
                    result = legal;
                }
                break;

            case "king":
                if(piece.xp - xp == 1 || piece.yp - yp == 1 || piece.xp + xp == 1 || piece.yp + yp == 1 || piece.xp - xp == -1 || piece.yp - yp == -1 || piece.xp + xp == -1 || piece.yp + yp == -1){
                    result = true;
                }
                break;

            case "queen":
                if(
                        ((xp - piece.xp) == 0 && (yp - piece.yp) != 0)
                        || ((xp - piece.xp) != 0 && (yp - piece.yp) == 0)
                        || ((piece.yp - yp) == (piece.xp - xp)
                        || (piece.yp - yp) == (piece.xp + xp)
                        || (piece.yp + yp) == (piece.xp - xp)
                        || (xp - piece.xp) == -(yp - piece.yp))
                ){
                    boolean legal = true;
                    boolean isMoveVertical = (xp - piece.xp) == 0;
                    boolean isMoveHorizontal = (yp - piece.yp) == 0;
                    boolean isMoveDown = (yp - piece.yp) > -1 && (xp - piece.xp) == 0;
                    boolean isMoveRight = (xp - piece.xp) > -1 && (yp - piece.yp) == 0;
                    if(!isMoveVertical && !isMoveHorizontal){
                        boolean isMoveTopRight = (((xp - piece.xp) == Math.abs(piece.yp - yp)) && (piece.yp - yp) >= 1);
                        boolean isMoveDownRight = (((xp - piece.xp) == Math.abs(piece.yp - yp)) && (piece.yp - yp) <= -1);
                        boolean isMoveTopLeft = (((piece.xp - xp) == (piece.yp - yp)) && (piece.yp - yp) >= 1);
                        boolean isMoveBottomLeft = (((xp - piece.xp) == (piece.yp - yp)) && (piece.yp - yp) <= -1);
                        if(isMoveTopRight){
                            for(int newY = piece.yp; newY>yp+1; newY--){
                                System.out.println(newY);
                                System.out.println("check these coordinates: " + x/64 + " - " + (newY - 1));
                                if(getPiece(x, (newY - 1) * 64) != null){
                                    legal = false;
                                    break;
                                }
                            }
                            for(int newX = piece.xp; newX<xp-1; newX++){
                                System.out.println(newX);
                                if(getPiece((newX + 1) * 64, y) != null){
                                    legal = false;
                                    break;
                                }
                            }
                        }else if(isMoveDownRight){
                            for(int newY = piece.yp; newY<yp-1; newY++){
                                System.out.println(newY);
                                if(getPiece(x, (newY + 1) * 64) != null){
                                    legal = false;
                                    break;
                                }
                            }
                            for(int newX = piece.xp; newX<xp-1; newX++){
                                System.out.println(newX);
                                if(getPiece((newX + 1) * 64, y) != null){
                                    legal = false;
                                    break;
                                }
                            }

                        }else if(isMoveTopLeft){
                            for(int newY = piece.yp; newY>yp+1; newY--){
                                System.out.println(newY);
                                if(getPiece(x, (newY - 1) * 64) != null){
                                    legal = false;
                                    break;
                                }
                            }
                            for(int newX = piece.xp; newX>xp+1; newX--){
                                System.out.println(newX);
                                if(getPiece((newX - 1) * 64, y) != null){
                                    System.out.println(getPiece((newX - 1) * 64, y).name);
                                    legal = false;
                                    break;
                                }
                            }

                        }else if(isMoveBottomLeft){
                            for(int newY = piece.yp; newY<yp-1; newY++){
                                if(getPiece(x, (newY + 1) * 64) != null){
                                    legal = false;
                                    break;
                                }
                            }
                            for(int newX = piece.xp; newX>xp+1; newX--){
                                if(getPiece((newX - 1) * 64, y) != null){
                                    System.out.println(getPiece((newX - 1) * 64, y).name);
                                    legal = false;
                                    break;
                                }
                            }
                        }
                    }
                    else {
                        if(isMoveDown && isMoveVertical){
                            for(int newY = piece.yp; newY<yp-1; newY++){
                                if(getPiece(x, (newY + 1) * 64) != null){
                                    legal = false;
                                    break;
                                }
                            }
                        }else if(!isMoveDown && isMoveVertical) {
                            for(int newY = piece.yp; newY>yp+1; newY--){
                                if(getPiece(x, (newY - 1) * 64) != null){
                                    legal = false;
                                    break;
                                }
                            }
                        }
                        else if(isMoveRight &&  isMoveHorizontal){
                            for(int newX = piece.xp; newX<xp-1; newX++){
                                if(getPiece((newX + 1) * 64, y) != null){
                                    legal = false;
                                    break;
                                }
                            }
                        }else if (!isMoveRight && isMoveHorizontal){
                            for(int newX = piece.xp; newX>xp+1; newX--){
                                if(getPiece((newX - 1) * 64, y) != null){
                                    System.out.println(getPiece((newX - 1) * 64, y).name);
                                    legal = false;
                                    break;
                                }
                            }
                        }
                    }


                    result = legal;
                }
                break;

        }
        return result;
    }
}
