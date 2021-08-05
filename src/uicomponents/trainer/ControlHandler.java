package uicomponents.trainer;

import javax.swing.*;

public interface ControlHandler {
    AbstractButton createStartButton(ButtonFactory buttonFactory);
    AbstractButton createStopButton(ButtonFactory buttonFactory);
    AbstractButton createResetButton(ButtonFactory buttonFactory);
}
