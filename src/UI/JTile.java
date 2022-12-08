package UI;

import Engine.Model.TerrainType;

import javax.swing.*;
import java.awt.*;

/**
 * Tábla mezőinek UI osztálya, speciális típusú gombok. Fontos megjegyzés: Az originalTerrainType jelenti a mező valódi,
 * beállított, ellenőrzött típusát, és a UserTerrainType azt, amit a felhasználó kijelöl egy adott lépésben (vagy monster
 * esetén a gép) a mező típusának, de még nincs validálva, véglegesítve.
 */
public class JTile extends JButton {

    private final ImageBank tileImages;
    private TerrainType originalTerrainType;
    private boolean hasRuin;

    private boolean isMonsterMode;
    private TerrainType userTerrainType;
    private boolean isSelected;

    /**
     * Konstruktor. Beállítja az alapértelmezett (üres) kitöltést, hogy a mező épp nincs kiválasztva, a méretet
     * illetve az action listenert.
     * @param tileImages Képek amiket felvehet a mező (kitöltések).
     */
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

    /**
     * Ha már kitöltött a mező akkor nem történik semmi, ha még nem, akkor az isSelected értéke megváltozik: ha eddig
     * ki volt jelölve akkor nem lesz, ha nem volt akkor ki lesz.
     */
    private void toggleTile() {
        if (this.originalTerrainType != TerrainType.Empty || isMonsterMode) return;
        this.isSelected = !this.isSelected;
        this.revalidate();
        this.repaint();
    }

    /**
     * Beállítja a mező OriginalTerrainType-ját illetve, hogy van-e rajta rom.
     * @param terrainType A beállítandó kitöltés.
     * @param hasRuin Van-e rajta rom.
     * @param isMonsterMode Éppen szörnymező lerakásról va-e szó.
     */
    public void setOriginalData(TerrainType terrainType, boolean hasRuin, boolean isMonsterMode) {

        this.isMonsterMode = isMonsterMode;
        this.isSelected = false;

        if (this.originalTerrainType == terrainType && this.hasRuin == hasRuin) return;
        this.originalTerrainType = terrainType;
        this.hasRuin = hasRuin;
        this.revalidate();
        this.repaint();
    }

    /** Beállítja a UserTerrainType-ot: Ha a mező ki van jelölve akkor látszik a beállított kitöltéstípus.
     * @param terrainType A megadott kitöltéstípus.
     */
    public void setUserTerrainType(TerrainType terrainType) {
        this.userTerrainType = terrainType;
        if (isSelected) {
            this.revalidate();
            this.repaint();
        }
    }

    /**
     * Ha ki van választva a mező akkor nem kiválasztottá válik.
     */
    public void clearSelection() {
        if (isSelected) {
            isSelected = false;
            this.revalidate();
            this.repaint();
        }
    }

    /**
     * Beállítja a mező user kitöltését Monsterre.
     */
    public void setMonster() {
        this.isSelected = true;
        this.userTerrainType = TerrainType.Monster;
        this.revalidate();
        this.repaint();
    }

    /**
     *
     * @return ki van-e választva.
     */
    public boolean isSelected() {
        return this.isSelected;
    }

    /**
     * Ha a mező ki van választva éppen, akkor megrajzolja annak kitöltését renderelés segítségével -> így a kiválasztott
     * mező kitöltése áttetszőbb, szürkébb lesz a pályán már fixen elhelyezett mezőkéénél. Amennyiben a mező nincs
     * kiválasztva akkor megrajzolja simán a kitöltését renderelés nélkül.
     * @param g the <code>Graphics</code> object to protect
     */
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

    /**
     * Kirajzolja a mező kitöltését. Ha a kitöltés típusaRift csak simán fekete lesz, ha TerrainType akkor az adott
     * TerrainType-nak megfelelő kép, ha van rajta rom is akkor az a kép ami az adott TerrainTypehoz tartozik (az a neve)
     * de ruin van a kép nevének végén.
     * @param g Graphics 2d
     * @param terrainType A mező kitöltése.
     * @param hasRuin Van-e rajta rom.
     */
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
