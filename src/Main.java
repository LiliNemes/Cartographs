import UI.GameFrame;
import UI.ImageBank;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        ImageBank tileImages = new ImageBank("Tiles", List.of(
                "forest",
                "forestruin",
                "village",
                "villageruin",
                "water",
                "waterruin",
                "farm",
                "farmruin",
                "mountain",
                "monster",
                "monsterruin",
                "empty",
                "emptyruin"));
        ImageBank cardImages = new ImageBank("cards", List.of(
                "borderlands",
                "canallake",
                "discovery_back",
                "farmland",
                "fishingvillage",
                "forgottenforest",
                "greatcity",
                "greatriver",
                "greenbough",
                "greengoldplains",
                "hamlet",
                "hinterlandstream",
                "homestead",
                "magesvalley",
                "marshlands",
                "orchard",
                "riftlands",
                "sentinelwood",
                "treetopvillage",
                "shieldgate",
                "thebrokenroad",
                "thecauldrons",
                "treetower",
                "wildholds",
                "templeruins",
                "outpostruins",
                "koboldonslaught",
                "goblinattack",
                "gnollraid",
                "bugbearassault"));

        GameFrame frame = new GameFrame("Cartographers", tileImages, cardImages);
        SwingUtilities.invokeLater(() -> {
            frame.setMinimumSize(new Dimension(1400, 800));
            frame.setPreferredSize(new Dimension(1400, 800));
            frame.pack();
            frame.setVisible(true);
        });
    }
}