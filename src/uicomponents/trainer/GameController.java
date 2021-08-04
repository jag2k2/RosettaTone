package uicomponents.trainer;

import uicomponents.trainer.statechangers.ButtonFactory;

import javax.swing.*;

public interface GameController {
    AbstractButton createStartButton(ButtonFactory buttonFactory);
    AbstractButton createStopButton(ButtonFactory buttonFactory);
    AbstractButton createResetButton(ButtonFactory buttonFactory);
}
