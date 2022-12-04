package UI;

import Engine.Model.PlayerTilesSelection;
import Engine.Model.TerrainType;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.util.List;

public class JBoard extends JPanel {

    private JTile[][] tiles;
    private JPanel tilePanel;
    private JPanel controlPanel;
    private JPanel buttonPanel;
    private JPanel selectorPanel;
    private JButton clearButton;
    private JButton okButton;
    private ImageBank tileImages;

    private TerrainType userTerrainType;

    private IBoardInfo boardInfo;

    protected EventListenerList listenerList = new EventListenerList();

    public JBoard(ImageBank tileImages) {

        this.tileImages = tileImages;

        setMinimumSize(new Dimension(400, 400));
        setMaximumSize(new Dimension(600,600));

        this.tilePanel = new JPanel();
        this.tilePanel.setPreferredSize(new Dimension(300,300));
        this.setLayout(new BorderLayout());
        add(tilePanel, BorderLayout.CENTER);

        this.controlPanel = new JPanel();
        this.controlPanel.setPreferredSize(new Dimension(120, 200));
        this.controlPanel.setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.WEST);

        this.selectorPanel = new JPanel();
        this.selectorPanel.setLayout(new BoxLayout(this.selectorPanel, BoxLayout.Y_AXIS));
        this.controlPanel.add(this.selectorPanel, BorderLayout.CENTER);

        this.buttonPanel = new JPanel();
        this.buttonPanel.setLayout(new BoxLayout(this.buttonPanel, BoxLayout.Y_AXIS));

        this.okButton = new JButton("Ok");
        this.okButton.setMaximumSize(new Dimension(90,35));
        this.okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.clearButton = new JButton("Clear");
        this.clearButton.setMaximumSize(new Dimension(90,35));
        this.clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.clearButton.addActionListener(e -> {
            this.clearUserSelection();
        });

        this.okButton.addActionListener(e -> {
            this.completeUserSelection();
        });

        this.buttonPanel.add(clearButton);
        this.buttonPanel.add(Box.createVerticalStrut(10));
        this.buttonPanel.add(okButton);
        this.buttonPanel.add(Box.createVerticalStrut(10));

        this.clearButton.setVisible(false);
        this.okButton.setVisible(false);

        this.controlPanel.add(this.buttonPanel, BorderLayout.SOUTH);
    }

    public void addUserOkEventListener(UserOkEventListener listener) {
        listenerList.add(UserOkEventListener.class, listener);
    }
    public void removeUserOkEventListener(UserOkEventListener listener) {
        listenerList.remove(UserOkEventListener.class, listener);
    }

    private void fireUserOkEvent(UserOkEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == UserOkEventListener.class) {
                ((UserOkEventListener) listeners[i+1]).userOkEventOccurred(evt);
            }
        }
    }

    public void setPossibleTerrainTypes(List<TerrainType> terrainTypes) {
        setUserTerrainType(terrainTypes.get(0));
        this.selectorPanel.removeAll();
        for(int i=0;i<terrainTypes.size();i++) {
            var terrainType = terrainTypes.get(i);
            var button = new JButton(terrainType.name());
            var image = this.tileImages.getByName(terrainType.name().toLowerCase());
            var scaledImage = image.getScaledInstance(20,20, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(100, 40));
            button.addActionListener(e -> {
                this.setUserTerrainType(terrainType);
            });
            this.selectorPanel.add(Box.createVerticalStrut(5));
            this.selectorPanel.add(button);
        }
    }

    private void setUserTerrainType(TerrainType terrainType) {
        this.userTerrainType = terrainType;
        for (int row=0;row<tiles.length;row++) {
            for (int col=0;col<tiles.length;col++) {
                tiles[col][row].setUserTerrainType(terrainType);
            }
        }
    }

    private void clearUserSelection() {
        for (int row=0;row<tiles.length;row++) {
            for (int col=0;col<tiles.length;col++) {
                tiles[col][row].clearSelection();
            }
        }
    }

    private void completeUserSelection() {
        PlayerTilesSelection selection = new PlayerTilesSelection();
        for (int row=0;row<tiles.length;row++) {
            for (int col=0;col<tiles.length;col++) {
                if (tiles[col][row].isSelected()) {
                    selection.addTile(col, row, this.userTerrainType);
                }
            }
        }
        fireUserOkEvent(new UserOkEvent(this, selection));
    }

    public void refreshBoard(boolean isMonsterMode) {
        this.selectorPanel.revalidate();
        this.selectorPanel.repaint();
        for (int row=0;row<boardInfo.getSize();row++) {
            for (int col=0;col<boardInfo.getSize();col++) {
                var tileInfo = boardInfo.getTileInfo(col, row);
                tiles[col][row].setOriginalData(tileInfo.getTerrainType(), tileInfo.hasRuins(), isMonsterMode);
            }
        }
        this.clearButton.setVisible(!isMonsterMode);
    }

    public void closeBoard() {
        this.tilePanel.removeAll();
        this.selectorPanel.removeAll();
        this.tilePanel.setBackground(Color.LIGHT_GRAY);
        this.clearButton.setVisible(false);
        this.okButton.setVisible(false);
        this.tilePanel.revalidate();
        this.tilePanel.repaint();
        this.selectorPanel.revalidate();
        this.selectorPanel.repaint();
    }

    public void setMonsterSelection(PlayerTilesSelection monsterSelection) {
        var selectedTiles = monsterSelection.getSelectedTiles();
        for (int i=0; i<selectedTiles.size(); i++) {
            var x = selectedTiles.get(i).getX();
            var y = selectedTiles.get(i).getY();
            tiles[x][y].setMonster();
        }
    }

    public void setBoardInfo(IBoardInfo boardInfo) {
        this.boardInfo = boardInfo;
        int boardSize = boardInfo.getSize();
        tilePanel.removeAll();
        tilePanel.setLayout(new GridLayout(boardSize, boardSize, 2, 2));
        tilePanel.setBackground(Color.BLACK);
        tiles = new JTile[boardSize][boardSize];
        for (int row=0;row<boardInfo.getSize();row++) {
            for (int col=0;col<boardInfo.getSize();col++) {
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
