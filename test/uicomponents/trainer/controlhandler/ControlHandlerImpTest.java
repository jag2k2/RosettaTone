package uicomponents.trainer.controlhandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statemodels.ScoreImp;
import statemodels.TrainerStateImp;
import uicomponents.ScoreKeepable;
import uicomponents.TrainerState;
import uicomponents.trainer.ButtonFactory;
import uicomponents.trainer.ControlHandler;
import uicomponents.trainer.buttonfactory.SimpleButtonFactory;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;

class ControlHandlerImpTest {
    private ControlHandler controlHandler;
    private TrainerState trainerState;
    private ScoreKeepable score;

    @BeforeEach
    void setup(){
        trainerState = new TrainerStateImp();
        score = new ScoreImp();
        controlHandler = new ControlHandlerImp(trainerState, score);
    }

    @Test
    void canStart() {
        ButtonFactory simpleButtonFactory = new SimpleButtonFactory();

        AbstractButton startButton = controlHandler.createStartButton(simpleButtonFactory);

        startButton.doClick();
        assertTrue(trainerState.isEnabled());
    }

    @Test
    void canStop(){
        ButtonFactory simpleButtonFactory = new SimpleButtonFactory();

        AbstractButton stopButton = controlHandler.createStopButton(simpleButtonFactory);

        trainerState.enable();
        score.addHit();
        score.addMiss();

        assertTrue(trainerState.isEnabled());
        assertFalse(score.isReset());

        stopButton.doClick();
        assertTrue(score.isReset());
        assertFalse(trainerState.isEnabled());
    }

    @Test
    void createReset() {
        ButtonFactory simpleButtonFactory = new SimpleButtonFactory();
        AbstractButton resetButton = controlHandler.createResetButton(simpleButtonFactory);

        score.addHit();
        score.addMiss();

        assertFalse(score.isReset());
        resetButton.doClick();
        assertTrue(score.isReset());
    }
}