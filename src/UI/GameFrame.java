package UI;

import Engine.Builder.GameFactory;
import Engine.Model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Paths;
import java.util.Map;

public class GameFrame extends JFrame implements UserOkEventListener {

    private ImageBank tileImages;
    private ImageBank cardImages;
    private ImageBank otherImages;

    private JBoard board;
    private JPanel scoreArea;
    private JScoreBlock block1;
    private JScoreBlock block2;
    private JScoreBlock block3;
    private JScoreBlock block4;

    private JPanel mainPanel;
    private JPanel rightPanel;
    private JPanel discoveryPanel;

    private JPanel scoreCardPanel;
    private JLabel infoLabel;

    private JLabel goldLabel;

    private IGameEngine currentGame;

    private ScoreBoard scoreBoard;

    public GameFrame(String title, ImageBank tileImages, ImageBank cardImages, ImageBank otherImages) throws HeadlessException {
        super(title);
        this.tileImages = tileImages;
        this.cardImages = cardImages;
        this.otherImages = otherImages;
        this.initScoreBoard();
        this.initFrame();
    }

    private void initScoreBoard() {
        var filePath = Paths.get("").toAbsolutePath().toString() + "/scores.bin";
        System.out.println(filePath);
        var f = new File(filePath);
        if (f.exists()) {
            this.scoreBoard = ScoreBoard.load(filePath);
        }
        else {
            this.scoreBoard = new ScoreBoard();
        }
    }

    private void saveScoreBoard() {
        var filePath = Paths.get("").toAbsolutePath().toString() + "/scores.bin";
        System.out.println(filePath);
        this.scoreBoard.save(filePath);
    }

    private void startGame(boolean isHardLevel) {
        String name = JOptionPane.showInputDialog(this, "Enter your name");
        this.currentGame = GameFactory.Instance.createSolitaireGame(name, isHardLevel);
        this.board.setBoardInfo(this.currentGame);
        this.showScoreCards();
        this.drawCard();
        this.showDiscoveryCards();
        this.showCurrentSeason();
        this.createScores();
        this.showGold();
    }

    private void showCurrentSeason() {
        String info = String.format("Season: %s, Time:%s", this.currentGame.getCurrentSeason().name(), this.currentGame.getCurrentTime());
        String name;
        if(this.currentGame.getPlayerName()==null) {
            name= "null";
        }
        else {
            name=this.currentGame.getPlayerName();
        }
        this.infoLabel.setText(info + " Player: " + name);
    }

    private void showGold() {
        int golds = this.currentGame.getGolds();
        this.goldLabel.setText(String.format("Gold:%s", golds));
    }

    private void createScores() {
        this.scoreArea.removeAll();

        block1 = new JScoreBlock();
        this.scoreArea.add(block1);
        block2 = new JScoreBlock();
        this.scoreArea.add(block2);
        block3 = new JScoreBlock();
        this.scoreArea.add(block3);
        block4 = new JScoreBlock();
        this.scoreArea.add(block4);

        this.scoreArea.revalidate();
        this.scoreArea.repaint();
    }
    private void showScores(int id, int a, int b, int gold, int monster) {
        if(id==0) {
            block1.setScores(a, b,  gold, monster);
        }
        if(id==1) {
            block2.setScores(a, b, gold, monster);
        }
        if(id==2) {
            block3.setScores(a, b, gold, monster);
        }
        if(id==3) {
            block4.setScores(a, b, gold, monster);
        }

        this.scoreArea.revalidate();
        this.scoreArea.repaint();
    }

    private void showScoreCards() {
        this.scoreCardPanel.removeAll();
        double scale = 0.6;

        var scoreCards = this.currentGame.getDrawnScoreCards();
        for (int i=0; i<scoreCards.size(); i++) {
            var drawnCard = new JCard(this.cardImages, scale);
            drawnCard.setCardType(scoreCards.get(i).getName(), scoreCards.get(i).getDescription());
            this.scoreCardPanel.add(drawnCard);
        }

        this.scoreCardPanel.revalidate();
        this.scoreCardPanel.repaint();
    }

    private void showDiscoveryCards() {
        this.discoveryPanel.removeAll();

        int topY = 10;
        int topX = 20;
        var card = new JCard(this.cardImages);
        int cardWidth = card.getPreferredSize().width;
        int cardHeight = card.getPreferredSize().height;
        this.discoveryPanel.add(card);
        card.setBounds(new Rectangle(topX,topY, cardWidth, cardHeight));

        var discoveryCards = this.currentGame.getDrawnDiscoveryCards();
        for (int i=0; i<discoveryCards.size(); i++) {
            var drawnCard = new JCard(this.cardImages);
            drawnCard.setCardType(discoveryCards.get(i), null);
            drawnCard.setBounds(new Rectangle(topX  + cardWidth + 20 + (int)(i * cardWidth*0.4),topY, cardWidth, cardHeight));
            this.discoveryPanel.add(drawnCard);
            drawnCard.getParent().setComponentZOrder(drawnCard, 0);
        }

        this.discoveryPanel.revalidate();
        this.discoveryPanel.repaint();
    }

    private void drawCard() {
        var monsterSelection = this.currentGame.drawNextCard();
        this.board.setPossibleTerrainTypes(this.currentGame.getPossibleTerrainTypes());
        this.board.refreshBoard(monsterSelection != null);
        if (monsterSelection != null) {
            this.board.setMonsterSelection(monsterSelection);
        }
    }

    private void initFrame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BorderLayout());
        this.board = new JBoard(tileImages);
        this.board.addUserOkEventListener(this);

        JPanel scoreBorderPanel = new JPanel();
        scoreBorderPanel.setPreferredSize(new Dimension(400, 160));
        scoreBorderPanel.setLayout(new BorderLayout());

        this.scoreArea = new JPanel();
        this.scoreArea.setLayout(new FlowLayout());
        scoreBorderPanel.add(this.scoreArea, BorderLayout.CENTER);

        this.goldLabel = new JLabel();
        this.goldLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.goldLabel.setBorder(new EmptyBorder(4,10,4,0));
        scoreBorderPanel.add(this.goldLabel, BorderLayout.NORTH);

        this.mainPanel.add(this.board, BorderLayout.CENTER);
        this.mainPanel.add(scoreBorderPanel, BorderLayout.SOUTH);

        this.rightPanel = new JPanel();
        this.rightPanel.setPreferredSize(new Dimension(700, 800));
        add(this.mainPanel, BorderLayout.CENTER);
        add(this.rightPanel, BorderLayout.EAST);

        this.rightPanel.setLayout(new BorderLayout());
        this.infoLabel = new JLabel();
        this.infoLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.infoLabel.setBorder(new EmptyBorder(4,10,4,0));
        this.rightPanel.add(this.infoLabel, BorderLayout.NORTH);

        this.discoveryPanel = new JPanel();
        this.discoveryPanel.setPreferredSize(new Dimension(400, 300));
        this.discoveryPanel.setLayout(null);
        this.rightPanel.add(this.discoveryPanel, BorderLayout.SOUTH);

        this.scoreCardPanel = new JPanel();
        this.scoreCardPanel.setLayout(new FlowLayout());
        this.rightPanel.add(this.scoreCardPanel, BorderLayout.CENTER);

        this.menuBar = this.createMenuBar();
        this.setJMenuBar(this.menuBar);
    }

    private JMenuBar menuBar;

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        var menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);

        var startMenuItem = new JMenuItem("Start", KeyEvent.VK_S);
        startMenuItem.addActionListener(e -> {
            if (this.currentGame != null) {
                int result = JOptionPane.showConfirmDialog(this, "The current game will be lost. Are you sure?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.NO_OPTION) return;
            }
            this.startGame(false);
        });

        var startHardMenuItem = new JMenuItem("Start Hard");
        startHardMenuItem.addActionListener(e -> {
            if (this.currentGame != null) {
                int result = JOptionPane.showConfirmDialog(this, "The current game will be lost. Are you sure?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.NO_OPTION) return;
            }
            this.startGame(true);
        });

        var loadMenuItem = new JMenuItem("Load game");
        loadMenuItem.addActionListener(e -> {
            this.loadGame();
        });

        var saveMenuItem = new JMenuItem("Save game");
        saveMenuItem.addActionListener(e -> {
            this.saveGame();
        });

        var scoreboardMenuItem = new JMenuItem("View scoreboard");
        scoreboardMenuItem.addActionListener(e -> {
            showScoreBoard();
        });

        var exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> {
            if (this.currentGame != null) {
                int result = JOptionPane.showConfirmDialog(this, "The current game will be lost. Are you sure?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.NO_OPTION) return;
            }
            this.dispose();
        });

        menu.add(startMenuItem);
        menu.add(startHardMenuItem);
        menu.add(loadMenuItem);
        menu.add(saveMenuItem);
        menu.add(scoreboardMenuItem);
        menu.add(exitMenuItem);

        return menuBar;
    }

    private void showScoreBoard() {
        var dialog = new JDialog(this);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        var closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            dialog.dispose();
        });
        dialog.add(closeButton, BorderLayout.SOUTH);

        var scores = this.scoreBoard.getScoresArray();
        var table = new JTable(scores, new String[] {"Name", "Score"});
        table.setEnabled(false);
        JScrollPane sp = new JScrollPane(table);

        dialog.add(sp, BorderLayout.CENTER);

        dialog.setTitle("TOP 10 players");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(new Dimension(400, 300));
        dialog.setVisible(true);
    }

    private void loadGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Cartographers game file", "cartg"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.exists()) return;
            if (this.currentGame != null) {
                int confirmResult = JOptionPane.showConfirmDialog(this, "The current game will be lost. Are you sure?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirmResult == JOptionPane.NO_OPTION) return;
            }
            String filePath = selectedFile.getAbsolutePath();
            System.out.println(filePath);

            this.currentGame = GameFactory.Instance.loadGame(filePath);
            this.board.setBoardInfo(this.currentGame);
            this.showScoreCards();
            this.drawCard();
            this.showDiscoveryCards();
            this.showCurrentSeason();
            this.createScores();
            this.showGold();
        }
    }

    private void saveGame() {
        if (this.currentGame == null) return;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Cartographers game file", "cartg"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.endsWith(".cartg")) {
                filePath += ".cartg";
            }
            System.out.println(filePath);
            GameFactory.Instance.saveGame(filePath, (Game)this.currentGame);
        }
    }

    private static Map<ValidationResult, String> validationMessages = Map.of(
            ValidationResult.TileNotEmpty, "Tile is not empty",
            ValidationResult.InvalidTerrain, "Invalid tile type",
            ValidationResult.NoSelection, "Must select some tiles",
            ValidationResult.InvalidArrangement, "Invalid shape of selected tiles",
            ValidationResult.MustPutOnRuin, "Shape must contain a ruin",
            ValidationResult.OnlySingleReplacement, "As you cannot place your shape properly, you can only place a single tile"
            );

    @Override
    public void userOkEventOccurred(UserOkEvent event) {
        var sel = event.getPlayerTilesSelection();
        var validationResult = this.currentGame.executePlayerSelection(sel);
        if (validationResult.getVr() != ValidationResult.Ok) {
            String message = validationMessages.containsKey(validationResult.getVr()) ?
                    validationMessages.get(validationResult.getVr()) : "Invalid selection of tiles! Please try again!";
            JOptionPane.showMessageDialog(this, message);
        }
        if(validationResult.isEndOfSeason()) {
            String endOfSeasonMessage = "End of season! Accumulated points can be seen below.";
            if(validationResult.getSeason()== Seasons.spring) {
                showScores(0, validationResult.getA(), validationResult.getB(), validationResult.getMoney(), validationResult.getMonster());
                JOptionPane.showMessageDialog(this, endOfSeasonMessage);
                this.currentGame.setCurrentSeason(Seasons.summer);
            }
            if(validationResult.getSeason()== Seasons.summer) {
                showScores(1, validationResult.getA(), validationResult.getB(), validationResult.getMoney(), validationResult.getMonster());
                JOptionPane.showMessageDialog(this, endOfSeasonMessage);
                this.currentGame.setCurrentSeason(Seasons.autumn);
            }
            if(validationResult.getSeason()== Seasons.autumn) {
                showScores(2, validationResult.getA(), validationResult.getB(), validationResult.getMoney(), validationResult.getMonster());
                JOptionPane.showMessageDialog(this, endOfSeasonMessage);
                this.currentGame.setCurrentSeason(Seasons.winter);
            }
            if(validationResult.getSeason()== Seasons.winter) {
                showScores(3, validationResult.getA(), validationResult.getB(), validationResult.getMoney(), validationResult.getMonster());
                int finalScore=block1.getTotal()+block2.getTotal()+block3.getTotal()+block4.getTotal();
                JOptionPane.showMessageDialog(this, "End of the game! Your final score is " + finalScore + ". You can start a new one in the main menu.");
                this.scoreBoard.addScore(this.currentGame.getPlayerName(), finalScore);
                this.saveScoreBoard();
                this.board.closeBoard();
                this.currentGame = null;
            }
        }
        if(validationResult.getVr() == ValidationResult.Ok && !(validationResult.isEndOfSeason() && validationResult.getSeason()==Seasons.winter)) {
            this.drawCard();
            this.showCurrentSeason();
            this.showDiscoveryCards();
            this.showGold();
        }

    }
}
