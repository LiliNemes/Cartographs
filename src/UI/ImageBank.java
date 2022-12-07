package UI;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageBank {

    Map<String, BufferedImage> images;

    public ImageBank(String subFolder, List<String> names) throws IOException {
        LoadImages(subFolder, names);
    }

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

    public BufferedImage getByName(String name) {
        String cleanedName = name.toLowerCase().replace(" ", "");
        if (!images.containsKey(cleanedName)) {
            return null;
        }
        return images.get(cleanedName);
    }
}
