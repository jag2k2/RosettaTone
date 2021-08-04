package uicomponents.trainer.statechangers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statemodels.ScoreImp;
import statemodels.TrainerStateImp;
import uicomponents.ScoreKeepable;
import uicomponents.TrainerState;
import uicomponents.trainer.GameController;
import uicomponents.trainer.buttonfactory.SimpleButtonFactory;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;

class GameControllerImpTest {
    private GameController gameController;
    private TrainerState trainerState;
    private ScoreKeepable score;

    @BeforeEach
    void setup(){
        trainerState = new TrainerStateImp();
        score = new ScoreImp();
        gameController = new GameControllerImp(trainerState, score);
    }

    @Test
    void canStart() {
        ButtonFactory simpleButtonFactory = new SimpleButtonFactory();

        AbstractButton startButton = gameController.createStartButton(simpleButtonFactory);

        startButton.doClick();
        assertTrue(trainerState.isEnabled());
    }

    @Test
    void canStop(){
        ButtonFactory simpleButtonFactory = new SimpleButtonFactory();

        AbstractButton stopButton = gameController.createStopButton(simpleButtonFactory);

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
        AbstractButton resetButton = gameController.createResetButton(simpleButtonFactory);

        score.addHit();
        score.addMiss();

        assertFalse(score.isReset());
        resetButton.doClick();
        assertTrue(score.isReset());
    }
}