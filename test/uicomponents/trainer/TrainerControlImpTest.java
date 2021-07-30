package uicomponents.trainer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statemodels.*;

import static org.junit.jupiter.api.Assertions.*;

class TrainerControlImpTest {
    private TrainerControlImp trainerControl;
    private TrainerStateModifier trainerState;
    private ScoreImp score;

    @BeforeEach
    void setup(){
        trainerState = new TrainerStateImp();
        score = new ScoreImp();
        trainerControl = new TrainerControlImp(trainerState, score);
    }

    @Test
    void canStartTrainer(){

    }
}