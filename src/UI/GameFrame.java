package UI;

import Engine.Builder.GameFactory;
import Engine.Model.ExecutionSeasonResult;
import Engine.Model.Seasons;
import Engine.Model.ValidationResult;
import UI.Fakes.FakeGameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

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

    public GameFrame(String title, ImageBank tileImages, ImageBank cardImages, ImageBank otherImages) throws HeadlessException {
        super(title);
        this.tileImages = tileImages;
        this.cardImages = cardImages;
        this.otherImages = otherImages;
        this.initFrame();
        this.startGame(true);
    }

    private void startGame(boolean isFake) {
        this.currentGame = isFake ? new FakeGameEngine(11) : GameFactory.Instance.createSolitaireGame("Player");
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
        this.infoLabel.setText(info);
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
           block1.setScores(a,b,gold, monster);
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
        /*this.scoreCardPanel.add(new JCard(this.cardImages, scale));
        this.scoreCardPanel.add(new JCard(this.cardImages, scale));
        this.scoreCardPanel.add(new JCard(this.cardImages, scale));
        this.scoreCardPanel.add(new JCard(this.cardImages, scale));
        */

        var scoreCards = this.currentGame.getDrawnScoreCards();
        for (int i=0; i<scoreCards.size(); i++) {
            var drawnCard = new JCard(this.cardImages, scale);
            drawnCard.setCardType(scoreCards.get(i));
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
            drawnCard.setCardType(discoveryCards.get(i));
            drawnCard.setBounds(new Rectangle(topX  + cardWidth + 20 + (int)(i * cardWidth*0.4),topY, cardWidth, cardHeight));
            this.discoveryPanel.add(drawnCard);
            drawnCard.getParent().setComponentZOrder(drawnCard, 0);
        }

        this.discoveryPanel.revalidate();
        this.discoveryPanel.repaint();
    }

    private void drawCard() {
        this.currentGame.drawNextCard();
        this.board.setPossibleTerrainTypes(this.currentGame.getPossibleTerrainTypes());
        this.board.refreshBoard();
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
        this.scoreArea.setBackground(Color.GREEN);
        this.scoreArea.setLayout(new FlowLayout());
        scoreBorderPanel.add(this.scoreArea, BorderLayout.CENTER);

        this.goldLabel = new JLabel();
        scoreBorderPanel.add(this.goldLabel, BorderLayout.NORTH);

        this.mainPanel.add(this.board, BorderLayout.CENTER);
        this.mainPanel.add(scoreBorderPanel, BorderLayout.SOUTH);

        this.rightPanel = new JPanel();
        this.rightPanel.setBackground(Color.WHITE);
        this.rightPanel.setPreferredSize(new Dimension(700, 800));
        add(this.mainPanel, BorderLayout.CENTER);
        add(this.rightPanel, BorderLayout.EAST);

        this.rightPanel.setLayout(new BorderLayout());
        this.infoLabel = new JLabel();
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

        var startFakeMenuItem = new JMenuItem("Start Fake", KeyEvent.VK_T);
        startFakeMenuItem.addActionListener(e -> {
            this.startGame(true);
        });
        var startMenuItem = new JMenuItem("Start", KeyEvent.VK_S);
        startMenuItem.addActionListener(e -> {
            this.startGame(false);
        });

        menu.add(startMenuItem);
        menu.add(startFakeMenuItem);

        return menuBar;
    }

    @Override
    public void userOkEventOccurred(UserOkEvent event) {
        var sel = event.getPlayerTilesSelection();
        var validationResult = this.currentGame.executePlayerSelection(sel);
        if (validationResult.getVr() != ValidationResult.Ok) {

        }
        if(validationResult.isEndOfSeason()) {
            if(validationResult.getSeason()== Seasons.spring) {
                int a= this.currentGame.getSeasonalScoreCards(Seasons.spring).get(0).score(this.currentGame.getCurrentSheet());
                int b= this.currentGame.getSeasonalScoreCards(Seasons.spring).get(1).score(this.currentGame.getCurrentSheet());
                int money=this.currentGame.getCurrentSheet().getAccumulatedGold();
                showScores(0, a, b, money, 0);
                this.currentGame.setCurrentSeason(Seasons.summer);

            }
            if(validationResult.getSeason()== Seasons.summer) {
                int a= this.currentGame.getSeasonalScoreCards(Seasons.summer).get(0).score(this.currentGame.getCurrentSheet());
                int b= this.currentGame.getSeasonalScoreCards(Seasons.summer).get(1).score(this.currentGame.getCurrentSheet());
                int money=this.currentGame.getCurrentSheet().getAccumulatedGold();
                showScores(1, a, b, money, 0);
                this.currentGame.setCurrentSeason(Seasons.autumn);
            }
            if(validationResult.getSeason()== Seasons.autumn) {
                int a= this.currentGame.getSeasonalScoreCards(Seasons.autumn).get(0).score(this.currentGame.getCurrentSheet());
                int b= this.currentGame.getSeasonalScoreCards(Seasons.autumn).get(1).score(this.currentGame.getCurrentSheet());
                int money=this.currentGame.getCurrentSheet().getAccumulatedGold();
                showScores(2, a, b, money, 0);
                this.currentGame.setCurrentSeason(Seasons.winter);
            }
            if(validationResult.getSeason()== Seasons.winter) {
                int a= this.currentGame.getSeasonalScoreCards(Seasons.winter).get(0).score(this.currentGame.getCurrentSheet());
                int b= this.currentGame.getSeasonalScoreCards(Seasons.winter).get(1).score(this.currentGame.getCurrentSheet());
                int money=this.currentGame.getCurrentSheet().getAccumulatedGold();
                int id=3;
                showScores(3, a, b, money, 0);
            }
        }
        if(validationResult.getVr() == ValidationResult.Ok) {
            this.drawCard();
            this.showCurrentSeason();
            this.showDiscoveryCards();
            this.showGold();
        }

    }
}
