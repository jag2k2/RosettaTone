package instrument;

import javax.sound.midi.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MidiInstrumentSimImp implements KeyListener, Transmitter {
    private Receiver receiver;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Character keyChar = e.getKeyChar();
        int midiKey = mapCharToMidiKey(keyChar);
        if (midiKey > 0) {
            sendKey(ShortMessage.NOTE_ON, midiKey, e.getWhen());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Character keyChar = e.getKeyChar();
        int midiKey = mapCharToMidiKey(keyChar);
        if (midiKey > 0) {
            sendKey(ShortMessage.NOTE_OFF, midiKey, e.getWhen());
        }
    }

    protected int mapCharToMidiKey(Character keyChar){
        int key = -1;
        if (keyChar.equals('Q')){
            key = 48;
        }
        if (keyChar.equals('W')){
            key = 49;
        }
        if (keyChar.equals('E')){
            key = 50;
        }
        if (keyChar.equals('R')){
            key = 51;
        }
        if (keyChar.equals('T')){
            key = 52;
        }
        if (keyChar.equals('Y')){
            key = 53;
        }
        if (keyChar.equals('U')){
            key = 54;
        }
        if (keyChar.equals('I')){
            key = 55;
        }
        if (keyChar.equals('O')){
            key = 56;
        }
        if (keyChar.equals('P')){
            key = 57;
        }
        if (keyChar.equals('{')){
            key = 58;
        }
        if (keyChar.equals('}')){
            key = 59;
        }

        if (keyChar.equals('q')){
            key = 60;
        }
        if (keyChar.equals('w')){
            key = 61;
        }
        if (keyChar.equals('e')){
            key = 62;
        }
        if (keyChar.equals('r')){
            key = 63;
        }
        if (keyChar.equals('t')){
            key = 64;
        }
        if (keyChar.equals('y')){
            key = 65;
        }
        if (keyChar.equals('u')){
            key = 66;
        }
        if (keyChar.equals('i')){
            key = 67;
        }
        if (keyChar.equals('o')){
            key = 68;
        }
        if (keyChar.equals('p')){
            key = 69;
        }
        if (keyChar.equals('[')){
            key = 70;
        }
        if (keyChar.equals(']')){
            key = 71;
        }
        return key;
    }

    protected void sendKey(int command, int key, long time){
        try {
            ShortMessage shortMessage = new ShortMessage();
            shortMessage.setMessage(command, 0, key, 100);
            receiver.send(shortMessage, time);
        } catch (InvalidMidiDataException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Receiver getReceiver() {
        return receiver;
    }

    @Override
    public void close() {

    }
}
