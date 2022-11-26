package UI;

import Engine.Model.TerrainType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class JTile extends JPanel implements MouseListener {

    private ImageBank tileImages;
    private TerrainType originalTerrainType;
    private boolean hasRuin;
    private TerrainType userTerrainType;
    private boolean isSelected;

    public JTile(ImageBank tileImages) {
        super(true);
        this.tileImages = tileImages;
        this.originalTerrainType = TerrainType.Empty;
        this.userTerrainType = TerrainType.Empty;
        this.isSelected = false;
        this.setPreferredSize(new Dimension(30, 30));
        this.addMouseListener(this);
    }

    public void setOriginalData(TerrainType terrainType, boolean hasRuin) {
        if (this.originalTerrainType == terrainType && this.hasRuin == hasRuin)
            return;
        this.originalTerrainType = terrainType;
        this.hasRuin = hasRuin;
        this.isSelected = false;
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

    public boolean isSelected() {
        return this.isSelected;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gCopy = (Graphics2D)g.create();
        if (isSelected) {
            drawTerrainType(gCopy, this.userTerrainType);
            gCopy.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            gCopy.setColor(Color.LIGHT_GRAY);
            gCopy.fillRect(0,0, this.getWidth(), this.getHeight());
        }
        else
        {
            drawTerrainType(gCopy, this.originalTerrainType);
        }
        gCopy.dispose();
    }

    private void drawTerrainType(Graphics2D g, TerrainType terrainType) {

        if (terrainType == TerrainType.Empty) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0,0, this.getWidth(), this.getHeight());
        }
        else if (terrainType == TerrainType.Rift) {
            g.setColor(Color.BLACK);
            g.fillRect(0,0, this.getWidth(), this.getHeight());
        }
        else {
            String imageName = terrainType.name().toLowerCase();
            g.drawImage(tileImages.getByName(imageName), 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //cannot click on non-empty field
        if (this.originalTerrainType != TerrainType.Empty)
            return;
        this.isSelected = !this.isSelected;
        this.revalidate();
        this.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
