package instrument;

import instrument.key.Key;

import javax.sound.midi.*;

public class MidiReceiverImp implements Receiver {
    private final KeyStateManipulator keyStateManipulator;

    public MidiReceiverImp(KeyStateManipulator keyStateManipulator){
        this.keyStateManipulator = keyStateManipulator;
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        if(message instanceof ShortMessage) {
            ShortMessage sm = (ShortMessage) message;
            if (sm.getCommand() == ShortMessage.NOTE_ON) {
                Key key = new Key(sm.getData1());
                int velocity = sm.getData2();
                if (velocity > 0){
                    keyStateManipulator.keyPressed(key);
                }
                else {
                    keyStateManipulator.keyReleased(key);
                }
            }
            else if (sm.getCommand() == ShortMessage.NOTE_OFF) {
                Key key = new Key(sm.getData1());
                int velocity = sm.getData2();
                keyStateManipulator.keyReleased(key);
            }
        }
    }
    @Override
    public void close() {}
}
