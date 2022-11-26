package Engine.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerSheetTest {
    PlayerTilesSelection ps;
    PlayerSheet sheet;

    @Before
    public void init() {
        DiscoveryCardDeck.createDeck(false);
        ps=new PlayerTilesSelection();
        Board b=new Board(5, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        sheet =new PlayerSheet("Lili", b);
    }

    /**
     * A forgottenForest DiscoveryCardra jó lerakás ellenőrzése.
     * @throws Exception ha rossz kitöltés.
     */
    @Test
    public void forgottenForestOK() throws Exception {
        DiscoveryCard forgottenForest=DiscoveryCardDeck.ForgottenForest;
        ps.addTile(1, 0, TerrainType.Forest);
        ps.addTile(2, 1, TerrainType.Forest);
        ValidationResult expected=ValidationResult.Ok;
        Assert.assertEquals(expected, sheet.check(ps, forgottenForest));
    }

    /**
     * A forgottenForest DiscoveryCardra a kártya miatt nem jó lerakás ellenőrzése.
     * @throws Exception ha rossz kitöltés.
     */
    @Test
    public void forgottenForestCardNotOK() throws Exception {
        DiscoveryCard forgottenForest=DiscoveryCardDeck.ForgottenForest;
        ps.addTile(1, 0, TerrainType.Water);
        ps.addTile(2, 1, TerrainType.Water);
        ValidationResult expected = ValidationResult.InvalidTerrain;
        Assert.assertEquals(expected, sheet.check(ps, forgottenForest));
    }

    /**
     * A forgottenForest DiscoveryCardra a játéktábla miatt nem jó lerakás ellenőrzése.
     * @throws Exception ha rossz kitöltés.
     */
    @Test
    public void forgottenForestBoardNotOK() throws Exception {
        DiscoveryCard forgottenForest=DiscoveryCardDeck.ForgottenForest;
        ps.addTile(1, 1, TerrainType.Forest);
        ps.addTile(2, 2, TerrainType.Forest);
        ValidationResult expected = ValidationResult.TileNotEmpty;
        Assert.assertEquals(expected, sheet.check(ps, forgottenForest));
    }

    /**
     * A forgottenForest DiscoveryCardra execute, ha pénz is jár érte.
     * @throws Exception ha rossz kitöltés.
     */
    @Test
    public void forgottenForestexecuteOKGold() throws Exception {
        DiscoveryCard forgottenForest=DiscoveryCardDeck.ForgottenForest;
        ps.addTile(0, 1, TerrainType.Forest);
        ps.addTile(1, 2, TerrainType.Forest);
        ValidationResult expected = ValidationResult.Ok;
        Assert.assertEquals(expected, sheet.execute(ps, forgottenForest));
        Assert.assertEquals(1, sheet.getAccumulatedGold());
    }

    /**
     * A forgottenForest DiscoveryCardra execute, ha pénz nem jár érte.
     * @throws Exception ha rossz kitöltés.s
     */
    @Test
    public void forgottenForestExecuteNoGold() throws Exception {
        DiscoveryCard forgottenForest=DiscoveryCardDeck.ForgottenForest;
        ps.addTile(4, 4, TerrainType.Forest);
        ps.addTile(4, 3, TerrainType.Forest);
        ps.addTile(3, 3, TerrainType.Forest);
        ps.addTile(3, 2, TerrainType.Forest);
        ValidationResult expected = ValidationResult.Ok;
        Assert.assertEquals(expected, sheet.execute(ps, forgottenForest));
        Assert.assertEquals(0, sheet.getAccumulatedGold());
    }

}