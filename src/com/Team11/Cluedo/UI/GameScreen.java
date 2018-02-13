/**
 * Code to handle the creation of the gamescreen and UI.
 *
 * Authors Team11:  Jack Geraghty - 16384181
 *                  Conor Beenham - 16350851
 *                  Alen Thomas   - 16333003
 */


package com.Team11.Cluedo.UI;

import com.Team11.Cluedo.Assets.Assets;
import com.Team11.Cluedo.Board.Board;
import com.Team11.Cluedo.Suspects.Players;
import com.Team11.Cluedo.UI.Panel.BackgroundPanel;
import com.Team11.Cluedo.Weapons;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;

public class GameScreen implements Screen {
    private JFrame frame;
    private JPanel mainPanel;
    private BoardUI boardPanel;

    private JTextArea infoOutput;
    private JButton testButton;
    private String input;
    private JTextField commandInput;

    private Players gamePlayers;
    private Board gameBoard;
    private Weapons gameWeapons;

    private Assets gameAssets;

    public GameScreen(Players gamePlayers, Board gameBoard, Weapons gameWeapons, Assets gameAssets) throws IOException{
        this.gamePlayers = gamePlayers;
        this.gameBoard = gameBoard;
        this.gameWeapons = gameWeapons;
        this.gameAssets = gameAssets;

        this.createScreen();
        this.setupScreen();
        this.displayScreen();
        frame.getRootPane().setDefaultButton(testButton);
    }

    @Override
    public void createScreen() {
        this.frame = new JFrame("Cluedo");
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void setupScreen() {
        this.mainPanel = new JPanel(new BorderLayout());
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);

        ImageIcon backgroundImage = this.gameAssets.getBackgroundTile();
        Image bgroundImage = backgroundImage.getImage();
        BackgroundPanel backgroundPanel = new BackgroundPanel(bgroundImage,BackgroundPanel.TILED);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3,3,1,3);

        JPanel cardPanel = setupCardPanel();
        gbc.gridx = 0; gbc.gridy = 11;
        gbc.gridwidth = 2; gbc.gridheight = 2;
        contentPanel.add(cardPanel, gbc);

        JPanel commandPanel = setupCommandPanel();
        gbc.gridx = 11; gbc.gridy = 7;
        gbc.gridwidth = 1; gbc.gridheight = 2;
        contentPanel.add(commandPanel, gbc);

        JPanel infoPanel = setupInfoPanel();
        gbc.gridx = 11; gbc.gridy = 0;
        gbc.gridwidth = 2; gbc.gridheight = 7;
        gbc.fill = GridBagConstraints.BOTH;
        contentPanel.add(infoPanel, gbc);

        this.boardPanel = new BoardUI(this.gamePlayers, this.gameBoard, this.gameWeapons, new BoardComponent());
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 10; gbc.gridheight = 10;
        gbc.fill = GridBagConstraints.NONE;
        contentPanel.add(boardPanel, gbc);

        backgroundPanel.add(contentPanel);
        mainPanel.add(backgroundPanel);
        this.frame.getContentPane().add(this.mainPanel);

        this.frame.pack();
    }

    @Override
    public void displayScreen() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setLocation(dimension.width/2 - frame.getSize().width/2, dimension.height/2 - (frame.getSize().height/2));
        this.frame.setVisible(true);
    }

    @Override
    public void closeScreen() {
        this.frame.removeAll();
        this.frame.dispose();
    }

    @Override
    public void reDraw() {
        this.boardPanel.repaint();
    }

    private JPanel setupCommandPanel() {
        JPanel commandPanel = new JPanel(new BorderLayout());
        commandPanel.setBorder(new TitledBorder("CommandInput Entry"));
        commandInput = new JTextField(30);

        testButton = new JButton("Input");
        commandPanel.add(commandInput, BorderLayout.LINE_START);
        commandPanel.add(testButton, BorderLayout.LINE_END);

        return commandPanel;
    }

    private JPanel setupInfoPanel() {
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoOutput = new JTextArea(35, 30);
        infoOutput.setEditable(false); infoOutput.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(infoOutput);
        infoPanel.add(scrollPane, BorderLayout.CENTER);


        return infoPanel;
    }

    private JPanel setupCardPanel() {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.DARK_GRAY);

        return cardPanel;
    }

    public JTextArea getInfoOutput() {
        return infoOutput;
    }

    public JButton getTestButton() {
        return testButton;
    }

    public JTextField getCommandInput() {
        return commandInput;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Players getGamePlayers() {
        return gamePlayers;
    }

    public class BoardComponent extends JComponent {
        @Override
        public void paintComponent(Graphics g) {
            Image boardImage = gameAssets.getBoardImage();
            g.drawImage(boardImage, 0, 0, this);
        }
    }

    public class BoardUI extends JLayeredPane {
        Players gamePlayers;
        Board gameBoard;
        Weapons gameWeapons;
        BoardComponent boardComponent;

        public BoardUI(Players players, Board board, Weapons weapons, BoardComponent boardImage) {
            this.gamePlayers = players;
            this.gameBoard = board;
            this.gameWeapons = weapons;
            this.boardComponent = boardImage;

            this.add(this.gameBoard, new Integer(0));
            this.add(this.boardComponent, new Integer(1));
            this.add(this.gamePlayers, new Integer(2));
            this.add(this.gameWeapons, new Integer(3));

            Dimension imageSize = new Dimension(650, 675);
            this.setPreferredSize(imageSize);
        }

        @Override
        public void paint(Graphics g) {
            //gameBoard.paintComponent(g);
            boardComponent.paintComponent(g);
            gamePlayers.paintComponent(g);
            gameWeapons.paintComponent(g);
        }
    }
}
