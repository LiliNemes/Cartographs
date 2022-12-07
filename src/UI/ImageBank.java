package UI;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Olyan osztály, mely a játékban szereplő képeket egy mapben tárolja, String-BufferedImage párokban.
 */
public class ImageBank {

    Map<String, BufferedImage> images;

    /**
     * Konstruktor.
     * @param subFolder A megadott mappa ahol a képek találhatóak.
     * @param names A beolvasandó képek neveinek listája.
     * @throws IOException Képbeolvasás közben hiba lép fel.
     */
    public ImageBank(String subFolder, List<String> names) throws IOException {
        LoadImages(subFolder, names);
    }

    /**
     * Betölti a BufferedImage-eket, a kulcsukkal együtt az images Mapbe teszi.
     * @param subFolder A megadott mappa ahol a képek találhatóak.
     * @param names A beolvasandó képek neveinek listája.
     * @throws IOException Képbeolvasás közben hiba lép fel.
     */
    private void LoadImages(String subFolder, List<String> names) throws IOException {
        this.images = new HashMap<>();
        for (String name : names) {
            var path = "Images/" + subFolder + "/" + name + ".jpg";
            InputStream imageStream = getClass().getResourceAsStream(path);
            BufferedImage image = null;
            if (imageStream != null) {
                image = ImageIO.read(imageStream);
            }
            this.images.put(name, image);
        }
    }

    /**
     * Azzal a BufferedImage-el tér vissza, aminek az osztály images attribútumában a kulcsa a paraméterként megadott név
     * egy speciális (kisbetűs, space mentes) formája.
     * @param name A paraméterként megadott név.
     * @return A hozzá tartozó BufferedImage, null ha nincs ilyen.
     */
    public BufferedImage getByName(String name) {
        String cleanedName = name.toLowerCase().replace(" ", "");
        if (!images.containsKey(cleanedName)) {
            return null;
        }
        return images.get(cleanedName);
    }
}
