package instrument;

import statemodels.KeyboardState;

import javax.sound.midi.*;

public class KeyNoteReceiverImp implements Receiver {
    private final KeyboardState keyboardState;

    public KeyNoteReceiverImp(KeyboardState keyboardState){
        this.keyboardState = keyboardState;
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        if(message instanceof ShortMessage) {
            ShortMessage sm = (ShortMessage) message;
            if (sm.getCommand() == ShortMessage.NOTE_ON) {
                Key key = new Key(sm.getData1());
                int velocity = sm.getData2();
                if (velocity > 0){
                    keyboardState.keyPressed(key);
                }
                else {
                    keyboardState.keyReleased(key);
                }
            }
            else if (sm.getCommand() == ShortMessage.NOTE_OFF) {
                Key key = new Key(sm.getData1());
                int velocity = sm.getData2();
                keyboardState.keyReleased(key);
            }
        }
    }
    @Override
    public void close() {}
}
