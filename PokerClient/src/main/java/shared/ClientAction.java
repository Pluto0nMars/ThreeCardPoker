package shared;

import java.io.Serializable;

//prolly dont need all of these
public enum ClientAction implements Serializable {
    START_ROUND,
    PLACE_BET,
    PLAY,
    FOLD,
    QUIT,
    RESTART
}
