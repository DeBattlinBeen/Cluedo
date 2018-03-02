/*
 * Code to handle the behaviour of the suspects.
 * Main Author : Jack Geraghty
 * Authors Team11:  Jack Geraghty - 16384181
 *                  Conor Beenham - 16350851
 *                  Alen Thomas   - 16333003
 */

package com.team11.cluedo.suspects;

import com.team11.cluedo.board.Board;
import com.team11.cluedo.board.room.TileType;
import com.team11.cluedo.ui.Resolution;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class Suspect extends JComponent{
    private int suspectID;
    private String suspectName;
    private Point location;
    private Image tokenImage;
    private Resolution resolution;
    private int currentRoom;
    private Point previousLocation;
    private Point lastPoint;

    public Suspect(int suspectID, String suspectName, Point location, Image tokenImage, Resolution resolution){
        this.suspectID = suspectID;
        this.suspectName = suspectName;
        this.location = location;
        this.tokenImage = tokenImage;
        this.resolution = resolution;
        this.currentRoom = -1;
        this.previousLocation = null;
    }

    private boolean checkMove(ArrayList<Direction> moveList, Board gameBoard) {
        Point testerPoint = new Point(this.getLoc());
        ArrayList<Direction> tmpList = new ArrayList<>(moveList);

        boolean isValid = true;
        HashSet<String> validitySet = new HashSet<>();  //Hashset used to hold true and false values. Values are inserted based on whether a move is valid or not
        int moveCounter = 0;
        boolean doOnce = true;
        boolean hasDoor = false;
        Point lastLoc;  //The last location of the player, used to check to see if they are entering a room from a doormat tile

        //Check through all the moves to see if they are valid
        //If they aren't then add false to the validity set
        while (!tmpList.isEmpty() && !validitySet.contains("false") && !hasDoor ) {
            //Update the last location
            lastLoc = testerPoint.getLocation();

            switch (tmpList.get(0)){
                case NORTH:
                    if (gameBoard.getBoardPos((int)testerPoint.getLocation().getY()-1, (int)testerPoint.getLocation().getX()).isTraversable()){
                        if (gameBoard.getBoardPos((int)testerPoint.getLocation().getY()-1, (int)testerPoint.getLocation().getX()).getType() == TileType.DOOR && !(gameBoard.getBoardPos((int)lastLoc.getY(), (int)lastLoc.getX()).getType() == TileType.DOORMAT)){
                            System.out.println("Cannot move into room from this side of tile");
                            validitySet.add("false");
                        }
                        testerPoint.setLocation((int)testerPoint.getLocation().getX(), (int)testerPoint.getLocation().getY()-1);
                        validitySet.add("true");
                    }

                    else{
                        if (doOnce){
                            System.out.println(gameBoard.getBoardPos((int)testerPoint.getLocation().getY()-1, (int)testerPoint.getLocation().getX()));
                            System.out.println("Problem with move u @ position " + moveCounter);
                            for (int i = 0; i < moveCounter; i++){
                                System.out.println(moveList.get(i) + " ");
                            }
                            doOnce = false;
                        }
                        validitySet.add("false");
                    }
                    break;
                case EAST:
                    if (gameBoard.getBoardPos((int)testerPoint.getLocation().getY(), (int)testerPoint.getLocation().getX()+1).isTraversable()){
                        if (gameBoard.getBoardPos((int)testerPoint.getLocation().getY(), (int)testerPoint.getLocation().getX()+1).getType() == TileType.DOOR && !(gameBoard.getBoardPos((int)lastLoc.getY(), (int)lastLoc.getX()).getType() == TileType.DOORMAT)){
                            System.out.println("Cannot move into room from this side of tile");
                            validitySet.add("false");
                        }
                        testerPoint.setLocation((int)testerPoint.getLocation().getX()+1, (int)testerPoint.getLocation().getY());
                        validitySet.add("true");
                    }

                    else{
                        if (doOnce){
                            System.out.println(gameBoard.getBoardPos((int)testerPoint.getLocation().getY(), (int)testerPoint.getLocation().getX()+1));
                            System.out.println("Problem with move u @ position " + moveCounter);
                            for (int i = 0; i < moveCounter; i++){
                                System.out.println(moveList.get(i) + " ");
                            }
                            doOnce = false;
                        }
                        validitySet.add("false");
                    }
                    break;
                case SOUTH:
                    if (gameBoard.getBoardPos((int)testerPoint.getLocation().getY()+1, (int)testerPoint.getLocation().getX()).isTraversable()){
                        if (gameBoard.getBoardPos((int)testerPoint.getLocation().getY()+1, (int)testerPoint.getLocation().getX()).getType() == TileType.DOOR && !(gameBoard.getBoardPos((int)lastLoc.getY(), (int)lastLoc.getX()).getType() == TileType.DOORMAT)){
                            System.out.println("Cannot move into room from this side of tile");
                            validitySet.add("false");
                        }
                        testerPoint.setLocation((int)testerPoint.getLocation().getX(), (int)testerPoint.getLocation().getY()+1);
                        validitySet.add("true");
                    }

                    else{
                        if (doOnce){
                            System.out.println(gameBoard.getBoardPos((int)testerPoint.getLocation().getY()+1, (int)testerPoint.getLocation().getX()));
                            System.out.println("Problem with move u @ position " + moveCounter);
                            for (int i = 0; i < moveCounter; i++){
                                System.out.println(moveList.get(i) + " ");
                            }
                            doOnce = false;
                        }
                        validitySet.add("false");
                    }
                    break;
                case WEST:
                    if (gameBoard.getBoardPos((int)testerPoint.getLocation().getY(), (int)testerPoint.getLocation().getX()-1).isTraversable()){
                        if (gameBoard.getBoardPos((int)testerPoint.getLocation().getY(), (int)testerPoint.getLocation().getX()-1).getType() == TileType.DOOR && !(gameBoard.getBoardPos((int)lastLoc.getY(), (int)lastLoc.getX()).getType() == TileType.DOORMAT)){
                            System.out.println("Cannot move into room from this side of tile");
                            validitySet.add("false");
                        }
                        testerPoint.setLocation((int)testerPoint.getLocation().getX()-1, (int)testerPoint.getLocation().getY());
                        validitySet.add("true");
                    }

                    else{
                        if (doOnce){
                            System.out.println(gameBoard.getBoardPos((int)testerPoint.getLocation().getY(), (int)testerPoint.getLocation().getX()-1));
                            System.out.println("Problem with move u @ position " + moveCounter);
                            for (int i = 0; i < moveCounter; i++){
                                System.out.print(moveList.get(i) + " ");
                            }
                            doOnce = false;
                        }
                        validitySet.add("false");
                    }
                    break;
                default:
                    break;
            }
            tmpList.remove(0);
            moveCounter++;
            if (gameBoard.getBoardPos((int)testerPoint.getLocation().getY(), (int)testerPoint.getLocation().getX()).getType() == TileType.DOOR && gameBoard.getBoardPos((int)lastLoc.getY(), (int)lastLoc.getX()).getType() == TileType.DOORMAT){
                hasDoor = true;
            }
        }

        //Have checked all of the moves to see if they go to any non traversal
        //Now check to see of testerPoint is on a tile that is already occupied and that the validity set doesn't contain false
        if ((gameBoard.getBoardPos((int)testerPoint.getLocation().getY(), (int) testerPoint.getLocation().getX()).isOccupied() && !(gameBoard.getBoardPos((int)testerPoint.getLocation().getY(), (int) testerPoint.getLocation().getX()).getType() == TileType.DOOR) || validitySet.contains("false"))){
            isValid = false;
            if ((gameBoard.getBoardPos((int)testerPoint.getLocation().getY(), (int) testerPoint.getLocation().getX()).isOccupied())){
                System.out.println("Move will result in the current player landing on another player");
            } else
            {
                System.out.println("Moves entered are invalid");
            }
        }

        //Check to see if they tried to enter a door
        if (hasDoor){
            isValid = true;
        }

        //Return whether the player should move or not
        return isValid;
    }

    /*
     * Method for handling the playerMovement of the suspects
     */
    public boolean move(Board gameBoard, ArrayList<Direction> moveList){
        boolean isValid = checkMove(moveList, gameBoard);
        boolean doMoveToRoom = false;
        //Future use
        Stack<Direction> reverseStack = new Stack<>();

        if (isValid){
            System.out.println("Moving");
            this.setPreviousLocation(this.getLoc());
            gameBoard.getBoardPos((int)this.getLoc().getY(), (int)this.getLoc().getX()).setOccupied(false);
            while(!moveList.isEmpty() && !doMoveToRoom){
                this.setLastPoint(this.getLoc());
                switch (moveList.get(0)){
                    case NORTH:
                        moveUp();
                        reverseStack.push(moveList.remove(0));
                        if (gameBoard.getBoardPos((int)this.getLoc().getY(), (int)this.getLoc().getX()).getType() == TileType.DOOR){
                            moveToRoom(gameBoard);
                            doMoveToRoom = true;
                        }
                        break;
                    case EAST:
                        moveRight();
                        reverseStack.push(moveList.remove(0));
                        if (gameBoard.getBoardPos((int)this.getLoc().getY(), (int)this.getLoc().getX()).getType() == TileType.DOOR){
                            moveToRoom(gameBoard);
                            doMoveToRoom = true;
                        }
                        break;
                    case SOUTH:
                        moveDown();
                        reverseStack.push(moveList.remove(0));
                        if (gameBoard.getBoardPos((int)this.getLoc().getY(), (int)this.getLoc().getX()).getType() == TileType.DOOR){
                            moveToRoom(gameBoard);
                            doMoveToRoom = true;
                        }
                        break;

                    case WEST:
                        moveLeft();
                        reverseStack.push(moveList.remove(0));
                        if (gameBoard.getBoardPos((int)this.getLoc().getY(), (int)this.getLoc().getX()).getType() == TileType.DOOR){
                            moveToRoom(gameBoard);
                            doMoveToRoom = true;
                        }
                        break;
                    default:
                        break;
                }
                //Ask to see if we can use thread.sleep here
            }
            System.out.println(this.getLoc());
            gameBoard.getBoardPos((int)this.getLoc().getY(), (int)this.getLoc().getX()).setOccupied(true);
        }

        else{
            System.out.println("Moves desired are invalid");
        }
        return isValid;
    }

    //Method to move up
    private void moveUp(){
        this.getLoc().setLocation((int)this.getLoc().getX(), (int)this.getLoc().getY()-1);

    }
    //Method to move down
    private void moveDown(){
        this.getLoc().setLocation((int)this.getLoc().getX(), (int)this.getLoc().getY()+1);
    }
    //Method to move right
    private void moveRight(){
        this.getLoc().setLocation((int)this.getLoc().getX()+1, (int)this.getLoc().getY());
    }
    //Method to move left
    private void moveLeft(){
        this.getLoc().setLocation((int) this.getLoc().getX() - 1, (int) this.getLoc().getY());
    }

    //Method to move a player into a room when they land on a door tile
    private void moveToRoom(Board gameBoard){
        int currRoom = findParentRoom(gameBoard.getBoardPos((int)this.getLoc().getY(), (int)this.getLoc().getX()).getLocation(), gameBoard);
        Point nextPoint = gameBoard.getRoom(currRoom).getRandomPoint(gameBoard.getRoom(currRoom).getPlayerPositions());
        this.setLoc(nextPoint);
        this.setCurrentRoom(currRoom);
        gameBoard.getRoom(currRoom).getPlayerPositions().remove(nextPoint);
    }

    //Method to move the player out of the room
    public void moveOutOfRoom(Board gameBoard, int exitNum){
        //Get the current point and add it back to the roomSpawn points
        Point currPoint = new Point(this.getLoc());
        Point nextPoint = new Point(gameBoard.getRoom(this.getCurrentRoom()).getExitPoints().get(exitNum));
        gameBoard.getRoom(this.getCurrentRoom()).getPlayerPositions().add(currPoint);
        this.setLoc(nextPoint);
        this.setCurrentRoom(-1);

    }

    public boolean useSecretPassageWay(Board gameBoard){
        Point currentPoint = new Point(this.getLoc());

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
            this.setLoc(gameBoard.getRoom(this.getCurrentRoom()).getRandomPoint(gameBoard.getRoom(this.getCurrentRoom()).getPlayerPositions()));
            System.out.println("Moved to " + gameBoard.getRoom(this.getCurrentRoom()) + " using a secret passageway");
            return true;

        } else{
            System.out.println("This room does not have a secret passageway to use");
            return false;
        }

    }

    //Method which finds what room a suspect is in based on the door tile that they are on
    private int findParentRoom(Point point, Board gameBoard){
        int parentRoom = 0;
        System.out.println(this.getCurrentRoom());
        System.out.println("Checking Point " + point);
        System.out.println("Door location" + gameBoard.getRoom(3).getEntryPoints().get(1));
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
            System.out.println(gameBoard.getRoom(6).getEntryPoints().get(0));
            return 6;
        } else if (point.equals(gameBoard.getRoom(7).getEntryPoints().get(0)) || point.equals(gameBoard.getRoom(7).getEntryPoints().get(1)) ||
                point.equals(gameBoard.getRoom(7).getEntryPoints().get(2))){
            return 7;
        } else if (point.equals(gameBoard.getRoom(8).getEntryPoints().get(0))){
            return 8;
        } else if (point.equals(gameBoard.getRoom(9).getEntryPoints().get(0))){
            return 9;
        } else {
            System.out.println("Something's not right");
        }

        return parentRoom;
    }

    public void reverseMoves(){
        this.setLoc(this.getPreviousLocation());
    }

    private Direction reverseDirection(Direction dir){
        switch (dir){
            case NORTH:
                return Direction.SOUTH;
            case SOUTH:
                return Direction.NORTH;
            case WEST:
                return  Direction.EAST;
            case EAST:
                return Direction.WEST;
            default:
                return dir;
        }
    }

    //  Setters

    private void setPreviousLocation(Point point){
        this.previousLocation = point;
    }

    private void setLastPoint(Point point){
        this.lastPoint = point;
    }

    private void setLoc(Point p){
        this.location = p;
        System.out.println("Location has been changed to ");
    }

    public void setSuspectID(int i){
        this.suspectID = i;
    }

    private void setCurrentRoom(int currRoom){
        this.currentRoom = currRoom;
    }

    //  Getters

    private Point getPreviousLocation(){
        return this.previousLocation;
    }

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
        return currentRoom >= 0;
    }

    public Point getLastPoint(){
        return this.lastPoint;
    }

    public String getSuspectName() {
        return this.suspectName;
    }

    public Point getLoc(){
        return this.location;
    }

    public int getSuspectID(){
        return this.suspectID;
    }

    public int getCurrentRoom(){
        return this.currentRoom;
    }

    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(this.tokenImage, (int)(this.location.getX() * ((int)(30 * this.resolution.getScalePercentage()))),
                (int)(this.location.getY() * ((int)(30 * this.resolution.getScalePercentage()))),
                ((int)(30 * this.resolution.getScalePercentage())),  ((int)(30 * this.resolution.getScalePercentage())),null);
    }
}
