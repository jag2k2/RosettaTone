package statemodels;

import uicomponents.clefmode.ClefMode;

public interface ClefModeState {
    ClefMode getState();
    void setState(ClefMode clefMode);
    boolean trebleEnabled();
    boolean bassEnabled();
}
