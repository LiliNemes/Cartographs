package Engine.Model;

/**
 * Egy játékos pontozó- és játéklapját reprezentáló osztály.
 */
public class PlayerSheet {
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
    public ValidationResult check(PlayerTilesSelection playerTilesSelection, DiscoveryCard currentDiscoveryCard) {
        ValidationResult one = currentDiscoveryCard.check(playerTilesSelection);
        boolean yieldsGold=currentDiscoveryCard.yieldsGold(playerTilesSelection);
        ValidationResult two = this.board.check(playerTilesSelection,yieldsGold);
        if (one==ValidationResult.Ok && two==ValidationResult.Ok)
            return ValidationResult.Ok;
        if(one!=ValidationResult.Ok)
            return one;
        return two;
    }

    /**
     * Ha a check ValidationResult.Ok-ot ad vissza, végrehajtja a lépést.
     * @param playerTilesSelection A játékos által kiválasztott mezők.
     * @param currentDiscoveryCard A játékban lévő felfedezőkártya.
     * @return ValidationResult.Ok ha sikeres, ha nem akkor más ValidationResult.
     */
    public ValidationResult execute(PlayerTilesSelection playerTilesSelection, DiscoveryCard currentDiscoveryCard) {
        ValidationResult vr = check(playerTilesSelection, currentDiscoveryCard);
        if(vr!=ValidationResult.Ok)
            return vr;
        ExecutionResult er = this.board.execute(playerTilesSelection, false);
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
        return accumulatedGold;
    }
}
