package instrument;

import statemodels.NoteState;
import notification.StaffChangeNotifier;

import javax.sound.midi.*;

public class KeyNoteReceiverImp implements Receiver {
    private final NoteState noteState;
    private final StaffChangeNotifier staffChangeNotifier;

    public KeyNoteReceiverImp(NoteState noteState, StaffChangeNotifier staffChangeNotifier){
        this.noteState = noteState;
        this.staffChangeNotifier = staffChangeNotifier;
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
                staffChangeNotifier.notifyObservers();
            }
            else if (sm.getCommand() == ShortMessage.NOTE_OFF) {
                Key key = new Key(sm.getData1());
                int velocity = sm.getData2();
                noteState.NoteOff(key);
                staffChangeNotifier.notifyObservers();
            }
        }
    }
    @Override
    public void close() {}
}
