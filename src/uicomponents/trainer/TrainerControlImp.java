package uicomponents.trainer;

import uicomponents.trainer.buttonfactory.SimpleButtonFactory;
import uicomponents.trainer.statechangers.ButtonFactory;
import javax.swing.*;
import java.awt.*;


public class TrainerControlImp extends JComponent {

    public TrainerControlImp(GameController gameController){
        ButtonFactory simpleButtonFactory = new SimpleButtonFactory();

        Component startButton = gameController.createStartButton(simpleButtonFactory);
        Component stopButton = gameController.createStopButton(simpleButtonFactory);
        Component resetButton = gameController.createResetButton(simpleButtonFactory);

        this.setLayout(new FlowLayout());
        this.add(startButton);
        this.add(stopButton);
        this.add(resetButton);
    }
}
