/*
 * Class which handles the concept of each tile on the board
 *
 * Authors Team11:  Jack Geraghty - 16384181
 *                  Conor Beenham - 16350851
 *                  Alen Thomas   - 16333003
 */

package com.team11.cluedo.board;

import com.team11.cluedo.board.room.TileType;
import com.team11.cluedo.suspects.Suspect;
import com.team11.cluedo.suspects.SuspectData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardPos extends JComponent {
    private Point location;
    private boolean isTraversable;
    private boolean isOccupied;
    private TileType type;
    private int tileSize;

    public BoardPos(Point loc, boolean traverse, boolean occ, TileType t, int size){
        this.location = loc;
        this.isTraversable = traverse;
        this.isOccupied = occ;
        this.type = t;
        this.tileSize = size;

        Dimension buttonSize = new Dimension(size, size);
        super.setPreferredSize(buttonSize);
    }

    public Point getLocation(){
        return this.location;
    }

    public boolean isTraversable(){
        return this.isTraversable;
    }

    public void setOccupied(boolean o){
        this.isOccupied = o;
    }

    public boolean isOccupied(){
        return this.isOccupied;
    }

    public TileType getType(){
        return this.type;
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        int borderWidth = 3;

        if ( (this.location.getX() > 0 && this.location.getY() > 0) &&
                (this.location.getX() < 26 && this.location.getY() < 25) &&
                (this.getType() == TileType.HALLWAY || this.getType() == TileType.AVOID || this.getType() == TileType.SPAWN || this.getType() == TileType.DOORMAT || this.getType() == TileType.DOOR ||
                 this.getType() == TileType.PREFER)
                ){
            g2.setColor(new Color(255,255,0,130));
            //g2.fillRect(0,0,tileSize,tileSize);
            g2.setStroke(new BasicStroke(borderWidth));
            //g2.drawRect((int)location.getY() * tileSize, (int)location.getX() * tileSize, tileSize, tileSize);
        }
    }

    public String toString(){
        StringBuilder s = new StringBuilder();

        s.append("\nLocation: " + this.getLocation());
        s.append("\nIsTraverseable:" + this.isTraversable);
        s.append("\nIsOccupied: " + this.isOccupied());
        s.append("\nTileType: " + this.getType() + "\n");
        return s.toString();
    }


}
