package UI;

import Engine.Model.PlayerTilesSelection;
import Engine.Model.TerrainType;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.util.List;

/**
 * Játékpálya UI.
 */
public class JBoard extends JPanel {

    private final JPanel tilePanel;
    private final JPanel controlPanel;
    private final JPanel buttonPanel;
    private final JPanel selectorPanel;
    private final JButton clearButton;
    private final JButton okButton;
    private final ImageBank tileImages;
    protected EventListenerList listenerList = new EventListenerList();
    private JTile[][] tiles;
    private TerrainType userTerrainType;
    private IBoardInfo boardInfo;
    private final Color bgColor = new Color(255,238,203);

    /**
     * Konstruktor.
     * @param tileImages A tábla mezői álal felvehető kitöltések képei.
     */
    public JBoard(ImageBank tileImages) {

        this.tileImages = tileImages;

        //min, max méret
        setMinimumSize(new Dimension(400, 400));
        setMaximumSize(new Dimension(600, 600));

        //Játékpálya panelje
        this.tilePanel = new JPanel();
        this.tilePanel.setPreferredSize(new Dimension(300, 300));
        this.tilePanel.setBackground (bgColor);
        this.setLayout(new BorderLayout());
        add(tilePanel, BorderLayout.CENTER);

        this.controlPanel = new JPanel();
        this.controlPanel.setPreferredSize(new Dimension(120, 200));
        this.controlPanel.setLayout(new BorderLayout());
        this.controlPanel.setBackground(bgColor);
        add(controlPanel, BorderLayout.WEST);

        //Bal oldali panel ahol a választható tereptípusok gombjai vannak.
        this.selectorPanel = new JPanel();
        this.selectorPanel.setLayout(new BoxLayout(this.selectorPanel, BoxLayout.Y_AXIS));
        this.selectorPanel.setBackground(bgColor);
        this.controlPanel.add(this.selectorPanel, BorderLayout.CENTER);

        //Bal alsó panel ahol az okés clear gombok vannak.
        this.buttonPanel = new JPanel();
        this.buttonPanel.setLayout(new BoxLayout(this.buttonPanel, BoxLayout.Y_AXIS));
        this.buttonPanel.setBackground(bgColor);

        //OK gomb, Clear gomb
        this.okButton = new JButton("Ok");
        this.okButton.setMaximumSize(new Dimension(90, 35));
        this.okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.okButton.setBackground(new Color(54,37,27));
        this.okButton.setForeground(Color.white);
        this.okButton.setFont(new Font("Whisky-1670", Font.PLAIN, 16));
        this.okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.okButton.setFocusPainted(false);
        this.clearButton = new JButton("Clear");
        this.clearButton.setMaximumSize(new Dimension(90, 35));
        this.clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.clearButton.setBackground(new Color(54,37,27));
        this.clearButton.setForeground(Color.white);
        this.clearButton.setFont(new Font("Whisky-1670", Font.PLAIN, 16));
        this.clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.clearButton.setFocusPainted(false);

        this.clearButton.addActionListener(e -> this.clearUserSelection());

        this.okButton.addActionListener(e -> this.completeUserSelection());

        //Gombok a ButtonPanelra
        this.buttonPanel.add(clearButton);
        this.buttonPanel.add(Box.createVerticalStrut(10));
        this.buttonPanel.add(okButton);
        this.buttonPanel.add(Box.createVerticalStrut(10));

        this.clearButton.setVisible(false);
        this.okButton.setVisible(false);

        this.controlPanel.add(this.buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Az event listenerek listájához adja a paraméterként kapott EventListenert.
     * @param listener A listener amit a listához ad.
     */
    public void addUserOkEventListener(UserOkEventListener listener) {
        listenerList.add(UserOkEventListener.class, listener);
    }

    /**
     * Akkor hívódik meg ez a függvény, mikor egy UserOkEvent bekövetkezik. Meghívja az EventListeneren a userOkEventOccured
     * függvényt ami gondoskodik az ennek hatására végrehajtandó feladatokról.
     * @param evt A paraméterként kapott UserOkEvent.
     */
    private void fireUserOkEvent(UserOkEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == UserOkEventListener.class) {
                ((UserOkEventListener) listeners[i + 1]).userOkEventOccurred(evt);
            }
        }
    }

    /**
     * A paraméterként kapott lista első elemét beállítja az alapértelmezettnek. A lista minden elemére készít egy
     * gombot a selector panel területére, melyet megnyomva a játékos által kiválasztott mezők ilyen típusú kitöltéssel
     * rendelkeznek majd az eddigi, alapértelmezett helyett (a már kijelölt mezőkre és a még kijelölendőekre is).
     * @param terrainTypes Lista (az adott felfedező kártya felhúzásának hatására) beállítható tereptípusokról.
     */
    public void setPossibleTerrainTypes(List<TerrainType> terrainTypes) {
        setUserTerrainType(terrainTypes.get(0));
        this.selectorPanel.removeAll();
        for (TerrainType terrainType : terrainTypes) {
            var button = new JButton(terrainType.name());
            var image = this.tileImages.getByName(terrainType.name().toLowerCase());
            var scaledImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
            button.setBackground(new Color(54,37,27));
            button.setForeground(Color.white);
            button.setFont(new Font("Whisky-1670", Font.PLAIN, 14));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setFocusPainted(false);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(110, 40));
            button.addActionListener(e -> this.setUserTerrainType(terrainType));
            this.selectorPanel.add(Box.createVerticalStrut(5));
            this.selectorPanel.add(button);
        }
    }

    /**
     * Beállítja azt az értéket, hogy milyen típusú kitöltést fog adni egy mezőnek ha kijelöléskor rákattint a játékos.
     * Ezt beállítja minden egyes mezőnél (JTile) is.
     * @param terrainType A beállítandó kitöltési érték.
     */
    private void setUserTerrainType(TerrainType terrainType) {
        this.userTerrainType = terrainType;
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                tiles[col][row].setUserTerrainType(terrainType);
            }
        }
    }

    /**
     * Nullázza a felhasználó által kijelölt mezőket, törli róluk a kijelölést.
     */
    private void clearUserSelection() {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                tiles[col][row].clearSelection();
            }
        }
    }

    /**
     * A felhasználó által kijelölt mezőkből player selectiont készít, majd meghívja a fireUserOkEvent-et, mely a
     * kijelölés elleőrzését, véglegesítését folytatja.
     */
    private void completeUserSelection() {
        PlayerTilesSelection selection = new PlayerTilesSelection();
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                if (tiles[col][row].isSelected()) {
                    selection.addTile(col, row, this.userTerrainType);
                }
            }
        }
        fireUserOkEvent(new UserOkEvent(this, selection));
    }

    /**
     * Törli a selector panelen lévő TerrainType gombokat. Minden mezőre beállítja annak OriginalDatáját, törli a
     * kijelöléseket. Attól függően változtatja a clear button láthatóságát, hogy a paraméterként kapott boolean true
     * vagy false.
     * @param isMonsterMode Ha false akkor nem szörnykártya került játékba, szükség van rá, ha true akkor szörnykártya
     *                      húzódott, nem lehet használni.
     */
    public void refreshBoard(boolean isMonsterMode) {
        this.selectorPanel.revalidate();
        this.selectorPanel.repaint();
        for (int row = 0; row < boardInfo.getSize(); row++) {
            for (int col = 0; col < boardInfo.getSize(); col++) {
                var tileInfo = boardInfo.getTileInfo(col, row);
                tiles[col][row].setOriginalData(tileInfo.getTerrainType(), tileInfo.hasRuins(), isMonsterMode);
            }
        }
        this.clearButton.setVisible(!isMonsterMode);
    }

    /**
     * Letörli a táblát, leszedi róla a különböző elemeket.
     */
    public void closeBoard() {
        this.tilePanel.removeAll();
        this.selectorPanel.removeAll();
        this.tilePanel.setBackground(bgColor);
        this.clearButton.setVisible(false);
        this.okButton.setVisible(false);
        this.tilePanel.revalidate();
        this.tilePanel.repaint();
        this.selectorPanel.revalidate();
        this.selectorPanel.repaint();
    }

    /**
     * Minden, a paraméterként kapott playerTilesSelection mezőjének beállítja a kitöltésének a monster TerrainType-ot.
     * @param monsterSelection
     */
    public void setMonsterSelection(PlayerTilesSelection monsterSelection) {
        var selectedTiles = monsterSelection.getSelectedTiles();
        for (Engine.Model.SelectedTile selectedTile : selectedTiles) {
            var x = selectedTile.getX();
            var y = selectedTile.getY();
            tiles[x][y].setMonster();
        }
    }

    /**
     * Ez a függvény hozza létre magát a játékpályát JTile-okból. Láthatóvá teszi az ok illetve clear gombokat.
     * @param boardInfo Információk a tábláról.
     */
    public void setBoardInfo(IBoardInfo boardInfo) {
        this.boardInfo = boardInfo;
        int boardSize = boardInfo.getSize();
        tilePanel.removeAll();
        tilePanel.setLayout(new GridLayout(boardSize, boardSize, 2, 2));
        tilePanel.setBackground(Color.BLACK);
        tiles = new JTile[boardSize][boardSize];
        for (int row = 0; row < boardInfo.getSize(); row++) {
            for (int col = 0; col < boardInfo.getSize(); col++) {
                JTile tile = new JTile(this.tileImages);
                var tileInfo = boardInfo.getTileInfo(col, row);
                tile.setOriginalData(tileInfo.getTerrainType(), tileInfo.hasRuins(), false);
                tiles[col][row] = tile;
                tilePanel.add(tile);
            }
        }
        tilePanel.revalidate();
        tilePanel.repaint();

        this.clearButton.setVisible(true);
        this.okButton.setVisible(true);
    }

}