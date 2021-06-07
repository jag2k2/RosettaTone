package music;

import java.util.ArrayList;
import java.util.List;

public class KeyboardImp implements Keyboard{
    int octaves = 9;
    int naturalsPerOctave = 7;
    private final Note[][] keyboardNotes = new Note[octaves][naturalsPerOctave];

    public KeyboardImp(){
        NoteName[] noteNames = NoteName.values();
        for (int octave = 0; octave < octaves; octave++){
            for(int octaveNote = 0; octaveNote < naturalsPerOctave; octaveNote++){
                keyboardNotes[octave][octaveNote] = new Note(noteNames[octaveNote], octave);
            }
        }
    }

    @Override
    public void pressKey(Key key) {
        changeNoteState(key, true);
    }

    @Override
    public void releaseKey(Key key) {
        changeNoteState(key, false);
    }

    protected void changeNoteState(Key key, boolean active){
        int octave = key.getOctave();
        int naturalIndex = key.getNaturalIndex();
        if (key.isCDorE()){
            if (key.isEven()){
                keyboardNotes[octave][naturalIndex].setAccidental(NoteAccidental.NATURAL, active);
            }
            else{
                keyboardNotes[octave][naturalIndex].setAccidental(NoteAccidental.SHARP, active);
            }
        }
        else{
            if (key.isEven()){
                keyboardNotes[octave][naturalIndex].setAccidental(NoteAccidental.SHARP, active);
            }
            else{
                keyboardNotes[octave][naturalIndex].setAccidental(NoteAccidental.NATURAL, active);
            }
        }
    }

    @Override
    public List<Note> getPressedNotes() {
        List<Note> pressedNotes = new ArrayList<>();
        for (Note[] notes : keyboardNotes){
            for(Note note : notes){
                if (note.isActive()){
                    pressedNotes.add(note);
                }
            }
        }
        return pressedNotes;
    }
}
