package instrument;

import statemodels.NoteState;
import notification.StaffChangeNotifier;

import javax.sound.midi.*;

public class KeyNoteReceiverImp implements Receiver {
    private final NoteState noteState;

    public KeyNoteReceiverImp(NoteState noteState){
        this.noteState = noteState;
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
            }
            else if (sm.getCommand() == ShortMessage.NOTE_OFF) {
                Key key = new Key(sm.getData1());
                int velocity = sm.getData2();
                noteState.NoteOff(key);
            }
        }
    }
    @Override
    public void close() {}
}
