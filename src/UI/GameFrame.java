package UI;

import Engine.Builder.GameFactory;
import Engine.Model.Game;
import Engine.Model.ScoreBoard;
import Engine.Model.Seasons;
import Engine.Model.ValidationResult;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Egész játék UI.
 */
public class GameFrame extends JFrame implements UserOkEventListener {

    //Különböző validáció során felmerülhető problémák hibaüzenetei.
    private static final Map<ValidationResult, String> validationMessages = Map.of(
            ValidationResult.TileNotEmpty, "Tile is not empty",
            ValidationResult.InvalidTerrain, "Invalid tile type",
            ValidationResult.NoSelection, "Must select some tiles",
            ValidationResult.InvalidArrangement, "Invalid shape of selected tiles",
            ValidationResult.MustPutOnRuin, "Shape must contain a ruin",
            ValidationResult.OnlySingleReplacement, "As you cannot place your shape properly, you can only place a single tile"
    );
    private final ImageBank tileImages;
    private final ImageBank cardImages;
    private boolean random;
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
    private JMenuBar menuBar;
    private Color bgColor = new Color(255,238,203);

    /**
     * Konstruktor.
     * @param title A játék címe.
     * @param tileImages A mezők kitöltéseinek képei.
     * @param cardImages A krtyák képei.
     * @throws HeadlessException Thrown when code that is dependent on a keyboard, display, or mouse is called in an
     * environment that does not support a keyboard, display, or mouse.
     */
    public GameFrame(String title, ImageBank tileImages, ImageBank cardImages) throws HeadlessException {
        super(title);
        this.tileImages = tileImages;
        this.cardImages = cardImages;
        this.random = false;
        this.initScoreBoard();
        this.initFrame();
    }

    /**
     * Ha létezik ScoreBoard akkor betölti azt a fileból, ha még nem akkor létrehoz egyet.
     */
    private void initScoreBoard() {
        var filePath = Paths.get("").toAbsolutePath() + "/scores.bin";
        System.out.println(filePath);
        var f = new File(filePath);
        if (f.exists()) {
            this.scoreBoard = ScoreBoard.load(filePath);
        } else {
            this.scoreBoard = new ScoreBoard();
        }
    }

    /**
     * Elmenti a ScoreBoardot a filejába.
     */
    private void saveScoreBoard() {
        var filePath = Paths.get("").toAbsolutePath() + "/scores.bin";
        System.out.println(filePath);
        this.scoreBoard.save(filePath);
    }

    /**
     * A játékot inicializáló, előkészítő függvény.
     * @param isHardLevel Advanced szintű-e a játék (mikor vannak szakadékok) vagy sem.
     * @param random Random-e a pálya kialakítása vagy sem.
     */
    private void startGame(boolean isHardLevel, boolean random) {
        String name = JOptionPane.showInputDialog(this, "Enter your name");
        this.currentGame = GameFactory.Instance.createSolitaireGame(name, isHardLevel, random);
        this.board.setBoardInfo(this.currentGame);
        this.showScoreCards();
        this.drawCard();
        this.showDiscoveryCards();
        this.showCurrentSeason();
        this.createScores();
        int[] zero=new int[4];
        for(int i=0; i<4;i++){
            this.showScores(i, zero);
        }
        this.showGold();
    }

    /**
     * Egy JLabelbe kiírja a játékról a felhasználó számára hasznos információkat: aktuális évszak, mennyi idő telt el
     * belőle, mennyi ideig tart összesen, játékos neve.
     */
    private void showCurrentSeason() {
        String info = String.format("Season: %s, Actual time: %s, Season time: %s", this.currentGame.getCurrentSeason().name(), this.currentGame.getCurrentTime(), this.currentGame.getCurrentSeasonTime());
        String name;
        if (this.currentGame.getPlayerName() == null) {
            name = "null";
        } else {
            name = this.currentGame.getPlayerName();
        }
        this.infoLabel.setText(info + "  Player: " + name);
    }

    /**
     * Egy Jlabelbe kiírja mennyi aranyat gyűjtött a játékos a játék folyamán.
     */
    private void showGold() {
        int golds = this.currentGame.getGolds();
        this.goldLabel.setText(String.format("Gold: %s", golds));
    }

    /**
     * Létrehozza a játékban évszakonként elért pontok megjelenítésére szolgáló JScoreBlock-okat.
     */
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

    /**
     * Beállítja a JScoreBlockok értékeit a kapott paraméterek szerint.
     * @param id Számérték ami megmutatja, hogy melyik évszaknak megfelelő JScoreBlock értékét kell beállítani.
     * @param scores A különböző pontszerzési módokhoz tarrtozó szerzett pontok tömbje.
     */
    private void showScores(int id, int[] scores) {
        if (id == 0) {
            block1.setScores(scores[0], scores[1], scores[2], scores[3], "A", "B");
        } else if (id == 1) {
            block2.setScores(scores[0], scores[1], scores[2], scores[3], "B", "C");
        } else if (id == 2) {
            block3.setScores(scores[0], scores[1], scores[2], scores[3], "C", "D");
        } else if (id == 3) {
            block4.setScores(scores[0], scores[1], scores[2], scores[3], "D", "A");
        }
        this.scoreArea.revalidate();
        this.scoreArea.repaint();
    }

    /**
     * A scoreCardokat jeleníti meg a scoreCardPanelen. Minden évszakban az aktívak keretet kapnak.
     */
    private void showScoreCards() {
        this.scoreCardPanel.removeAll();
        double scale = 0.6;

        var scoreCards = this.currentGame.getDrawnScoreCards();
        for (int i = 0; i < scoreCards.size(); i++) {
            var drawnCard = new JCard(this.cardImages, scale);
            boolean active = false;
            switch (this.currentGame.getCurrentSeason()) {
                case spring -> active = i < 2;
                case summer -> active = i > 0 && i < 3;
                case autumn -> active = i > 1;
                case winter -> active = i == 0 || i == 3;
            }
            drawnCard.setCardType(scoreCards.get(i).getName(), scoreCards.get(i).getDescription(), active);
            this.scoreCardPanel.add(drawnCard);
        }

        this.scoreCardPanel.revalidate();
        this.scoreCardPanel.repaint();
    }

    /**
     * A discoveryPanelen megjeleníti a paklit amiből húz a játék, a pakli mellett megjeleníti az aktuális, kihúzott
     * kártyá(ka)t. Amennyiben több van belőlük egymásra fektetve, eltolva helyezi el őket.
     */
    private void showDiscoveryCards() {
        this.discoveryPanel.removeAll();

        int topY = 10;
        int topX = 20;
        var card = new JCard(this.cardImages);
        int cardWidth = card.getPreferredSize().width;
        int cardHeight = card.getPreferredSize().height;
        this.discoveryPanel.add(card);
        card.setBounds(new Rectangle(topX, topY, cardWidth, cardHeight));

        var discoveryCards = this.currentGame.getDrawnDiscoveryCards();
        for (int i = 0; i < discoveryCards.size(); i++) {
            var drawnCard = new JCard(this.cardImages);
            drawnCard.setCardType(discoveryCards.get(i), null, false);
            drawnCard.setBounds(new Rectangle(topX + cardWidth + 20 + (int) (i * cardWidth * 0.4), topY, cardWidth, cardHeight));
            this.discoveryPanel.add(drawnCard);
            drawnCard.getParent().setComponentZOrder(drawnCard, 0);
        }

        this.discoveryPanel.revalidate();
        this.discoveryPanel.repaint();
    }

    /**
     * Kártyahúzás. Amennyiben AmbushCard meghívja a megfelelő függvény, hogy állítsa be a lehelyezendő alakot. Beállítja
     * a lehelyezhető terep típusoknak megfelelő gombokat, a játékpályát új lehelyezésre késszé teszi.
     */
    private void drawCard() {
        var monsterSelection = this.currentGame.drawNextCard();
        this.board.setPossibleTerrainTypes(this.currentGame.getPossibleTerrainTypes());
        this.board.refreshBoard(monsterSelection != null);
        if (monsterSelection != null) {
            this.board.setMonsterSelection(monsterSelection);
        }
    }

    /**
     * A játék összerakása.
     */
    private void initFrame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        URL url = ClassLoader.getSystemResource("UI/Images/icon.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        this.setIconImage(img);
        setLayout(new BorderLayout());
        this.getContentPane().setBackground(bgColor);


        GraphicsEnvironment ge = null;
        try{
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, GameFrame.class.getResourceAsStream("/UI/Images/whisky.ttf")));
        } catch(FontFormatException | IOException e){
            System.err.println("No font.");
        }

        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BorderLayout());
        this.board = new JBoard(tileImages);
        this.board.addUserOkEventListener(this);

        JPanel scoreBorderPanel = new JPanel();
        scoreBorderPanel.setPreferredSize(new Dimension(400, 160));
        scoreBorderPanel.setLayout(new BorderLayout());
        scoreBorderPanel.setBackground(bgColor);

        this.scoreArea = new JPanel();
        this.scoreArea.setLayout(new FlowLayout());
        this.scoreArea.setBackground(bgColor);
        scoreBorderPanel.add(this.scoreArea, BorderLayout.CENTER);

        this.goldLabel = new JLabel();
        this.goldLabel.setFont(new Font("Whisky-1670", Font.PLAIN, 20));
        this.goldLabel.setBorder(new EmptyBorder(4, 10, 4, 0));
        scoreBorderPanel.add(this.goldLabel, BorderLayout.NORTH);

        this.mainPanel.add(this.board, BorderLayout.CENTER);
        this.mainPanel.add(scoreBorderPanel, BorderLayout.SOUTH);

        this.rightPanel = new JPanel();
        this.rightPanel.setPreferredSize(new Dimension(700, 800));
        add(this.mainPanel, BorderLayout.CENTER);
        add(this.rightPanel, BorderLayout.EAST);

        this.rightPanel.setLayout(new BorderLayout());
        this.rightPanel.setBackground(bgColor);
        this.infoLabel = new JLabel();
        this.infoLabel.setFont(new Font("Whisky-1670", Font.PLAIN, 20));
        this.infoLabel.setBorder(new EmptyBorder(4, 10, 4, 0));
        this.rightPanel.add(this.infoLabel, BorderLayout.NORTH);

        this.discoveryPanel = new JPanel();
        this.discoveryPanel.setPreferredSize(new Dimension(400, 300));
        this.discoveryPanel.setLayout(null);
        this.discoveryPanel.setBackground(bgColor);
        this.rightPanel.add(this.discoveryPanel, BorderLayout.SOUTH);

        this.scoreCardPanel = new JPanel();
        this.scoreCardPanel.setLayout(new FlowLayout());
        this.scoreCardPanel.setBackground(bgColor);
        this.rightPanel.add(this.scoreCardPanel, BorderLayout.CENTER);

        this.menuBar = this.createMenuBar();
        this.menuBar.setBackground(bgColor);
        this.menuBar.setBorder(new LineBorder(bgColor));
        this.setJMenuBar(this.menuBar);
    }

    /**
     * A menü létrehozása.
     * @return A kész menü.
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        var menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);

        var startMenuItem = new JMenuItem("Start new game", KeyEvent.VK_N);
        startMenuItem.addActionListener(e -> {
            if (this.currentGame != null) {
                int result = JOptionPane.showConfirmDialog(this, "The current game will be lost. Are you sure?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.NO_OPTION) return;
            }
            this.startGame(false, this.random);
        });

        var startHardMenuItem = new JMenuItem("Start new hard game", KeyEvent.VK_H);
        startHardMenuItem.addActionListener(e -> {
            if (this.currentGame != null) {
                int result = JOptionPane.showConfirmDialog(this, "The current game will be lost. Are you sure?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.NO_OPTION) return;
            }
            this.startGame(true, this.random);
        });

        var randomMenuItem = new JMenuItem("Random", KeyEvent.VK_R);
        randomMenuItem.addActionListener(e ->{
            String msg = "";
            if(this.random) {
                this.random = false;
                msg = "off";
            }
            else {
                this.random = true;
                msg = "on";
            }
            JOptionPane.showMessageDialog(this, "You have successfully turned "+msg+" the randomness in the game!");
        });

        var loadMenuItem = new JMenuItem("Load game", KeyEvent.VK_L);
        loadMenuItem.addActionListener(e -> this.loadGame());

        var saveMenuItem = new JMenuItem("Save game", KeyEvent.VK_S);
        saveMenuItem.addActionListener(e -> this.saveGame());

        var scoreboardMenuItem = new JMenuItem("View scoreboard", KeyEvent.VK_B);
        scoreboardMenuItem.addActionListener(e -> showScoreBoard());

        var exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitMenuItem.addActionListener(e -> {
            if (this.currentGame != null) {
                int result = JOptionPane.showConfirmDialog(this, "The current game will be lost. Are you sure?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.NO_OPTION) return;
            }
            this.dispose();
        });

        menu.add(startMenuItem);
        menu.add(startHardMenuItem);
        menu.add(randomMenuItem);
        menu.add(loadMenuItem);
        menu.add(saveMenuItem);
        menu.add(scoreboardMenuItem);
        menu.add(exitMenuItem);

        return menuBar;
    }

    /**
     * A ranglista megjelenítése JDialog benne JTable formájában.
     */
    private void showScoreBoard() {
        var dialog = new JDialog(this);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        var closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        dialog.add(closeButton, BorderLayout.SOUTH);

        var scores = this.scoreBoard.getScoresArray();
        var table = new JTable(scores, new String[]{"Name", "Score"});
        table.setEnabled(false);
        JScrollPane sp = new JScrollPane(table);

        dialog.add(sp, BorderLayout.CENTER);

        dialog.setTitle("TOP 10 players");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(new Dimension(400, 300));
        dialog.setVisible(true);
    }

    /**
     * Játék betöltése mentettek közül JFileChooser segítségével.
     */
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
            for (Seasons s : Seasons.values()) {
                int id = -1;
                switch (s) {
                    case spring -> id = 0;
                    case summer -> id = 1;
                    case autumn -> id = 2;
                    case winter -> id = 3;
                }
                this.showScores(id, this.currentGame.getSeasonScores(s));
            }
            this.showGold();
        }
    }

    /**
     * Játék mentése JFileChooser segítségével.
     */
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
            GameFactory.Instance.saveGame(filePath, (Game) this.currentGame);
        }
    }

    /**
     * Először elkéri az eventtől a játékos által kiválasztott mezőket. Ezekre meghívja a validációt, ha az sikertelen,
     * akkor pop up windowba hibaüzenetet ír ki. Ha sikeres, akkor megnézi, hogy éppen vége van-e egy évszaknak. Ha nincs
     * akkor kártyát húz, megy tovább a játék. Ha vége van, akkor gondoskodik a pontozásról, új évszak, aktuális score
     * cardok beállításáról. Ha vége van a játéknak kiírja pop up windowként az összpontszámot, bezárja a játékpályát.
     * @param event paraméterként kapott event.
     */
    @Override
    public void userOkEventOccurred(UserOkEvent event) {
        var sel = event.getPlayerTilesSelection();
        var validationResult = this.currentGame.executePlayerSelection(sel);
        if (validationResult.getVr() != ValidationResult.Ok) {
            String message = validationMessages.getOrDefault(validationResult.getVr(), "Invalid selection of tiles! Please try again!");
            JOptionPane.showMessageDialog(this, message);
        }
        if (validationResult.isEndOfSeason()) {
            String endOfSeasonMessage = "End of season! Accumulated points can be seen below.";
            if (validationResult.getSeason() == Seasons.spring) {
                showScores(0, this.currentGame.getSeasonScores(validationResult.getSeason()));
                JOptionPane.showMessageDialog(this, endOfSeasonMessage);
                this.currentGame.setCurrentSeason(Seasons.summer);
                showScoreCards();
            }
            if (validationResult.getSeason() == Seasons.summer) {
                showScores(1, this.currentGame.getSeasonScores(validationResult.getSeason()));
                JOptionPane.showMessageDialog(this, endOfSeasonMessage);
                this.currentGame.setCurrentSeason(Seasons.autumn);
                showScoreCards();
            }
            if (validationResult.getSeason() == Seasons.autumn) {
                showScores(2, this.currentGame.getSeasonScores(validationResult.getSeason()));
                JOptionPane.showMessageDialog(this, endOfSeasonMessage);
                this.currentGame.setCurrentSeason(Seasons.winter);
                showScoreCards();
            }
            if (validationResult.getSeason() == Seasons.winter) {
                showScores(3, this.currentGame.getSeasonScores(validationResult.getSeason()));
                int finalScore = this.currentGame.getFinalScore();
                JOptionPane.showMessageDialog(this, "End of the game! Your final score is " + finalScore + ". You can start a new one in the main menu.");
                this.scoreBoard.addScore(this.currentGame.getPlayerName(), finalScore);
                this.saveScoreBoard();
                this.board.closeBoard();
                this.currentGame = null;
            }
        }
        if (validationResult.getVr() == ValidationResult.Ok && !(validationResult.isEndOfSeason() && validationResult.getSeason() == Seasons.winter)) {
            this.drawCard();
            this.showCurrentSeason();
            this.showDiscoveryCards();
            this.showGold();
        }

    }
}