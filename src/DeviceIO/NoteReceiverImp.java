package DeviceIO;

import music.Note;

import javax.sound.midi.*;
import javax.swing.*;

public class NoteReceiverImp implements Receiver {
    JTextArea textArea;

    public NoteReceiverImp(JTextArea textArea){
        this.textArea = textArea;
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        if(message instanceof ShortMessage) {
            ShortMessage sm = (ShortMessage) message;
            if (sm.getCommand() == ShortMessage.NOTE_ON) {
                int key = sm.getData1();
                int velocity = sm.getData2();
                Note note = new Note(key);
                textArea.append("On " + note + " " + velocity + "\n");
                //System.out.println("On " + note + " " + velocity);
            } else {
                textArea.append("Command: " + sm.getCommand() + "\n");
                //System.out.println("Command:" + sm.getCommand());
            }
        }
    }
    @Override
    public void close() {}
}
