package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class JScoreBlock extends JPanel {

    public JScoreBlock() {
        super(true);
        this.setPreferredSize(new Dimension(160,100));
    }

    private int scoreA;
    private int scoreB;
    private int gold;
    private int monster;
    private int total;

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D gCopy = (Graphics2D)g.create();
        gCopy.drawRoundRect(margin,margin, this.getWidth()-margin*2, this.getHeight()-margin*2,10,10);
        drawText(gCopy, "A: " + scoreA , 0,0);
        drawText(gCopy, "B: " + scoreB, 0.5, 0);
        drawText(gCopy, "G: " +gold, 0, 0.333);
        drawText(gCopy, "M: " + -1*monster, 0.5, 0.333);
        drawText(gCopy, "Total: " + total, 0.1, 0.666);
        gCopy.dispose();
    }

    public void setScores(int scoreA, int scoreB, int money, int monster) {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.gold = money;
        this.monster=monster;
        this.total=scoreA+scoreB+money+monster;
        this.revalidate();
        this.repaint();
    }

    private static int margin = 5;
    private static int fontSize = 25;
    private static int spacing = 5;

    private void drawText(Graphics2D g, String text, double xPosPercent, double yPosPercent) {
        int xPosition = (int)((this.getWidth() - margin*2) * xPosPercent);
        int yPosition = (int)((this.getHeight() - margin*2) * yPosPercent);
        AttributedString str = new AttributedString(text);
        str.addAttribute(TextAttribute.SIZE, fontSize);
        int colonPosition = text.indexOf(":");
        str.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD, 0, colonPosition);
        str.addAttribute(TextAttribute.SIZE, fontSize *0.8, colonPosition, text.length());
        g.drawString(str.getIterator(),  margin+ xPosition + spacing,margin + yPosition + fontSize);
    }
}
