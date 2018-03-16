/*
  Code to handle the creation of the gamescreen and UI.

  Authors Team11:  Jack Geraghty - 16384181
                   Conor Beenham - 16350851
                   Alen Thomas   - 16333003
 */

package com.team11.cluedo.ui;

import com.team11.cluedo.assets.Assets;
import com.team11.cluedo.components.Autocomplete;
import com.team11.cluedo.components.InputData;

import com.team11.cluedo.board.Board;
import com.team11.cluedo.players.Players;
import com.team11.cluedo.suspects.Suspect;
import com.team11.cluedo.suspects.Suspects;
import com.team11.cluedo.ui.components.*;
import com.team11.cluedo.ui.panel.BackgroundPanel;
import com.team11.cluedo.weapons.Weapons;
import com.team11.cluedo.cards.Cards;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class GameScreen extends JFrame implements Screen {
    private static final String COMMIT_ACTION = "commit";

    private BoardUI boardPanel;
    private PlayerLayout playerPanel;
    private PlayerHandLayout playerHandPanel;
    private NotesPanel notesPanel;

    private MoveOverlay moveOverlay;
    private DoorOverlay doorOverlay;

    private JTextArea infoOutput;
    private JTabbedPane infoTabs;
    private JTextField commandInput;

    private Board gameBoard;
    private Suspects gameSuspects;
    private Weapons gameWeapons;
    private Players gamePlayers;
    private Assets gameAssets;
    private Cards gameCards;

    private Resolution resolution;
    private Dimension currSize;

    public GameScreen(Board gameBoard, Suspects gameSuspects, Weapons gameWeapons, Players gamePlayers, Assets gameAssets, Resolution resolution) throws IOException{
        this.gameBoard = gameBoard;
        this.gameSuspects = gameSuspects;
        this.gameWeapons = gameWeapons;
        this.gamePlayers = gamePlayers;
        this.gameAssets = gameAssets;
        this.resolution = resolution;
        this.gameCards = new Cards(resolution);
        this.moveOverlay = new MoveOverlay(this.getGamePlayers().getPlayer(0), this.resolution);
        this.doorOverlay = new DoorOverlay(this.getGamePlayers().getPlayer(0), this.resolution);
    }

    @Override
    public void createScreen(String name) {
        super.setName(name);
        super.setResizable(false);
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void setupScreen(int state) {
        ImageIcon backgroundTile = this.gameAssets.getBackgroundTile();
        Image backgroundImage = backgroundTile.getImage();
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage, BackgroundPanel.TILED);

        this.playerPanel = setupPlayerPanel();
        this.playerHandPanel = setupCardPanel();
        this.notesPanel = new NotesPanel(gamePlayers);
        this.boardPanel = new BoardUI();
        JPanel infoPanel = setupInfoPanel();

        int index = 0;
        backgroundPanel.add(playerPanel, BorderLayout.WEST, index++);
        backgroundPanel.add(boardPanel, BorderLayout.CENTER, index++);
        backgroundPanel.add(infoPanel, BorderLayout.EAST, index++);

        this.add(backgroundPanel);
        this.pack();
    }
    @Override
    public void displayScreen() {
        setLocation(resolution.getScreenSize().width/2 - getSize().width/2, resolution.getScreenSize().height/2 - (getSize().height/2));
        setVisible(true);
        currSize = getSize();
    }

    @Override
    public void closeScreen() {
        removeAll();
        dispose();
        System.exit(0);
    }

    @Override
    public void reDraw(int currentPlayer) {
        this.playerPanel.reDraw(currentPlayer);
        this.playerHandPanel.reDraw(currentPlayer);
        this.notesPanel.reDraw(currentPlayer);
    }

    public void reDrawFrame() {
        if (new Dimension(getPreferredSize().width, getSize().height) != currSize) {
            setSize(new Dimension(getPreferredSize().width, getSize().height));
            currSize = new Dimension(getPreferredSize().width, getSize().height);
        }
        repaint();
    }

    private JPanel setupCommandPanel() {
        JPanel commandPanel = new JPanel(new BorderLayout());
        Font f = new Font("Orange Kid",Font.BOLD, (int)(25*resolution.getScalePercentage()));

        commandInput = new JTextField(15);
        commandInput.setFont(f);
        commandInput.setBackground(Color.WHITE);
        commandInput.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        commandInput.setForeground(Color.DARK_GRAY);

        //////////////////////////////////////////////////////////////////////////////
        /*
        Code for auto-completing commands
        Only add or remove commands to the commandData arrayList in InputData class
        Don't change any other code in this section
        */
        commandInput.setFocusTraversalKeysEnabled(false);
        InputData inputData = new InputData();
        Autocomplete autoComplete = new Autocomplete(commandInput, inputData.getCommandData());
        commandInput.getDocument().addDocumentListener(autoComplete);
        commandInput.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
        commandInput.getActionMap().put(COMMIT_ACTION, autoComplete.new CommitAction());
        ////////////////////////////////////////////////////////////////////////////////

        commandPanel.add(commandInput, BorderLayout.CENTER);
        commandPanel.setOpaque(false);

        return commandPanel;
    }

    private JTextArea setupHelpPanel() {
        JTextArea helpOutput = new JTextArea(28, 25);
        int fontSize = (int)(18 * resolution.getScalePercentage());
        helpOutput.setFont(new Font("Orange Kid",Font.BOLD, fontSize));
        helpOutput.setEditable(false); infoOutput.setLineWrap(true);
        helpOutput.setBackground(Color.DARK_GRAY);
        helpOutput.setForeground(Color.WHITE);
        helpOutput.setBorder(null);

        try {
            InputStream in = getClass().getResourceAsStream("helpPanel.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while ((strLine = br.readLine()) != null)   {

                helpOutput.append(strLine + "\n");
            }
            in.close();

        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }

        return helpOutput;
    }

    private JPanel setupInfoPanel() {
        UIManager.put("TabbedPane.selected", gameAssets.getDarkerGrey());
        UIManager.put("TabbedPane.contentAreaColor", gameAssets.getDarkerGrey());

        int fontSize = (int)(25 * resolution.getScalePercentage());
        Font f = new Font("Orange Kid", Font.BOLD, fontSize);

        this.infoTabs = new JTabbedPane();
        infoTabs.setUI(new BasicTabbedPaneUI(){
            @Override
            protected void installDefaults() {
                super.installDefaults();
                Color alpha = new Color(0,0,0,0);
                highlight = Color.BLACK;
                lightHighlight = alpha;
                shadow = alpha;
                darkShadow = alpha;
                focus = alpha;
            }
        });
        infoTabs.setBackground(Color.BLACK);
        infoTabs.setForeground(Color.WHITE);
        infoOutput = new JTextArea(22, 25);
        DefaultCaret caret = (DefaultCaret)infoOutput.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        infoOutput.setFont(f);
        infoOutput.setEditable(false); infoOutput.setLineWrap(true);
        infoOutput.setBackground(Color.DARK_GRAY);
        infoOutput.setForeground(Color.WHITE);
        infoOutput.setBorder(null);

        JScrollPane[] scrollPane = new JScrollPane[] {new JScrollPane(infoOutput), new JScrollPane(setupHelpPanel()),
                new JScrollPane(playerHandPanel), new JScrollPane(notesPanel)};

        for (JScrollPane pane : scrollPane) {
            pane.setBorder(null);
        }

        infoTabs.addTab("Game Log", null, scrollPane[0], "Game Log - Forget what's happened so far?");
        infoTabs.addTab("Help Panel", null, scrollPane[1], "Help Panel - List of all commands");
        infoTabs.addTab("Current Cards", null, scrollPane[2], "The Current Cards you're holding");
        infoTabs.addTab("Notes", null, scrollPane[3], "Check List for who has what cards");

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false);
        infoPanel.add(infoTabs, BorderLayout.CENTER);
        infoPanel.add(setupCommandPanel(), BorderLayout.SOUTH);
        return infoPanel;


    }

    private PlayerHandLayout setupCardPanel() {
        return new PlayerHandLayout(gameCards, gamePlayers, resolution);
    }

    private PlayerLayout setupPlayerPanel() {
        PlayerLayout playerPanel = new PlayerLayout(gamePlayers, resolution, 0);
        playerPanel.setOpaque(false);
        TitledBorder border = new TitledBorder(new LineBorder(Color.BLACK,3), "PLAYERS");
        border.setTitleFont(new Font("Orange Kid",Font.BOLD, (int)(20*resolution.getScalePercentage())));
        border.setTitleColor(Color.WHITE);
        playerPanel.setBorder(border);

        return playerPanel;
    }

    //  Getters //

    public JTextArea getInfoOutput() {
        return infoOutput;
    }

    public JTextField getCommandInput() {
        return commandInput;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public Suspects getGameSuspects() {
        return gameSuspects;
    }

    public Weapons getGameWeapons() {
        return gameWeapons;
    }

    public Players getGamePlayers() {
        return gamePlayers;
    }

    public Cards getGameCards() {
        return gameCards;
    }

    public MoveOverlay getMoveOverlay() {
        return this.moveOverlay;
    }

    public DoorOverlay getDoorOverlay() {
        return this.doorOverlay;
    }

    public void setTab(int i) {
        infoTabs.setSelectedIndex(i);
    }

    public BoardUI getBoardPanel() {
        return this.boardPanel;
    }

    public Resolution getResolution(){
        return this.resolution;
    }

    public PlayerLayout getPlayerPanel() {
        return playerPanel;
    }

    public JTabbedPane getInfoTabs() {
        return infoTabs;
    }

    public class BoardUI extends JLayeredPane {

        public BoardUI() {
            //setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.weightx = 1; gbc.weighty = 1;
            gbc.gridx = 0; gbc.gridy = 0;
            gbc.fill = GridBagConstraints.BOTH;

            add(gameCards.getMurderEnvelope());
            add(gameSuspects);
            add(gameWeapons);
            add(doorOverlay);
            add(moveOverlay);
            add(gameBoard);

            ImageIcon board = new ImageIcon(gameAssets.getBoardImage());
            Dimension imageSize = new Dimension((int)(board.getIconWidth()*resolution.getScalePercentage()), (int)(board.getIconHeight()*resolution.getScalePercentage()));
            this.setPreferredSize(imageSize);

            this.getComponent(0).setLocation(0,0);
            for (int i = 1 ; i < getComponentCount() ; i++) {
                this.getComponent(i).setSize(imageSize);
                this.getComponent(i).setLocation(0,0);
            }
        }

        public boolean checkPoint(int x, int y){
            OverlayTile clickedPoint = new OverlayTile(x,y);
            HashSet<OverlayTile> validMoves = moveOverlay.getValidMoves();

            if (!validMoves.isEmpty()){
                for (OverlayTile overlayTile : validMoves){
                    if (overlayTile.getLocation().equals(clickedPoint.getLocation())){
                        return true;
                    }
                }
            }
            return false;
        }


    }
}
