package instrument.keybinder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Transmitter;
import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

//https://stackoverflow.com/questions/47336703/simulate-key-press-in-junit-tests

class KeyBindTransmitterTest {
    private Transmitter keyBindTransmitter;
    private Receiver mockReceiver;
    private JPanel panel;

    @BeforeEach
    void setup() {
        mockReceiver = new MockReceiver();
        panel = new JPanel();
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setVisible(true);
        keyBindTransmitter = new KeyBindTransmitter(panel);
        keyBindTransmitter.setReceiver(mockReceiver);
    }

    @Test
    void tempRegularKeyStrokes() throws Exception {
        int modifiers = 0;
        testKeyStroke(KeyEvent.VK_Q, 'q', modifiers);
        testKeyStroke(KeyEvent.VK_W, 'w', modifiers);
        testKeyStroke(KeyEvent.VK_E, 'e', modifiers);
        testKeyStroke(KeyEvent.VK_R, 'r', modifiers);
        testKeyStroke(KeyEvent.VK_T, 't', modifiers);
        testKeyStroke(KeyEvent.VK_Y, 'y', modifiers);
        testKeyStroke(KeyEvent.VK_U, 'u', modifiers);
        testKeyStroke(KeyEvent.VK_I, 'i', modifiers);
        testKeyStroke(KeyEvent.VK_O, 'o', modifiers);
        testKeyStroke(KeyEvent.VK_P, 'p', modifiers);
        testKeyStroke(KeyEvent.VK_OPEN_BRACKET, '[', modifiers);
        testKeyStroke(KeyEvent.VK_CLOSE_BRACKET, ']', modifiers);
        testKeyStroke(KeyEvent.VK_1, '1', modifiers);
        testKeyStroke(KeyEvent.VK_2, '2', modifiers);
        testKeyStroke(KeyEvent.VK_3, '3', modifiers);
        testKeyStroke(KeyEvent.VK_4, '4', modifiers);
        testKeyStroke(KeyEvent.VK_5, '5', modifiers);
        testKeyStroke(KeyEvent.VK_6, '6', modifiers);
        testKeyStroke(KeyEvent.VK_7, '7', modifiers);
        testKeyStroke(KeyEvent.VK_8, '8', modifiers);
        testKeyStroke(KeyEvent.VK_9, '9', modifiers);
        testKeyStroke(KeyEvent.VK_0, '0', modifiers);
        testKeyStroke(KeyEvent.VK_MINUS, '-', modifiers);
        testKeyStroke(KeyEvent.VK_EQUALS, '=', modifiers);
    }

    @Test
    void testShiftKeyStrokes() throws Exception {
        int modifiers = InputEvent.SHIFT_DOWN_MASK;
        testKeyStroke(KeyEvent.VK_Q, 'Q', modifiers);
        testKeyStroke(KeyEvent.VK_W, 'W', modifiers);
        testKeyStroke(KeyEvent.VK_E, 'E', modifiers);
        testKeyStroke(KeyEvent.VK_R, 'R', modifiers);
        testKeyStroke(KeyEvent.VK_T, 'T', modifiers);
        testKeyStroke(KeyEvent.VK_Y, 'Y', modifiers);
        testKeyStroke(KeyEvent.VK_U, 'U', modifiers);
        testKeyStroke(KeyEvent.VK_I, 'I', modifiers);
        testKeyStroke(KeyEvent.VK_O, 'O', modifiers);
        testKeyStroke(KeyEvent.VK_P, 'P', modifiers);
        testKeyStroke(KeyEvent.VK_OPEN_BRACKET, '{', modifiers);
        testKeyStroke(KeyEvent.VK_CLOSE_BRACKET, '}', modifiers);
        testKeyStroke(KeyEvent.VK_1, '!', modifiers);
        testKeyStroke(KeyEvent.VK_2, '@', modifiers);
        testKeyStroke(KeyEvent.VK_3, '#', modifiers);
        testKeyStroke(KeyEvent.VK_4, '$', modifiers);
        testKeyStroke(KeyEvent.VK_5, '%', modifiers);
        testKeyStroke(KeyEvent.VK_6, '^', modifiers);
        testKeyStroke(KeyEvent.VK_7, '&', modifiers);
        testKeyStroke(KeyEvent.VK_8, '*', modifiers);
        testKeyStroke(KeyEvent.VK_9, '(', modifiers);
        testKeyStroke(KeyEvent.VK_0, ')', modifiers);
        testKeyStroke(KeyEvent.VK_MINUS, '_', modifiers);
        testKeyStroke(KeyEvent.VK_EQUALS, '+', modifiers);
    }

    private void testKeyStroke(int keyCode, char keyChar, int modifiers) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            panel.dispatchEvent(new KeyEvent(panel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), modifiers, keyCode, keyChar));
            Receiver expected = new MockReceiver(ShortMessage.NOTE_ON, CharConverter.toKey(keyChar));
            assertEquals(expected, mockReceiver);

            panel.dispatchEvent(new KeyEvent(panel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), modifiers, keyCode, keyChar));
            expected = new MockReceiver(ShortMessage.NOTE_OFF, CharConverter.toKey(keyChar));
            assertEquals(expected, mockReceiver);
        });
    }
}