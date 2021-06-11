package music;

import instrument.Key;

import java.util.ArrayList;
import java.util.List;

public class NoteStateImp implements NoteState {
    private final int octaves = 9;
    private final int naturalsPerOctave = 7;
    private final Note[][] Notes = new Note[octaves][naturalsPerOctave];

    public NoteStateImp(){
        NoteName[] noteNames = NoteName.values();
        for (int octave = 0; octave < octaves; octave++){
            for(int octaveNote = 0; octaveNote < naturalsPerOctave; octaveNote++){
                Notes[octave][octaveNote] = new Note(noteNames[octaveNote], octave);
            }
        }
    }

    @Override
    public void NoteOn(Key key) {
        changeNoteState(key, true);
    }

    @Override
    public void NoteOff(Key key) {
        changeNoteState(key, false);
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
    public List<Note> getActiveNotes() {
        List<Note> pressedNotes = new ArrayList<>();
        for (Note[] notes : Notes){
            for(Note note : notes){
                if (note.isActive()){
                    pressedNotes.add(note);
                }
            }
        }
        return pressedNotes;
    }
}