package deviceio;

import music.Keyboard;
import music.Key;
import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;

public class NoteReceiverImp implements NoteReceiver{
    Keyboard keyboard;
    List<NoteChangeObserver> noteChangeObservers;

    public NoteReceiverImp(Keyboard keyboard){
        this.keyboard = keyboard;
        this.noteChangeObservers = new ArrayList<>();
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        if(message instanceof ShortMessage) {
            ShortMessage sm = (ShortMessage) message;
            if (sm.getCommand() == ShortMessage.NOTE_ON) {
                Key key = new Key(sm.getData1());
                int velocity = sm.getData2();
                if (velocity > 0){
                    keyboard.pressKey(key);
                }
                else {
                    keyboard.releaseKey(key);
                }
                notifyNoteChange();
            }
            else if (sm.getCommand() == ShortMessage.NOTE_OFF) {
                Key key = new Key(sm.getData1());
                int velocity = sm.getData2();
                System.out.println("NoteOff: " + key + " " + velocity);
                keyboard.releaseKey(key);
                notifyNoteChange();
            }
        }
    }
    @Override
    public void close() {}

    @Override
    public void addNoteChangeObserver(NoteChangeObserver noteChangeObserver) {
        noteChangeObservers.add(noteChangeObserver);
    }

    @Override
    public void notifyNoteChange() {
        for (NoteChangeObserver noteChangeObserver : noteChangeObservers) {
            noteChangeObserver.update();
        }
    }
}
