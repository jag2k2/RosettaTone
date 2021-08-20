package uicomponents.trainer.buttonfactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uicomponents.trainer.ControlButtonFactory;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class SimpleControlButtonFactoryTest {
    private ControlButtonFactory controlButtonFactory;

    @BeforeEach
    void setup(){
        controlButtonFactory = new SimpleControlButtonFactory();
    }

    @Test
    void canCreateStartButton(){
        String buttonText = "Test";
        AbstractButton startButton = controlButtonFactory.constructButton(buttonText);
        assertEquals(buttonText, startButton.getText());
    }
}