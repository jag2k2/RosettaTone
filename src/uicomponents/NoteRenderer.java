package uicomponents;

import music.Note;
import music.NoteName;

public class NoteRenderer {
    static public int calcLineNumber(Note note) {
        NoteName name = note.getName();
        int octave = note.getOctave();
        if (octave == 1){
            return name.getPosition() - 5;
        }
        if (octave == 2){
            return 2 + name.getPosition();
        }
        if (octave == 3){
            return 9 + name.getPosition();
        }
        if (octave == 4){
            return 16 + name.getPosition() + 4;
        }
        if (octave == 5){
            return 25 + name.getPosition() + 2;
        }
        if (octave == 6){
            return 36 + name.getPosition() - 2;
        }
        if (octave == 7){
            return 49 + name.getPosition() - 6;
        }
        return 0;
    }
}
