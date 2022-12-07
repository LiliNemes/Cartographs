package UI;

import Engine.Model.TerrainType;

import javax.swing.*;
import java.awt.*;

public class JTile extends JButton {

    private final ImageBank tileImages;
    private TerrainType originalTerrainType;
    private boolean hasRuin;

    private boolean isMonsterMode;
    private TerrainType userTerrainType;
    private boolean isSelected;

    public JTile(ImageBank tileImages) {
        super();
        this.setDoubleBuffered(true);
        this.tileImages = tileImages;
        this.originalTerrainType = TerrainType.Empty;
        this.userTerrainType = TerrainType.Empty;
        this.isSelected = false;
        this.setPreferredSize(new Dimension(30, 30));
        this.addActionListener(a -> this.toggleTile());
    }

    private void toggleTile() {
        //cannot click on non-empty field
        if (this.originalTerrainType != TerrainType.Empty || isMonsterMode) return;
        this.isSelected = !this.isSelected;
        this.revalidate();
        this.repaint();
    }

    public void setOriginalData(TerrainType terrainType, boolean hasRuin, boolean isMonsterMode) {

        this.isMonsterMode = isMonsterMode;
        this.isSelected = false;

        if (this.originalTerrainType == terrainType && this.hasRuin == hasRuin) return;
        this.originalTerrainType = terrainType;
        this.hasRuin = hasRuin;
        this.revalidate();
        this.repaint();
    }

    public void setUserTerrainType(TerrainType terrainType) {
        this.userTerrainType = terrainType;
        if (isSelected) {
            this.revalidate();
            this.repaint();
        }
    }

    public void clearSelection() {
        if (isSelected) {
            isSelected = false;
            this.revalidate();
            this.repaint();
        }
    }

    public void setMonster() {
        this.isSelected = true;
        this.userTerrainType = TerrainType.Monster;
        this.revalidate();
        this.repaint();
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gCopy = (Graphics2D) g.create();
        if (isSelected) {
            drawTerrainType(gCopy, this.userTerrainType, this.hasRuin);
            gCopy.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            gCopy.setColor(Color.LIGHT_GRAY);
            gCopy.fillRect(0, 0, this.getWidth(), this.getHeight());
        } else {
            drawTerrainType(gCopy, this.originalTerrainType, this.hasRuin);
        }
        gCopy.dispose();
    }

    private void drawTerrainType(Graphics2D g, TerrainType terrainType, boolean hasRuin) {

        if (terrainType == TerrainType.Rift) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        } else {
            String imageName = terrainType.name().toLowerCase();
            if (hasRuin) {
                imageName += "ruin";
            }
            g.drawImage(tileImages.getByName(imageName), 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }
}
