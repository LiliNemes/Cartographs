package Engine.Model;

import java.io.Serializable;

/**
 * Egy játékos pontozó- és játéklapját reprezentáló osztály.
 */
public class PlayerSheet implements Serializable {
    public String getName() {
        return name;
    }

    private String name;
    private Board board;
    private int accumulatedGold;
    private int score;

    /**
     * Konstruktor.
     * @param name A játékos neve.
     * @param board A lapon lévő játéktábla.
     */
    public PlayerSheet(String name, Board board) {
        this.name = name;
        this.board = board;
        accumulatedGold =0;
        score = 0;
    }

    /**
     * A játékos pontszámát változtatja.
     * @param i Hány pontot kell hozzáadni a játékos jelenlegi pontszámához.
     */
    public void setScore(int i) {
        score+=i;
    }

    /**
     *
     * @return A lapon lévő játéktábla.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Ellenőrzi, hogy a lehelyezni kívánt alakzat megfelel-e a kártya illetve a játéktábla szabályainak.
     * @param playerTilesSelection A játékos által kijelölt mezők.
     * @param currentDiscoveryCard Az éppen játékban lévő felfedezőjkártya.
     * @return ValidationResult, Ok ha minden rendben, ha nem akkor speciális, a problémára vonatkozó.
     */
    public ValidationResult check(PlayerTilesSelection playerTilesSelection, DiscoveryCard currentDiscoveryCard, boolean isThereRuinCard) {

        boolean specialDiscoveryCardValidation = false;
        if (isThereRuinCard) {
            if (!this.board.isOnRuin(playerTilesSelection))
            {
                if (canBePlacedOnRuin(currentDiscoveryCard))
                    return ValidationResult.MustPutOnRuin;
                // cannot place on ruin must place a single tile
                specialDiscoveryCardValidation = true;
            }
        }

        if (!canBePlaced(currentDiscoveryCard))
            specialDiscoveryCardValidation = true;

        ValidationResult one;
        if (specialDiscoveryCardValidation) {
            one = currentDiscoveryCard.specialCheck(playerTilesSelection);
        }
        else {
            one = currentDiscoveryCard.check(playerTilesSelection);
        }

        ValidationResult two = this.board.check(playerTilesSelection);
        if (one==ValidationResult.Ok && two==ValidationResult.Ok)
            return ValidationResult.Ok;
        if(one!=ValidationResult.Ok)
            return one;
        return two;
    }

    private boolean canBePlacedOnRuin(DiscoveryCard discoveryCard) {

        if (!this.board.areThereFreeRuinTiles())
            return false;

        var layouts = discoveryCard.getAllLayouts();
        for (int i=0; i<layouts.size(); i++) {
            if (this.board.canBePlacedOnRuin(layouts.get(i)))
                return true;
        }
        return false;
    }

    private boolean canBePlaced(DiscoveryCard discoveryCard) {

        var layouts = discoveryCard.getAllLayouts();
        for (int i=0; i<layouts.size(); i++) {
            if (this.board.canBePlaced(layouts.get(i)))
                return true;
        }
        return false;
    }

    /**
     * Ha a check ValidationResult.Ok-ot ad vissza, végrehajtja a lépést.
     * @param playerTilesSelection A játékos által kiválasztott mezők.
     * @param currentCard A játékban lévő felfedezőkártya.
     * @return ValidationResult.Ok ha sikeres, ha nem akkor más ValidationResult.
     */
    public ValidationResult execute(PlayerTilesSelection playerTilesSelection, DiscoveryCardBase currentCard, boolean isThereRuinCard) {

        if (currentCard.getCardType() == DiscoveryCardType.Ambush) {
            this.board.executeAmbush(playerTilesSelection);
            return ValidationResult.Ok;
        }

        if (playerTilesSelection.getSelectedTiles().size() == 0) {
            return ValidationResult.NoSelection;
        }

        var currentDiscoveryCard = (DiscoveryCard)currentCard;
        ValidationResult vr = check(playerTilesSelection, currentDiscoveryCard, isThereRuinCard);
        if(vr!=ValidationResult.Ok)
            return vr;
        ExecutionResult er = this.board.execute(playerTilesSelection);
        if(er.getResult()!=ValidationResult.Ok)
            return er.getResult();
        this.accumulatedGold+=er.getGoldYield();
        if(currentDiscoveryCard.yieldsGold(playerTilesSelection))
            this.accumulatedGold++;
        return ValidationResult.Ok;
    }

    /**
     *
     * @return A játékos által összegyűjtött aranyak.
     */
    public int getAccumulatedGold() {
        return this.accumulatedGold;
    }

    public int getMonsterPoints() {
        return this.board.getMonsterPoints() * -1;
    }
}
