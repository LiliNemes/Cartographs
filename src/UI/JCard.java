package UI;

import javax.swing.*;
import java.awt.*;

/**
 * Kártyák UI osztálya. Attribútumai a kártya típusa (String) és a kártyák képei (ImageBank).
 */
public class JCard extends JPanel {
    private final ImageBank cardImages;
    private String cardType;

    /**
     * Konstruktor.
     * @param cardImages A kártyák képei.
     */
    public JCard(ImageBank cardImages) {
        this(cardImages, 0.8);
    }

    /**
     * Konstruktor.
     * @param cardImages A kártyák képei.
     * @param scale Méret.
     */
    public JCard(ImageBank cardImages, double scale) {
        super(true);
        this.setPreferredSize(new Dimension((int) (248 * scale), (int) (349 * scale)));
        this.cardImages = cardImages;
        this.cardType = "discovery_back";
    }

    /**
     * A kártya tulajdonságait állítja be a függvény. Beállítja a típusát, a tooltip textjét, illetve a keretét.
     * Ha a tooltip paraméter nulla akkor nem ír semmit tooltip textként, ha viszont nem az, akkor html segítségével írja ki.
     * @param cardType A kártya típusa.
     * @param tooltip A kártyára húzva a kurzort milyen szöveget írjon ki.
     * @param active Aktív-e az adott évszakban a ScoreCard (más kártyatípusnál mindig false).
     */
    public void setCardType(String cardType, String tooltip, boolean active) {
        this.cardType = cardType;
        String tooltipHtml = tooltip != null ? "<html>" + tooltip.replaceAll("(\r\n|\n)", "<br />") + "</html>" : null;
        this.setToolTipText(tooltipHtml);
        if (active) this.setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, Color.BLACK));
        else this.setBorder(BorderFactory.createEmptyBorder());
        this.revalidate();
        this.repaint();
    }

    /**
     * A kártyatípusból megtalálja a nevét és így ImageBankben a képét is a kártyának amit ki kell rajzoljon és ezt meg is teszi.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gCopy = (Graphics2D) g.create();
        String imageName = this.cardType.toLowerCase();
        g.drawImage(cardImages.getByName(imageName), 0, 0, this.getWidth(), this.getHeight(), null);
        gCopy.dispose();
    }
}
