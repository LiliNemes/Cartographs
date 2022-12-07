package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class JScoreBlock extends JPanel {

    private static final int margin = 5;
    private static final int fontSize = 25;
    private static final int spacing = 5;
    private int scoreA;
    private int scoreB;
    private int gold;
    private int monster;
    private int total;

    public JScoreBlock() {
        super(true);
        this.setPreferredSize(new Dimension(160, 100));
        this.setBackground(new Color(255,238,203));
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D gCopy = (Graphics2D) g.create();
        gCopy.setColor(new Color(54,37,27));
        gCopy.fillRoundRect(margin, margin, this.getWidth() - margin * 2, this.getHeight() - margin * 2, 10, 10);
        gCopy.setColor(Color.WHITE);
        drawText(gCopy, "A: " + scoreA, 0, 0);
        drawText(gCopy, "B: " + scoreB, 0.5, 0);
        drawText(gCopy, "G: " + gold, 0, 0.333);
        drawText(gCopy, "M: " + monster, 0.5, 0.333);
        drawText(gCopy, "Total: " + total, 0.1, 0.666);
        gCopy.dispose();
    }

    public void setScores(int scoreA, int scoreB, int money, int monster) {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.gold = money;
        this.monster = monster;
        this.total = scoreA + scoreB + money + monster;
        this.revalidate();
        this.repaint();
    }

    private void drawText(Graphics2D g, String text, double xPosPercent, double yPosPercent) {
        int xPosition = (int) ((this.getWidth() - margin * 2) * xPosPercent);
        int yPosition = (int) ((this.getHeight() - margin * 2) * yPosPercent);
        AttributedString str = new AttributedString(text);
        str.addAttribute(TextAttribute.SIZE, fontSize);
        str.addAttribute(TextAttribute.FONT, new Font("Whisky-1670", Font.PLAIN, 24));
        int colonPosition = text.indexOf(":");
        str.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR, 0, colonPosition);
        str.addAttribute(TextAttribute.SIZE, fontSize * 0.8, colonPosition, text.length());
        g.drawString(str.getIterator(), margin + xPosition + spacing, margin + yPosition + fontSize);
    }
}
