package uicomponents.trainer;

import uicomponents.trainer.buttonfactory.SimpleControlButtonFactory;

import javax.swing.*;
import java.awt.*;


public class TrainerControlImp extends JComponent {

    public TrainerControlImp(ControlHandler controlHandler){
        ControlButtonFactory simpleControlButtonFactory = new SimpleControlButtonFactory();

        Component startButton = controlHandler.createStartButton(simpleControlButtonFactory);
        Component stopButton = controlHandler.createStopButton(simpleControlButtonFactory);
        Component resetButton = controlHandler.createResetButton(simpleControlButtonFactory);

        this.setLayout(new FlowLayout());
        this.add(startButton);
        this.add(stopButton);
        this.add(resetButton);
    }
}
