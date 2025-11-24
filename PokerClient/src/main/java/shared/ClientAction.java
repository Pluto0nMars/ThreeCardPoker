package shared;

import java.io.Serializable;

public enum ClientAction implements Serializable {
    START_ROUND,
    PLACE_BET,
    PLAY,
    FOLD,
    QUIT,
    RESTART
}
