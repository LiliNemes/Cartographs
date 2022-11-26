package UI;

import javax.swing.*;
import java.awt.*;

public class JCard extends JPanel {
    private ImageBank cardImages;
    private String cardType;

    public JCard(ImageBank cardImages) {
        this (cardImages, 0.8);
    }

    public JCard(ImageBank cardImages, double scale) {
        super(true);
        this.setPreferredSize(new Dimension((int)(248*scale), (int)(349*scale)));
        this.cardImages = cardImages;
        this.cardType = "discovery_back";
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
        this.revalidate();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gCopy = (Graphics2D)g.create();
        String imageName = this.cardType.toLowerCase();
        g.drawImage(cardImages.getByName(imageName), 0, 0, this.getWidth(), this.getHeight(), null);
        gCopy.dispose();
    }
}
