package statemodels;

import instrument.Key;
import music.*;
import notification.KeyboardChangeNotifier;
import notification.KeyboardChangeObserver;

import java.util.ArrayList;

public class KeyboardStateImp implements KeyboardState, KeyboardChangeNotifier {
    private final int octaves = 9;
    private final int naturalsPerOctave = 7;
    private final Note[][] Notes = new Note[octaves][naturalsPerOctave];
    private final ArrayList<KeyboardChangeObserver> keyboardChangeObservers;

    public KeyboardStateImp(){
        this.keyboardChangeObservers = new ArrayList<>();
        NoteName[] noteNames = NoteName.values();
        for (int octave = 0; octave < octaves; octave++){
            for(int octaveNote = 0; octaveNote < naturalsPerOctave; octaveNote++){
                Notes[octave][octaveNote] = new Note(noteNames[octaveNote], octave);
            }
        }
    }

    @Override
    public void KeyPressed(Key key) {
        changeNoteState(key, true);
        notifyObservers();
    }

    @Override
    public void KeyReleased(Key key) {
        changeNoteState(key, false);
        notifyObservers();
    }

    protected void changeNoteState(Key key, boolean active){
        int octave = key.getOctave();
        int naturalIndex = key.getNaturalIndex();
        if (key.isCDorE()){
            if (key.isEven()){
                Notes[octave][naturalIndex].setAccidental(NoteAccidental.NATURAL, active);
            }
            else{
                Notes[octave][naturalIndex].setAccidental(NoteAccidental.SHARP, active);
            }
        }
        else{
            if (key.isEven()){
                Notes[octave][naturalIndex].setAccidental(NoteAccidental.SHARP, active);
            }
            else{
                Notes[octave][naturalIndex].setAccidental(NoteAccidental.NATURAL, active);
            }
        }
    }

    @Override
    public NoteList getActiveNotes() {
        NoteList pressedNotes = new NoteListImp();
        for (Note[] notes : Notes){
            for(Note note : notes){
                if (note.isActive()){
                    pressedNotes.add(note);
                }
            }
        }
        return pressedNotes;
    }

    @Override
    public void addObserver(KeyboardChangeObserver observer) {
        keyboardChangeObservers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (KeyboardChangeObserver keyboardChangeObserver : keyboardChangeObservers) {
            keyboardChangeObserver.keyboardChanged();
        }
    }
}
