package uicomponents.trainer;

import uicomponents.trainer.buttonfactory.SimpleButtonFactory;

import javax.swing.*;
import java.awt.*;


public class TrainerControlImp extends JComponent {

    public TrainerControlImp(ControlHandler controlHandler){
        ButtonFactory simpleButtonFactory = new SimpleButtonFactory();

        Component startButton = controlHandler.createStartButton(simpleButtonFactory);
        Component stopButton = controlHandler.createStopButton(simpleButtonFactory);
        Component resetButton = controlHandler.createResetButton(simpleButtonFactory);

        this.setLayout(new FlowLayout());
        this.add(startButton);
        this.add(stopButton);
        this.add(resetButton);
    }
}
