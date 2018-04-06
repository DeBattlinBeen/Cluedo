package com.team11.cluedo.ui.components;

import com.team11.cluedo.components.T11Label;
import com.team11.cluedo.players.Player;
import com.team11.cluedo.ui.Resolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class PlayerChange extends JPanel {
    private JLabel doneButton, textLabel;
    private Resolution resolution;

    public PlayerChange(Resolution resolution) {
        this.resolution = resolution;

        setupButton("DONE");

        setLayout(new GridBagLayout());
        setBackground(new Color(0,0,0, 168));
        setVisible(false);
    }

    private void setupButton(String text) {
        doneButton = new JLabel(text);
        doneButton.setFont(new Font("Bulky Pixels", Font.BOLD, (int)(30 * resolution.getScalePercentage())));
        doneButton.setForeground(Color.WHITE);
        doneButton.setBackground(new Color(0,0,0,156));
        doneButton.setOpaque(false);
        doneButton.setForeground(Color.WHITE);

    }

    public void setPlayerCard(Player player){
        this.removeAll();

        textLabel = new JLabel(("Pass to player: " + player.getPlayerName()));
        textLabel.setFont(new Font("Bulky Pixels", Font.BOLD, (int)(20 * resolution.getScalePercentage())));
        textLabel.setForeground(Color.WHITE);
        textLabel.setBackground(Color.BLACK);

        ImageIcon icon = new ImageIcon(player.getCardImage().getScaledInstance(
                (int)(player.getCardImage().getWidth(this) * resolution.getScalePercentage()),
                (int)(player.getCardImage().getHeight(this) * resolution.getScalePercentage()),
                0
        ));
        JLabel playerCard = new JLabel(icon);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0; gbc.gridy = 0;
        super.add(textLabel, gbc);
        gbc.gridy = 1;
        super.add(playerCard, gbc);
        gbc.gridy = 2;
        super.add(doneButton, gbc);
    }

    public void setEndGame(Player player){
        this.removeAll();

        textLabel = new JLabel((player.getPlayerName() + " has solved the murder!"));
        textLabel.setFont(new Font("Bulky Pixels", Font.BOLD, (int)(20 * resolution.getScalePercentage())));
        textLabel.setForeground(Color.WHITE);
        textLabel.setBackground(Color.BLACK);

        ImageIcon icon = new ImageIcon(player.getCardImage().getScaledInstance(
                (int)(player.getCardImage().getWidth(this) * resolution.getScalePercentage()),
                (int)(player.getCardImage().getHeight(this) * resolution.getScalePercentage()),
                0
        ));
        JLabel playerCard = new JLabel(icon);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0; gbc.gridy = 0;
        super.add(textLabel, gbc);
        gbc.gridy = 1;
        super.add(playerCard, gbc);
        gbc.gridy = 2;
        super.add(doneButton, gbc);
    }

    public JLabel getDoneButton() {
        return doneButton;
    }

    public void setDoneButton(String text) {
        setupButton(text);
    }
}
