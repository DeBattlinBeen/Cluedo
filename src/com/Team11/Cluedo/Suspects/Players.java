/**
 * Code to handle the players in the game.
 *
 * Authors Team11:  Jack Geraghty - 16384181
 *                  Conor Beenham - 16350851
 *                  Alen Thomas   - 16333003
 */


package com.Team11.Cluedo.Suspects;

import com.Team11.Cluedo.Assets.Assets;

import javax.swing.*;
import java.awt.*;

public class Players extends JComponent {
    private int numPlayers;
    private int remainingPlayers;

    private final Point[] SUSPECT_SPAWN = new Point[]
            {new Point(10,1), new Point(15,1), new Point(24,7),
             new Point(24,20), new Point(8,25), new Point(1,18)};
    private final String[] PLAYER_NAME = new String[] {
            "Miss White", "Mr. Green", "Ms. Peacock", "Mr. Plum", "Miss Scarlett", "Colonel Mustard"};
    private Image[] playerTokens;

    /**
     * Player One - Miss White
     * Player Two - Mr. Green
     * Player Three - Ms. Peacock
     * Player Four - Mr. Plum
     * Player Five - Miss Scarlett
     * Player Six - Colonel Mustard
     *
     * */
    private final Color[] PLAYER_COLORS = {Color.WHITE, Color.GREEN, Color.BLUE, new Color(168, 2, 221), Color.RED, Color.YELLOW};
    private Suspect[] players;

    public Players(int numPlayers, Assets gameAssets){
        this.numPlayers = numPlayers;
        this.remainingPlayers = numPlayers;
        this.players = new Suspect[this.numPlayers];

        playerTokens = new Image[] {gameAssets.getWhiteToken(), gameAssets.getGreenToken(), gameAssets.getPeacockToken(),
        gameAssets.getPlumToken(), gameAssets.getScarletToken(), gameAssets.getMustardToken()};

        for (int i = 0; i < this.numPlayers; i++){
            players[i] = new Suspect(SUSPECT_SPAWN[i], PLAYER_NAME[i], i, PLAYER_COLORS[i], playerTokens[i]);
        }
    }

    @Override
    public void paintComponent(Graphics g){
        for (Suspect suspect : players) {
            suspect.draw(g);
        }
    }

    public void playerMove(int player, Direction dir, int space) {
        players[player].move(dir,space);
    }

    public int getRemainingPlayers() {
        return remainingPlayers;
    }

    public void setRemainingPlayers(int amount) {
        this.remainingPlayers = amount;
    }
}
