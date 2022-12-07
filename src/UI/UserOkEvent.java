package UI;

import Engine.Model.PlayerTilesSelection;

import java.util.EventListener;
import java.util.EventObject;

/**
 * EventObject-ből leszármaztatott UserOkEvent class.
 */
public class UserOkEvent extends EventObject {

    private final PlayerTilesSelection playerTilesSelection;

    /**
     * Konstruktor.
     * @param source Az az object, amin az event megtörténik.
     * @param playerTilesSelection A játékos által kiválasztott mezők.
     */
    public UserOkEvent(Object source, PlayerTilesSelection playerTilesSelection) {
        super(source);
        this.playerTilesSelection = playerTilesSelection;
    }

    /**
     *
     * @return A játékos által kiválasztott mezők.
     */
    public PlayerTilesSelection getPlayerTilesSelection() {
        return playerTilesSelection;
    }
}

/**
 * EventListener-ből leszármaztatott UserOkEventListener class.
 */
interface UserOkEventListener extends EventListener {
    /**
     * Mi történik az event megtörténésekor.
     * @param event paraméterként kapott event.
     */
    void userOkEventOccurred(UserOkEvent event);
}

