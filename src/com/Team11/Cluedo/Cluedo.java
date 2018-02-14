/**
 * The Main Class of the game.
 *
 * Authors Team11:  Jack Geraghty - 16384181
 *                  Conor Beenham - 16350851
 *                  Alen Thomas   - 16333003
 */

package com.Team11.Cluedo;

import com.Team11.Cluedo.assets.Assets;
import com.Team11.Cluedo.board.Board;
import com.Team11.Cluedo.controls.CommandInput;
import com.Team11.Cluedo.ui.GameScreen;
import com.Team11.Cluedo.ui.MenuScreen;

import java.io.IOException;

public class Cluedo {
    public static void main(String[] args) throws IOException{
        {
            Assets gameAssets = new Assets();

            Board gameBoard = new Board();
            Weapons gameWeapons = new Weapons();

            GameScreen gameScreen = new GameScreen(gameWeapons, gameAssets);
            CommandInput gameInput = new CommandInput(gameScreen, gameWeapons);
            new MenuScreen(gameAssets, gameScreen, gameInput);
        }
    }

}
