/**
 * Class which contains the behavior for the suspects of the game
 *
 * Authors :    Jack Geraghty - 163884181
 *              Conor Beenham - 16350851
 *              Alen Thomas   -
 */

package com.Team11.Cluedo.Suspects;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Suspect extends JComponent {
    private String name;
    private Point location;
    private int suspectID;
    private Color playerColour;

    /**
     * Default Constructor
     */
    public Suspect(){
    }

    /**
     * Parametrized Constuctor
     * @param p : The location of the player
     * @param name : The name of the player
     * @param ID : The ID associated with the player
     */
    public Suspect(Point p, String name, int ID, Color c){
        this.name = name;
        this.location = p;
        this.suspectID = ID;
        this.playerColour = c;
    }

    /**
     * Method to set the name of the suspect
     * @param n : The name to give them
     */
    public void setName(String n){
        this.name = n;
    }

    /**
     * Accessor Method to return the name of the suspect
     * @return : The name of the suspect
     */
    public String getName(){
        return this.name;
    }

    /**
     * Method to set the location of the player
     * @param p : The point to assign the player to
     */
    public void setLoc(Point p){
        this.location = p;
    }

    /**
     * Accessor Method to return the current position of the suspect
     * @return
     */
    public Point getLoc(){
        return this.location;
    }

    /**
     * Method to set the ID of the suspect
     * @param i : The id to assign to the suspect
     */
    public void setSuspectID(int i){
        this.suspectID = i;
    }

    public void setPlayerColour(Color c){
        this.playerColour = c;
    }

    public Color getPlayerColour(){
        return this.playerColour;
    }

    /**
     * Accessor Method to return the id of the suspect
     * @return
     */
    public int getSuspectID(){
        return this.suspectID;
    }

    /**
     * Draw method for drawing each of the suspects on the screen
     * @param g : The graphic to draw
     */
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        System.out.println("Draw Called");
        /**
         * Depending on the suspectID of each suspect they will be drawn as different coloured ellipses
         */
        Image playerToken = null;

        /**
         * Player One - Miss White
         * Player Two - Mr. Plum
         * Player Three - Ms. Peacock
         * Player Four - Colonel Mustard
         * Player Five - Mr. Green
         * Player Six - Miss. Scarlett
         */

        g2.setColor(this.getPlayerColour());

        /**
         * Draw the ellipse at an offset of the suspects location and the size of each tile
         */
        g2.fill(new Ellipse2D.Double((this.location.getX() * 25), (this.location.getY() * 25), 25,25));
    }

    /**
     * Method for handling the playerMovement of the suspects
     * Very much in a test state for the first sprint of the assignment
     * Only has very basic playerMovement with no restriction on where the suspect can playerMove to
     */
    public void move(Direction dir, int numMove){
        /**
         * Moving up
         */
        if (dir == Direction.NORTH){
            if (this.getLocation().getY() == 0){
                System.out.println("Cannot move Up, add the edge of board");
            }
            else{
                this.location.setLocation(this.location.getX(), this.location.getY() - numMove);
            }

        }

        /**
         * Moving down
         */
        else if (dir == Direction.SOUTH){
            //if (this.getLoc())
            this.location.setLocation(this.location.getX(), this.location.getY() + numMove);
        }

        /**
         * Moving right
         */
        else if (dir == Direction.EAST) {
            this.location.setLocation(this.location.getX() + numMove, this.location.getY());
        }

        /**
         * Moving left
         */
        else if (dir == Direction.WEST){
            this.location.setLocation(this.location.getX() - numMove, this.location.getY());
        }

        else{
            System.out.println("Unknown Direction");
        }
    }
}
