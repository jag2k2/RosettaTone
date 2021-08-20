package uicomponents.trainer.eventhandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statemodels.ScoreImp;
import statemodels.TrainerStateImp;
import uicomponents.ScoreKeepable;
import uicomponents.TrainerState;
import uicomponents.trainer.ControlButtonFactory;
import uicomponents.trainer.ControlHandler;
import uicomponents.trainer.buttonfactory.SimpleControlButtonFactory;
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
        ControlButtonFactory simpleControlButtonFactory = new SimpleControlButtonFactory();

        AbstractButton startButton = controlHandler.createStartButton(simpleControlButtonFactory);

        startButton.doClick();
        assertTrue(trainerState.isEnabled());
    }

    @Test
    void canStop(){
        ControlButtonFactory simpleControlButtonFactory = new SimpleControlButtonFactory();

        AbstractButton stopButton = controlHandler.createStopButton(simpleControlButtonFactory);

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
        ControlButtonFactory simpleControlButtonFactory = new SimpleControlButtonFactory();
        AbstractButton resetButton = controlHandler.createResetButton(simpleControlButtonFactory);

        score.addHit();
        score.addMiss();

        assertFalse(score.isReset());
        resetButton.doClick();
        assertTrue(score.isReset());
    }
}