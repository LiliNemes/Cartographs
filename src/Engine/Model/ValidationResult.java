package Engine.Model;

/**
 * Enum osztály a validációk és ellenőrzések visszatéréséhez.
 */
public enum ValidationResult {
    Ok,
    InvalidArrangement,
    DifferentTerrains,
    InvalidTerrain,
    TileNotEmpty,
    MustPutOnRuin,
    NoSelection,
    OnlySingleReplacement
}
