package com.Team11.Cluedo.Controls;

import javax.swing.*;

public class ChoiceOption {

    public String weapon;
    public String room;

    public ChoiceOption()
    {
        makeChoice();
    }

    public void makeChoice()
    {
        String[] weaponchoice = { "Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner" };
        weapon = (String) JOptionPane.showInputDialog(null, "Choose your Weapon",
                "Weapon Movement", JOptionPane.QUESTION_MESSAGE, null, weaponchoice, weaponchoice[0]);

        String[] roomchoice = { "Kitchen", "Ballroom", "Conservatory", "Biliard Room", "Library", "Study", "Hall", "Lounge", "Dining Room", "Cellar" };
        room = (String) JOptionPane.showInputDialog(null, "Choose your Room.",
                "Weapon Movement", JOptionPane.QUESTION_MESSAGE, null, roomchoice, roomchoice[0]);
    }

    public String getRoom() {
        return room;
    }

    public String getWeapon() {
        return weapon;
    }

  }
