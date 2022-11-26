package Engine.Model;

import Engine.Builder.ArrangementRuleFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrangementRuleTest {


    /**
     * Ugyanaz az alakzat forgatás nélkül check
     * @throws Exception
     */
    @Test
    public void testSameLayoutNoRotation() throws Exception {
        ArrangementRule rule = ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;1,0;2,0"), false);
        PlayerTilesSelection selection = new PlayerTilesSelection();
        selection.addTile(5, 5, TerrainType.Farm);
        selection.addTile(6, 5, TerrainType.Farm);
        selection.addTile(7, 5, TerrainType.Farm);

        var validationResult = rule.Check(selection);
        assertEquals(ValidationResult.Ok, validationResult);
    }

    /**
     * Rossz alakzat check.
     * @throws Exception
     */
    @Test
    public void testDifferentLayout() throws Exception {
        ArrangementRule rule = ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;1,0;2,0"), false);
        PlayerTilesSelection selection = new PlayerTilesSelection();
        selection.addTile(5, 5, TerrainType.Farm);
        selection.addTile(6, 5, TerrainType.Farm);
        selection.addTile(6, 6, TerrainType.Farm);

        var validationResult = rule.Check(selection);
        assertEquals(ValidationResult.InvalidArrangement, validationResult);
    }

    /**
     * Elforgatott alakzat check.
     * @throws Exception
     */
    @Test
    public void testTurnedLayout() throws Exception {
        ArrangementRule rule = ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;0,1;0,2;1,1"), false);
        PlayerTilesSelection selection = new PlayerTilesSelection();
        selection.addTile(7, 3, TerrainType.Farm);
        selection.addTile(8, 3, TerrainType.Farm);
        selection.addTile(9, 3, TerrainType.Farm);
        selection.addTile(8, 4, TerrainType.Farm);

        var validationResult = rule.Check(selection);
        assertEquals(ValidationResult.Ok,validationResult);
    }

    /**
     * Tükrözött alakzat check.
     * @throws Exception
     */
    @Test
    public void mirroredTurnedLayout() throws Exception {
        ArrangementRule rule = ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;0,1;1,1;1,2"), false);
        PlayerTilesSelection selection = new PlayerTilesSelection();
        selection.addTile(7, 3, TerrainType.Farm);
        selection.addTile(8, 3, TerrainType.Farm);
        selection.addTile(9, 4, TerrainType.Farm);
        selection.addTile(8, 4, TerrainType.Farm);

        var validationResult = rule.Check(selection);
        assertEquals(ValidationResult.Ok,validationResult);
    }
}
