package Engine.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DiscoveryCardTest {

    @Before
    public void init() {
        DiscoveryCardDeck.createDeck();
    }

    /**
     * Nem ad aranyat ha a kártya sem.
     */
    @Test
    public void fishingVillageYieldsGold() {
        DiscoveryCard fishingVillage=DiscoveryCardDeck.FishingVillage;
        PlayerTilesSelection ps= new PlayerTilesSelection();
        ps.addTile(3, 4, TerrainType.Village);
        ps.addTile(3, 5, TerrainType.Village);
        ps.addTile(3, 6, TerrainType.Village);
        ps.addTile(3, 7, TerrainType.Village);
        Assert.assertFalse(fishingVillage.yieldsGold(ps));
    }

    /**
     * Kártya egyik formájára jól ad aranyat.
     */
    @Test
    public void greatRiverAR1YieldsGold() {
        DiscoveryCard greatRiver = DiscoveryCardDeck.GreatRiver;
        PlayerTilesSelection ps= new PlayerTilesSelection();
        ps.addTile(6,2, TerrainType.Water);
        ps.addTile(5,2, TerrainType.Water);
        ps.addTile(4,2, TerrainType.Water);
        Assert.assertTrue(greatRiver.yieldsGold(ps));
    }

    /**
     * Kártya másik formájára jól nem ad aranyat.
     */
    @Test
    public void greatRiverAR2YieldsGold() {
        DiscoveryCard greatRiver = DiscoveryCardDeck.GreatRiver;
        PlayerTilesSelection ps= new PlayerTilesSelection();
        ps.addTile(0, 0, TerrainType.Water);
        ps.addTile(1, 0, TerrainType.Water);
        ps.addTile(1, 1, TerrainType.Water);
        ps.addTile(2, 1, TerrainType.Water);
        ps.addTile(2, 2, TerrainType.Water);
        Assert.assertFalse(greatRiver.yieldsGold(ps));
    }

    /**
     * Jó forma, járna érte arany, de rossz típus.
     */
    @Test
    public void greatRiverBadTerrainYieldsGold() {
        DiscoveryCard greatRiver = DiscoveryCardDeck.GreatRiver;
        PlayerTilesSelection ps= new PlayerTilesSelection();
        ps.addTile(6,2, TerrainType.Farm);
        ps.addTile(5,2, TerrainType.Farm);
        ps.addTile(4,2, TerrainType.Farm);
        Assert.assertFalse(greatRiver.yieldsGold(ps));
    }

    /**
     * Jó lerakást ellenőriz.
     */
    @Test
    public void checkHamlet() {
        DiscoveryCard hamlet = DiscoveryCardDeck.Hamlet;
        PlayerTilesSelection ps= new PlayerTilesSelection();
        ps.addTile(3,2, TerrainType.Village);
        ps.addTile(4,2, TerrainType.Village);
        ps.addTile(3,3, TerrainType.Village);
        Assert.assertEquals(ValidationResult.Ok, hamlet.check(ps));
    }

    /**
     * Nem azonos a mezők típusa lerakásra check.
     */
    @Test
    public void checkHomestead() {
        DiscoveryCard homestead = DiscoveryCardDeck.Homestead;
        PlayerTilesSelection ps= new PlayerTilesSelection();
        ps.addTile(3,2, TerrainType.Village);
        ps.addTile(4,2, TerrainType.Village);
        ps.addTile(3,3, TerrainType.Village);
        ps.addTile(3,1, TerrainType.Farm);
        Assert.assertEquals(ValidationResult.DifferentTerrains, homestead.check(ps));
    }

    /**
     * Rossz lerakás check.
     */
    @Test
    public void checkForgottenForest() {
        DiscoveryCard forgottenForest = DiscoveryCardDeck.ForgottenForest;
        PlayerTilesSelection ps= new PlayerTilesSelection();
        ps.addTile(2, 3, TerrainType.Forest);
        ps.addTile(3, 3, TerrainType.Forest);
        Assert.assertEquals(ValidationResult.InvalidArrangement, forgottenForest.check(ps));
    }

    /**
     * Rossz terrain check
     */
    @Test
    public void checkOrchard() {
        DiscoveryCard orchard = DiscoveryCardDeck.Orchard;
        PlayerTilesSelection ps= new PlayerTilesSelection();
        ps.addTile(2, 3, TerrainType.Village);
        ps.addTile(3, 3, TerrainType.Village);
        ps.addTile(4, 3, TerrainType.Village);
        ps.addTile(2, 4, TerrainType.Village);
        Assert.assertEquals(ValidationResult.InvalidTerrain, orchard.check(ps));
    }


}