package Engine.Model;

import java.io.Serializable;

/**
 * Egy játékos pontozó- és játéklapját reprezentáló osztály.
 */
public class PlayerSheet implements Serializable {
    private final String name;
    private final Board board;
    // Befejezett évszakonként tartalmazza a részpontszámokat
    // Évszakok sorrendje megfelel a játékban szereplő sorrenddel
    // A pontok sorrendje: A, B, money, monster
    private final int[][] results;
    private int accumulatedGold;
    /**
     * Konstruktor.
     *
     * @param name  A játékos neve.
     * @param board A lapon lévő játéktábla.
     */
    public PlayerSheet(String name, Board board) {
        this.name = name;
        this.board = board;
        accumulatedGold = 0;
        results = new int[4][4];
    }

    public String getName() {
        return name;
    }

    public void setSeasonResults(Seasons season, int[] s) {
        int id = -1;
        switch (season) {
            case spring -> id = 0;
            case summer -> id = 1;
            case autumn -> id = 2;
            case winter -> id = 3;
        }
        System.arraycopy(s, 0, results[id], 0, 4);
    }

    public int[] getSeasonScores(Seasons s) {
        return switch (s) {
            case spring -> results[0];
            case summer -> results[1];
            case autumn -> results[2];
            case winter -> results[3];
        };
    }

    /**
     * @return A lapon lévő játéktábla.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Ellenőrzi, hogy a lehelyezni kívánt alakzat megfelel-e a kártya illetve a játéktábla szabályainak.
     *
     * @param playerTilesSelection A játékos által kijelölt mezők.
     * @param currentDiscoveryCard Az éppen játékban lévő felfedezőjkártya.
     * @return ValidationResult, Ok ha minden rendben, ha nem akkor speciális, a problémára vonatkozó.
     */
    public ValidationResult check(PlayerTilesSelection playerTilesSelection, DiscoveryCard currentDiscoveryCard, boolean isThereRuinCard) {

        boolean specialDiscoveryCardValidation = false;
        if (isThereRuinCard) {
            if (!this.board.isOnRuin(playerTilesSelection)) {
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
        } else {
            one = currentDiscoveryCard.check(playerTilesSelection);
        }

        ValidationResult two = this.board.check(playerTilesSelection);
        if (one == ValidationResult.Ok && two == ValidationResult.Ok)
            return ValidationResult.Ok;
        if (one != ValidationResult.Ok)
            return one;
        return two;
    }

    private boolean canBePlacedOnRuin(DiscoveryCard discoveryCard) {

        if (!this.board.areThereFreeRuinTiles())
            return false;

        var layouts = discoveryCard.getAllLayouts();
        for (Layout layout : layouts) {
            if (this.board.canBePlacedOnRuin(layout))
                return true;
        }
        return false;
    }

    private boolean canBePlaced(DiscoveryCard discoveryCard) {

        var layouts = discoveryCard.getAllLayouts();
        for (Layout layout : layouts) {
            if (this.board.canBePlaced(layout))
                return true;
        }
        return false;
    }

    /**
     * Ha a check ValidationResult.Ok-ot ad vissza, végrehajtja a lépést.
     *
     * @param playerTilesSelection A játékos által kiválasztott mezők.
     * @param currentCard          A játékban lévő felfedezőkártya.
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

        var currentDiscoveryCard = (DiscoveryCard) currentCard;
        ValidationResult vr = check(playerTilesSelection, currentDiscoveryCard, isThereRuinCard);
        if (vr != ValidationResult.Ok)
            return vr;
        ExecutionResult er = this.board.execute(playerTilesSelection);
        if (er.getResult() != ValidationResult.Ok)
            return er.getResult();
        this.accumulatedGold += er.getGoldYield();
        if (currentDiscoveryCard.yieldsGold(playerTilesSelection))
            this.accumulatedGold++;
        return ValidationResult.Ok;
    }

    /**
     * @return A játékos által összegyűjtött aranyak.
     */
    public int getAccumulatedGold() {
        return this.accumulatedGold;
    }

    public int getMonsterPoints() {
        return this.board.getMonsterPoints() * -1;
    }

    public int getScoreSum() {
        int sum = 0;
        for (int[] i : results) {
            for (int j : i)
                sum += j;
        }
        return sum;
    }
}
