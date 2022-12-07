package UI;

import Engine.Model.PlayerTilesSelection;

import java.util.EventListener;
import java.util.EventObject;

public class UserOkEvent extends EventObject {

    private final PlayerTilesSelection playerTilesSelection;
    public UserOkEvent(Object source, PlayerTilesSelection playerTilesSelection) {
        super(source);
        this.playerTilesSelection = playerTilesSelection;
    }

    public PlayerTilesSelection getPlayerTilesSelection() {
        return playerTilesSelection;
    }
}

interface UserOkEventListener extends EventListener {
    void userOkEventOccurred(UserOkEvent event);
}

