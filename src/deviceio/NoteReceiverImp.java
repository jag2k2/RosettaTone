package deviceio;

import music.Note;
import music.NoteRenderer;
import javax.sound.midi.*;

public class NoteReceiverImp implements Receiver {
    NoteRenderer noteRenderer;

    public NoteReceiverImp(NoteRenderer noteRenderer){
        this.noteRenderer = noteRenderer;
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        if(message instanceof ShortMessage) {
            ShortMessage sm = (ShortMessage) message;
            if (sm.getCommand() == ShortMessage.NOTE_ON) {
                int key = sm.getData1();
                int velocity = sm.getData2();
                if (velocity > 0){
                    System.out.println("NoteOn: " + key + " " + velocity);
                }
                else {
                    System.out.println("NoteOff: " + key + " " + velocity);
                }
                //noteRenderer.drawNote(note);
            }
            else if (sm.getCommand() == ShortMessage.NOTE_OFF) {
                int key = sm.getData1();
                int velocity = sm.getData2();
                System.out.println("NoteOff: " + key + " " + velocity);
                //noteRenderer.drawNote(note);
            }
        }
    }
    @Override
    public void close() {}
}
