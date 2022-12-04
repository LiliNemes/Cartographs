package Engine.Model;

import java.io.Serializable;

/**
 * Felfedező kártyák megvalósításában segítő osztály,
 */
public class DiscoveryCardBase implements Serializable {
    private DiscoveryCardType cardType;
    private String name;

    /**
     * Konstruktor.
     * @param cardType Felfedezőkártya típusa.
     * @param name Felfedezőkártya neve.
     */
    public DiscoveryCardBase(DiscoveryCardType cardType, String name) {
        this.cardType = cardType;
        this.name = name;
    }

    /**
     *
     * @return Felfedezőkártya típusa.
     */
    public DiscoveryCardType getCardType() {
        return cardType;
    }

    /**
     *
     * @return Felfedezőkártya neve.
     */
    public String getName() {
        return name;
    }
}
