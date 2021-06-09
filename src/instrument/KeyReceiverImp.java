package instrument;

import music.NoteState;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;

public class KeyReceiverImp implements Receiver, KeyChangeNotifier {
    private final NoteState noteState;
    private final List<KeyChangeObserver> keyChangeObservers;

    public KeyReceiverImp(NoteState noteState){
        this.noteState = noteState;
        this.keyChangeObservers = new ArrayList<>();
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        if(message instanceof ShortMessage) {
            ShortMessage sm = (ShortMessage) message;
            if (sm.getCommand() == ShortMessage.NOTE_ON) {
                Key key = new Key(sm.getData1());
                int velocity = sm.getData2();
                if (velocity > 0){
                    noteState.NoteOn(key);
                }
                else {
                    noteState.NoteOff(key);
                }
                keyNoteChange();
            }
            else if (sm.getCommand() == ShortMessage.NOTE_OFF) {
                Key key = new Key(sm.getData1());
                int velocity = sm.getData2();
                noteState.NoteOff(key);
                keyNoteChange();
            }
        }
    }
    @Override
    public void close() {}

    @Override
    public void addKeyChangeObserver(KeyChangeObserver keyChangeObserver) {
        keyChangeObservers.add(keyChangeObserver);
    }

    @Override
    public void keyNoteChange() {
        for (KeyChangeObserver keyChangeObserver : keyChangeObservers) {
            keyChangeObserver.update();
        }
    }
}
