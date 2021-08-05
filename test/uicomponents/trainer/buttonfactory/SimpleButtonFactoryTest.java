package uicomponents.trainer.buttonfactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uicomponents.trainer.ButtonFactory;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class SimpleButtonFactoryTest {
    private ButtonFactory buttonFactory;

    @BeforeEach
    void setup(){
        buttonFactory = new SimpleButtonFactory();
    }

    @Test
    void canCreateStartButton(){
        String buttonText = "Test";
        AbstractButton startButton = buttonFactory.constructButton(buttonText);
        assertEquals(buttonText, startButton.getText());
    }
}