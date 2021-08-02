package uicomponents.trainer;

import uicomponents.UIComponent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrainerControlImp implements UIComponent, ActionListener {
    private static final String startButtonName = "Start";
    private static final String stopButtonName = "Stop";
    private static final String resetButtonName = "Reset";

    private final TrainerStateModifier trainerState;
    private final Resettable score;

    public TrainerControlImp(TrainerStateModifier trainerState, Resettable score){
        this.trainerState = trainerState;
        this.score = score;
    }

    @Override
    public Component makeComponent() {
        JPanel panel = new JPanel();

        AbstractButton startButton = new JButton(startButtonName);
        AbstractButton stopButton = new JButton(stopButtonName);
        AbstractButton resetButton = new JButton(resetButtonName);

        startButton.setName(startButtonName);
        stopButton.setName(stopButtonName);
        resetButton.setName(resetButtonName);

        panel.add(startButton);
        panel.add(stopButton);
        panel.add(resetButton);

        startButton.addActionListener(this);
        stopButton.addActionListener(this);
        resetButton.addActionListener(this);

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
                score.reset();
            }
            else if (buttonName.equals(resetButtonName)){
                score.reset();
            }
        }
    }
}
