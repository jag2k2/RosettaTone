package com.jag2k2.instrument.simluated;

import javax.sound.midi.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MidiTransmitterSimImp implements KeyListener, Transmitter {
    private Receiver receiver;

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

        if (keyChar.equals('!')){
            key = 36;
        }
        if (keyChar.equals('@')){
            key = 37;
        }
        if (keyChar.equals('#')){
            key = 38;
        }
        if (keyChar.equals('$')){
            key = 39;
        }
        if (keyChar.equals('%')){
            key = 40;
        }
        if (keyChar.equals('^')){
            key = 41;
        }
        if (keyChar.equals('&')){
            key = 42;
        }
        if (keyChar.equals('*')){
            key = 43;
        }
        if (keyChar.equals('(')){
            key = 44;
        }
        if (keyChar.equals(')')){
            key = 45;
        }
        if (keyChar.equals('_')){
            key = 46;
        }
        if (keyChar.equals('+')){
            key = 47;
        }

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

        if (keyChar.equals('1')){
            key = 72;
        }
        if (keyChar.equals('2')){
            key = 73;
        }
        if (keyChar.equals('3')){
            key = 74;
        }
        if (keyChar.equals('4')){
            key = 75;
        }
        if (keyChar.equals('5')){
            key = 76;
        }
        if (keyChar.equals('6')){
            key = 77;
        }
        if (keyChar.equals('7')){
            key = 78;
        }
        if (keyChar.equals('8')){
            key = 79;
        }
        if (keyChar.equals('9')){
            key = 80;
        }
        if (keyChar.equals('0')){
            key = 81;
        }
        if (keyChar.equals('-')){
            key = 82;
        }
        if (keyChar.equals('=')){
            key = 83;
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
}
