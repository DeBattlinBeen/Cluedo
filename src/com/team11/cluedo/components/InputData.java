/*
 * Code to handle all the command data and parameters.
 *
 * Authors Team11:  Jack Geraghty - 16384181
 *                  Conor Beenham - 16350851
 *                  Alen Thomas   - 16333003
 */

package com.team11.cluedo.components;

import com.team11.cluedo.board.room.RoomData;
import com.team11.cluedo.suspects.SuspectData;
import com.team11.cluedo.weapons.WeaponData;

import java.util.ArrayList;

public class InputData {

    private ArrayList<String> commandData = new ArrayList<>();

    public InputData(){
        addElement("roll", this.commandData);
        addElement("quit", this.commandData);
        addElement("passage", this.commandData);
        addElement("done", this.commandData);
        addElement("help", this.commandData);
        addElement("exit", this.commandData);
        addElement("weapon", this.commandData);
        addElement("move", this.commandData);
        addElement("cheat", this.commandData);
        addElement("notes", this.commandData);

        addElement("finished", this.commandData);
        addElement("question", this.commandData);
        addElement("cards", this.commandData);
        addElement("log", this.commandData);
        addElement("show", this.commandData);
        addElement("hide", this.commandData);

        SuspectData suspectData = new SuspectData();
        for (int i = 0; i < suspectData.getSuspectAmount(); i++){
            addElement(suspectData.getSuspectID(i), this.commandData);
        }

        RoomData roomData = new RoomData();
        for (int i = 0; i < RoomData.ROOM_AMOUNT ; i++){
            addElement(roomData.getRoomID(i), this.commandData);
        }

        WeaponData weaponData = new WeaponData();
        for (int i = 0; i < WeaponData.WEAPON_AMOUNT ; i++){
            addElement(weaponData.getWeaponID(i), this.commandData);
        }
    }

    private void addElement(String s, ArrayList<String> list){
        list.add(s);
    }

    public ArrayList<String> getCommandData(){
        return this.commandData;
    }

}
