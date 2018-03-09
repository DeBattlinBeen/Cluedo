/*
 * Code to handle the behaviour of the suspects.
 * Main Author : Jack Geraghty
 * Authors Team11:  Jack Geraghty - 16384181
 *                  Conor Beenham - 16350851
 *                  Alen Thomas   - 16333003
 */

package com.team11.cluedo.suspects;


import com.team11.cluedo.components.TokenComponent;
import com.team11.cluedo.pathfinder.Mover;
import com.team11.cluedo.board.Board;
import com.team11.cluedo.board.room.TileType;
import com.team11.cluedo.ui.Resolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Suspect extends TokenComponent implements Mover {
    public Suspect(int suspectID, String suspectName, Point location, Image tokenImage, Resolution resolution){
        super(suspectID,suspectName,location,tokenImage,resolution);
    }

    public boolean checkMove(Board gameBoard, ArrayList<Direction> moveList) {
        boolean isValid = true, doOnce = true, hasDoor = false;
        Point testerPoint = new Point(getLocation()), lastLoc;
        ArrayList<Direction> tmpList = new ArrayList<>(moveList);
        HashSet<String> validitySet = new HashSet<>();

        while (!tmpList.isEmpty() && !validitySet.contains("false") && !hasDoor ) {
            lastLoc = testerPoint.getLocation();
            switch (tmpList.get(0)){
                case NORTH:
                    if (gameBoard.getBoardPos((int)testerPoint.getLocation().getY()-1, (int)testerPoint.getLocation().getX()).isTraversable() &&
                            !(gameBoard.getBoardPos((int)testerPoint.getY()-1, (int)testerPoint.getX()).isOccupied())) {

                        if (gameBoard.getBoardPos((int)testerPoint.getLocation().getY()-1, (int)testerPoint.getLocation().getX()).getTileType() == TileType.DOOR && !(gameBoard.getBoardPos((int)lastLoc.getY(), (int)lastLoc.getX()).getTileType() == TileType.DOORMAT)){
                            validitySet.add("false");
                        }
                        testerPoint.setLocation((int)testerPoint.getLocation().getX(), (int)testerPoint.getLocation().getY()-1);
                        validitySet.add("true");
                    } else {
                        if (doOnce){
                            doOnce = false;
                        }
                        validitySet.add("false");
                    }
                    break;
                case EAST:
                    if (gameBoard.getBoardPos((int)testerPoint.getLocation().getY(), (int)testerPoint.getLocation().getX()+1).isTraversable() &&
                            !(gameBoard.getBoardPos((int)testerPoint.getY(), (int)testerPoint.getX()+1).isOccupied())){
                        if (gameBoard.getBoardPos((int)testerPoint.getLocation().getY(), (int)testerPoint.getLocation().getX()+1).getTileType() == TileType.DOOR && !(gameBoard.getBoardPos((int)lastLoc.getY(), (int)lastLoc.getX()).getTileType() == TileType.DOORMAT)){
                            validitySet.add("false");
                        }
                        testerPoint.setLocation((int)testerPoint.getLocation().getX()+1, (int)testerPoint.getLocation().getY());
                        validitySet.add("true");
                    } else {
                        if (doOnce){
                            doOnce = false;
                        }
                        validitySet.add("false");
                    }
                    break;
                case SOUTH:
                    if (gameBoard.getBoardPos((int)testerPoint.getLocation().getY()+1, (int)testerPoint.getLocation().getX()).isTraversable() &&
                            !(gameBoard.getBoardPos((int)testerPoint.getY()+1, (int)testerPoint.getX()).isOccupied())){

                        if (gameBoard.getBoardPos((int)testerPoint.getLocation().getY()+1, (int)testerPoint.getLocation().getX()).getTileType() == TileType.DOOR && !(gameBoard.getBoardPos((int)lastLoc.getY(), (int)lastLoc.getX()).getTileType() == TileType.DOORMAT)){
                            validitySet.add("false");
                        }
                        testerPoint.setLocation((int)testerPoint.getLocation().getX(), (int)testerPoint.getLocation().getY()+1);
                        validitySet.add("true");
                    } else{
                        if (doOnce) {
                            doOnce = false;
                        }
                        validitySet.add("false");
                    }
                    break;
                case WEST:
                    if (gameBoard.getBoardPos((int)testerPoint.getLocation().getY(), (int)testerPoint.getLocation().getX()-1).isTraversable() &&
                            !(gameBoard.getBoardPos((int)testerPoint.getY(), (int)testerPoint.getX()-1).isOccupied())){
                        if (gameBoard.getBoardPos((int)testerPoint.getLocation().getY(), (int)testerPoint.getLocation().getX()-1).getTileType() == TileType.DOOR && !(gameBoard.getBoardPos((int)lastLoc.getY(), (int)lastLoc.getX()).getTileType() == TileType.DOORMAT)){
                            validitySet.add("false");
                        }
                        testerPoint.setLocation((int)testerPoint.getLocation().getX()-1, (int)testerPoint.getLocation().getY());
                        validitySet.add("true");
                    } else {
                        if (doOnce) {
                            doOnce = false;
                        }
                        validitySet.add("false");
                    }
                    break;
                default:
                    break;
            }
            tmpList.remove(0);
            if (gameBoard.getBoardPos((int)testerPoint.getLocation().getY(), (int)testerPoint.getLocation().getX()).getTileType() == TileType.DOOR && gameBoard.getBoardPos((int)lastLoc.getY(), (int)lastLoc.getX()).getTileType() == TileType.DOORMAT){
                hasDoor = true;
            }
        }

        //Have checked all of the moves to see if they go to any non traversal
        //Now check to see of testerPoint is on a tile that is already occupied and that the validity set doesn't contain false
        if ((gameBoard.getBoardPos((int)testerPoint.getLocation().getY(), (int) testerPoint.getLocation().getX()).isOccupied() && !(gameBoard.getBoardPos((int)testerPoint.getLocation().getY(), (int) testerPoint.getLocation().getX()).getTileType() == TileType.DOOR) || validitySet.contains("false"))){
            isValid = false;
        }

        //Check to see if they tried to enter a door
        if (hasDoor) {
            isValid = true;
        }

        //Return whether the player should move or not
        return isValid;
    }

    public void move(Board gameBoard, Direction direction) {
        gameBoard.getBoardPos((int)getLocation().getY(), (int)getLocation().getX()).setOccupied(false);
        switch (direction){
            case NORTH:
                moveUp();
                break;
            case EAST:
                moveRight();
                break;
            case SOUTH:
                moveDown();
                break;
            case WEST:
                moveLeft();
                break;
        }
        if (gameBoard.getBoardPos((int)getLocation().getY(), (int)getLocation().getX()).getTileType() == TileType.DOOR)
            moveToRoom(gameBoard);

        gameBoard.getBoardPos((int)getLocation().getY(), (int)getLocation().getX()).setOccupied(true);
    }

    //Method to move up
    private void moveUp(){
        setLocation(new Point((int)getLocation().getX(), (int)getLocation().getY()-1));
    }
    //Method to move down
    private void moveDown(){
        setLocation(new Point((int)getLocation().getX(), (int)getLocation().getY()+1));
    }
    //Method to move right
    private void moveRight(){
        setLocation(new Point((int)getLocation().getX()+1, (int)getLocation().getY()));
    }
    //Method to move left
    private void moveLeft(){
        setLocation(new Point((int) getLocation().getX() - 1, (int) getLocation().getY()));
    }

    //Method to move a player into a room when they land on a door tile
    private void moveToRoom(Board gameBoard){
        int currRoom = findParentRoom(gameBoard.getBoardPos((int)getLocation().getY(), (int)getLocation().getX()).getLocation(), gameBoard);
        Point nextPoint = gameBoard.getRoom(currRoom).getRandomPoint(gameBoard.getRoom(currRoom).getPlayerPositions());
        setLocation(nextPoint);
        this.setCurrentRoom(currRoom);
        gameBoard.getRoom(currRoom).getPlayerPositions().remove(nextPoint);
    }

    //Method to move the player out of the room
    public int moveOutOfRoom(Board gameBoard, int exitNum){
        Point currPoint = new Point(getLocation());
        Point nextPoint = new Point(gameBoard.getRoom(this.getCurrentRoom()).getExitPoints().get(exitNum));
        if (!(gameBoard.getBoardPos((int)nextPoint.getY(), (int)nextPoint.getX()).isOccupied())) {
            gameBoard.getRoom(this.getCurrentRoom()).getPlayerPositions().add(currPoint);
            setLocation(nextPoint);
            this.setCurrentRoom(-1);
            return 1;
        } else {
            return 0;
        }
    }

    public boolean useSecretPassageWay(Board gameBoard){
        Point currentPoint = new Point(getLocation());
        //See if the room has as secret passage way
        if (gameBoard.getRoom(this.getCurrentRoom()).hasSecretPassage()){
            //Add the current position back to the spawn list
            gameBoard.getRoom(this.getCurrentRoom()).getPlayerPositions().add(currentPoint);
            switch (this.getCurrentRoom()){
                case (0):
                    this.setCurrentRoom(8);
                    break;
                case (8):
                    this.setCurrentRoom(0);
                    break;
                case (2):
                    this.setCurrentRoom(6);
                    break;
                case (6):
                    this.setCurrentRoom(2);
                    break;
            }
            setLocation(gameBoard.getRoom(this.getCurrentRoom()).getRandomPoint(gameBoard.getRoom(this.getCurrentRoom()).getPlayerPositions()));
            return true;
        } else{
            return false;
        }

    }

    //Method which finds what room a suspect is in based on the door tile that they are on
    private int findParentRoom(Point point, Board gameBoard){
        int parentRoom = 0;
        if (point.equals(gameBoard.getRoom(0).getEntryPoints().get(0))){
            return 0;
        } else if (point.equals(gameBoard.getRoom(1).getEntryPoints().get(0)) || point.equals(gameBoard.getRoom(1).getEntryPoints().get(1)) ||
                point.equals(gameBoard.getRoom(1).getEntryPoints().get(2)) || point.equals(gameBoard.getRoom(1).getEntryPoints().get(3))){
            return 1;
        } else if (point.equals(gameBoard.getRoom(2).getEntryPoints().get(0))){
            return 2;
        } else if (point.equals(gameBoard.getRoom(3).getEntryPoints().get(0)) || point.equals(gameBoard.getRoom(3).getEntryPoints().get(1))){
            return 3;
        } else if (point.equals(gameBoard.getRoom(4).getEntryPoints().get(0)) || point.equals(gameBoard.getRoom(4).getEntryPoints().get(1))){
            return 4;
        } else if (point.equals(gameBoard.getRoom(5).getEntryPoints().get(0)) || point.equals(gameBoard.getRoom(5).getEntryPoints().get(1))){
            return 5;
        } else if (point.equals(gameBoard.getRoom(6).getEntryPoints().get(0))){
            return 6;
        } else if (point.equals(gameBoard.getRoom(7).getEntryPoints().get(0)) || point.equals(gameBoard.getRoom(7).getEntryPoints().get(1)) ||
                point.equals(gameBoard.getRoom(7).getEntryPoints().get(2))){
            return 7;
        } else if (point.equals(gameBoard.getRoom(8).getEntryPoints().get(0))){
            return 8;
        } else if (point.equals(gameBoard.getRoom(9).getEntryPoints().get(0))){
            return 9;
        }

        return parentRoom;
    }

    //  Getters

    public String getCurrentRoomName() {
        switch (currentRoom) {
            case 0:
                return "Kitchen";
            case 1:
                return "Ballroom";
            case 2:
                return "Conservatory";
            case 3:
                return "Dining Room";
            case 4:
                return "Billiard Room";
            case 5:
                return "Library";
            case 6:
                return "Lounge";
            case 7:
                return "Hall";
            case 8:
                return "Study";
            case 9:
                return "Cellar";
            default:
                return "";
        }
    }

    public boolean isInRoom(){
        return super.currentRoom >= 0;
    }

    public int getSuspectID(){
        return super.tokenID;
    }
}
