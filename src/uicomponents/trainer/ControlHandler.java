package uicomponents.trainer;

import javax.swing.*;

public interface ControlHandler {
    AbstractButton createStartButton(ControlButtonFactory controlButtonFactory);
    AbstractButton createStopButton(ControlButtonFactory controlButtonFactory);
    AbstractButton createResetButton(ControlButtonFactory controlButtonFactory);
}
