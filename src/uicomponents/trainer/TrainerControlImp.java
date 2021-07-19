package uicomponents.trainer;

import uicomponents.UIComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrainerControlImp implements UIComponent, ActionListener {
    private static final String startButtonName = "start";
    private static final String stopButtonName = "stop";
    private static final String resetButtonName = "reset";

    private final TrainerStateModifier trainerState;
    private final Resettable score;
    private final JButton startButton;
    private final JButton stopButton;
    private final JButton resetButton;

    public TrainerControlImp(TrainerStateModifier trainerState, Resettable score){
        this.trainerState = trainerState;
        this.score = score;
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        resetButton = new JButton("Reset");

        startButton.setName(startButtonName);
        stopButton.setName(stopButtonName);
        resetButton.setName(resetButtonName);

        startButton.addActionListener(this);
        stopButton.addActionListener(this);
        resetButton.addActionListener(this);
    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel();
        panel.add(startButton);
        panel.add(stopButton);
        panel.add(resetButton);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton){
            JButton pushedButton = (JButton) e.getSource();
            String buttonName = pushedButton.getName();
            if (buttonName.equals(startButtonName)){
                trainerState.enable();
            }
            else if (buttonName.equals(stopButtonName)){
                trainerState.disable();
            }
            else if (buttonName.equals(resetButtonName)){
                score.reset();
            }
        }
    }
}
