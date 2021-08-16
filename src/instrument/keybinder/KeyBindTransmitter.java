package instrument.keybinder;

import utility.Maybe;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Transmitter;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyBindTransmitter implements Transmitter {
    private Maybe<Receiver> receiver;
    private final InputMap inputMap;
    private final ActionMap actionMap;

    public KeyBindTransmitter(JPanel panel) {
        this.receiver = new Maybe<>();
        this.inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        this.actionMap = panel.getActionMap();
    }

    @Override
    public void setReceiver(Receiver receiver) {
        this.receiver = new Maybe<>(receiver);

        Map<Integer, Character> keyCodes = new HashMap<>();
        keyCodes.put(KeyEvent.VK_Q, 'q');
        keyCodes.put(KeyEvent.VK_W, 'w');
        keyCodes.put(KeyEvent.VK_E, 'e');
        keyCodes.put(KeyEvent.VK_R, 'r');
        keyCodes.put(KeyEvent.VK_T, 't');
        keyCodes.put(KeyEvent.VK_Y, 'y');
        keyCodes.put(KeyEvent.VK_U, 'u');
        keyCodes.put(KeyEvent.VK_I, 'i');
        keyCodes.put(KeyEvent.VK_O, 'o');
        keyCodes.put(KeyEvent.VK_P, 'p');
        keyCodes.put(KeyEvent.VK_OPEN_BRACKET, '[');
        keyCodes.put(KeyEvent.VK_CLOSE_BRACKET, ']');
        keyCodes.put(KeyEvent.VK_1, '1');
        keyCodes.put(KeyEvent.VK_2, '2');
        keyCodes.put(KeyEvent.VK_3, '3');
        keyCodes.put(KeyEvent.VK_4, '4');
        keyCodes.put(KeyEvent.VK_5, '5');
        keyCodes.put(KeyEvent.VK_6, '6');
        keyCodes.put(KeyEvent.VK_7, '7');
        keyCodes.put(KeyEvent.VK_8, '8');
        keyCodes.put(KeyEvent.VK_9, '9');
        keyCodes.put(KeyEvent.VK_0, '0');
        keyCodes.put(KeyEvent.VK_MINUS, '-');
        keyCodes.put(KeyEvent.VK_EQUALS, '=');

        registerKeyCodes(keyCodes, 0);

        keyCodes.clear();
        keyCodes.put(KeyEvent.VK_Q, 'Q');
        keyCodes.put(KeyEvent.VK_W, 'W');
        keyCodes.put(KeyEvent.VK_E, 'E');
        keyCodes.put(KeyEvent.VK_R, 'R');
        keyCodes.put(KeyEvent.VK_T, 'T');
        keyCodes.put(KeyEvent.VK_Y, 'Y');
        keyCodes.put(KeyEvent.VK_U, 'U');
        keyCodes.put(KeyEvent.VK_I, 'I');
        keyCodes.put(KeyEvent.VK_O, 'O');
        keyCodes.put(KeyEvent.VK_P, 'P');
        keyCodes.put(KeyEvent.VK_OPEN_BRACKET, '{');
        keyCodes.put(KeyEvent.VK_CLOSE_BRACKET, '}');
        keyCodes.put(KeyEvent.VK_1, '!');
        keyCodes.put(KeyEvent.VK_2, '@');
        keyCodes.put(KeyEvent.VK_3, '#');
        keyCodes.put(KeyEvent.VK_4, '$');
        keyCodes.put(KeyEvent.VK_5, '%');
        keyCodes.put(KeyEvent.VK_6, '^');
        keyCodes.put(KeyEvent.VK_7, '&');
        keyCodes.put(KeyEvent.VK_8, '*');
        keyCodes.put(KeyEvent.VK_9, '(');
        keyCodes.put(KeyEvent.VK_0, ')');
        keyCodes.put(KeyEvent.VK_MINUS, '_');
        keyCodes.put(KeyEvent.VK_EQUALS, '+');

        registerKeyCodes(keyCodes, InputEvent.SHIFT_DOWN_MASK);
    }

    @Override
    public Receiver getReceiver() {
        for (Receiver activeReceiver : receiver){
            return activeReceiver;
        }
        return null;
    }

    @Override
    public void close() {
        receiver = new Maybe<>();
        inputMap.clear();
        actionMap.clear();
    }

    private void registerKeyCodes(Map<Integer, Character> keyCodes, int modifiers){
        for (Map.Entry<Integer, Character> entry : keyCodes.entrySet()){
            Integer keyCode = entry.getKey();
            Character keyChar = entry.getValue();

            String pressString = keyChar + ".pressed";
            String releaseString = keyChar + ".released";

            inputMap.put(KeyStroke.getKeyStroke(keyCode, modifiers, false), pressString);
            inputMap.put(KeyStroke.getKeyStroke(keyCode, modifiers, true), releaseString);

            actionMap.put(pressString, new KeyAction(ShortMessage.NOTE_ON, keyChar));
            actionMap.put(releaseString, new KeyAction(ShortMessage.NOTE_OFF, keyChar));
        }
    }

    private class KeyAction extends AbstractAction {
        private final int command;
        private final char keyChar;

        public KeyAction(int command, char keyChar) {
            this.command = command;
            this.keyChar = keyChar;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int keyInt = CharConverter.toKey(keyChar);
            if(keyInt > 0) {
                for (Receiver activeReceiver : receiver){
                    try {
                        ShortMessage shortMessage = new ShortMessage();
                        shortMessage.setMessage(command, 0, keyInt, 100);
                        activeReceiver.send(shortMessage, e.getWhen());
                    } catch (InvalidMidiDataException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
