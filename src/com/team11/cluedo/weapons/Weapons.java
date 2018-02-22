/**
 * Code to handle the weapons of the cluedo game and where they should be placed on to the gameBoard and how they are drawn
 *
 * Authors :    Jack Geraghty - 16384181
 *              Conor Beenham -
 *              Alen Thomas   -
 */

package com.team11.cluedo.weapons;


import com.team11.cluedo.board.Board;
import com.team11.cluedo.board.WeaponPoints;
import com.team11.cluedo.suspects.Suspect;
import com.team11.cluedo.suspects.SuspectData;
import com.team11.cluedo.ui.Resolution;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class Weapons extends JComponent implements Iterable<Weapon>, Iterator<Weapon> {

    private final int NUM_WEAPONS = 6;

    /*
     * NOTES on RoomID for moveWeaponToRoom Method
     * RoomID 0 - kitchen
     * RoomID 1 - ballroom
     * RoomID 2 - Conservatory
     * RoomID 3 - dining room
     * RoomID 4 - billiard room
     * RoomID 5 - library
     * RoomID 6 - lounge
     * RoomID 7 - hall
     * RoomID 8 - study
     */

    /*
     * Private inner class to handle each individual weapon
     * Contains information for each weapon such as its name location and id
     */


    /*
     * Array of all of the weapons for the game
     */

    private Iterator<Weapon> iterator;
    private final HashSet<Weapon> weapons = new HashSet<>();

    private Board gameBoard;

    public Weapons(Board gameBoard, Resolution resolution){
        this.gameBoard = gameBoard;

        setupWeapons(resolution);
        for (Weapon weapon : weapons){
            addWeaponToBoard(weapon.getWeaponID());
        }
    }

    private void setupWeapons(Resolution resolution) {
        WeaponData weaponData = new WeaponData();
        for(int i = 0 ; i < NUM_WEAPONS ; i++) {
            weapons.add(new Weapon(i, weaponData.getWeaponName(i), weaponData.getWeaponToken(i), resolution));
        }
    }

    private void addWeaponToBoard(int weaponID){
        Random random = new Random();
        int randomInt = random.nextInt(gameBoard.getRooms().size()-1);

        Point spawnPoint = gameBoard.getRoom(randomInt).getRandomPoint(gameBoard.getRoom(randomInt).getWeaponPositions());
        this.getWeapon(weaponID).setLocation(spawnPoint);
        this.getWeapon(weaponID).setCurrentRoom(randomInt);
        gameBoard.getRoom(randomInt).getWeaponPositions().remove(spawnPoint);
    }

    public void moveWeaponToRoom(int weaponID, int roomID){
        Point currentPoint = this.getWeapon(weaponID).getLocation();
        int currRoom = this.getWeapon(weaponID).getCurrentRoom();

        Point nextPoint = (gameBoard.getRoom(roomID).getRandomPoint(gameBoard.getRoom(roomID).getWeaponPositions()));

        gameBoard.getRoom(currRoom).getWeaponPositions().add(currentPoint);
        this.getWeapon(weaponID).setLocation(nextPoint);
        gameBoard.getRoom(roomID).getWeaponPositions().remove(nextPoint);
    }

    public int getNumWeapons() {
        return NUM_WEAPONS;
    }

    /*
     * Method to get a weapon at a specific index in the weapons array
     * @param i : The index of the weapon
     * @return : Reference to weapon at index i
     */

    public Weapon getWeapon(int index) {
        for(Weapon weapon : weapons) {
            if (weapon.getWeaponID() == index) {
                return weapon;
            }
        }
        return null;
    }

    @Override
    public void paintComponent(Graphics g){
        for (Weapon weapon : weapons) {
            weapon.draw(g);
        }
    }

    @Override
    public Iterator<Weapon> iterator() {
        iterator = weapons.iterator();
        return iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Weapon next() {
        return iterator.next();
    }
}
