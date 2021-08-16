package uicomponents.trainer.eventhandler;

import uicomponents.trainer.ButtonFactory;
import uicomponents.trainer.ControlHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*This game controller uses an abstract factory to create the buttons that trigger game state changes.  This pattern was
chosen because lots of different buttons could be used and I don't want to couple the application to just JButton.  One
day I hope to add a menu with these same options.  When that day comes all I need to do is feed this class a ButtonFactory
that returns JMenu items instead of JButtons.
 */

public class ControlHandlerImp implements ControlHandler, ActionListener {
    private static final String startText = "Start";
    private static final String stopText = "Stop";
    private static final String resetText = "Reset";

    private final Enableable trainerState;
    private final Resettable score;

    public ControlHandlerImp(Enableable trainerState, Resettable score) {
        this.trainerState = trainerState;
        this.score = score;
    }

    @Override
    public AbstractButton createStartButton(ButtonFactory buttonFactory){
        AbstractButton button = buttonFactory.constructButton(startText);
        button.addActionListener(this);
        button.setActionCommand(startText);
        return button;
    }

    @Override
    public AbstractButton createStopButton(ButtonFactory buttonFactory) {
        AbstractButton button = buttonFactory.constructButton(stopText);
        button.addActionListener(this);
        button.setActionCommand(stopText);
        return button;
    }

    @Override
    public AbstractButton createResetButton(ButtonFactory buttonFactory) {
        AbstractButton button = buttonFactory.constructButton(resetText);
        button.addActionListener(this);
        button.setActionCommand(resetText);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(startText)){
            trainerState.enable();
        } else if (command.equals(stopText)){
            trainerState.disable();
            score.reset();
        } else if (command.equals(resetText)){
            score.reset();
        }
    }
}
