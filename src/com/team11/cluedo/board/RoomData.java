package com.team11.cluedo.board;

import com.team11.cluedo.assets.Assets;

import java.awt.*;
import java.util.HashMap;

public class RoomData {
    private final Assets gameAssets = new Assets();

    private HashMap<Integer, String> roomName = new HashMap<>();
    private HashMap<Integer, Image> roomCard = new HashMap<>();
    private HashMap<Integer, Image> roomSelectedCard = new HashMap<>();
    private final int roomAmount = 9;

    public RoomData() {
        setRoomName();
        setRoomCard();
        setRoomSelectedCard();
    }

    private void setRoomName() {
        roomName.put(0, "Kitchen");
        roomName.put(1, "Ballroom");
        roomName.put(2, "Conservatory");
        roomName.put(3, "Dining Room");
        roomName.put(4, "Billiard Room");
        roomName.put(5, "Library");
        roomName.put(6, "Lounge");
        roomName.put(7, "Hall");
        roomName.put(8, "Study");
    }

    private void setRoomCard() {
        roomCard.put(0, gameAssets.getKitchenCard());
        roomCard.put(1, gameAssets.getBallroomCard());
        roomCard.put(2, gameAssets.getConservatoryCard());
        roomCard.put(3, gameAssets.getDiningCard());
        roomCard.put(4, gameAssets.getBilliardCard());
        roomCard.put(5, gameAssets.getLibraryCard());
        roomCard.put(6, gameAssets.getLoungeCard());
        roomCard.put(7, gameAssets.getHallCard());
        roomCard.put(8, gameAssets.getStudyCard());
    }

    private void setRoomSelectedCard() {
        roomSelectedCard.put(0, gameAssets.getSelectedKitchenCard());
        roomSelectedCard.put(1, gameAssets.getSelectedBallroomCard());
        roomSelectedCard.put(2, gameAssets.getSelectedConservatoryCard());
        roomSelectedCard.put(3, gameAssets.getSelectedDiningCard());
        roomSelectedCard.put(4, gameAssets.getSelectedBilliardCard());
        roomSelectedCard.put(5, gameAssets.getSelectedLibraryCard());
        roomSelectedCard.put(6, gameAssets.getSelectedLoungeCard());
        roomSelectedCard.put(7, gameAssets.getSelectedHallCard());
        roomSelectedCard.put(8, gameAssets.getSelectedStudyCard());
    }


    public String getRoomName(int index) {
        return roomName.getOrDefault(index, null);
    }

    public Image getRoomCard(int index) {
        return roomCard.getOrDefault(index, null);
    }

    public Image getSelectedRoomCard(int index) {
        return roomSelectedCard.getOrDefault(index, null);
    }

    public int getRoomAmount() {
            return this.roomAmount;
        }
}
