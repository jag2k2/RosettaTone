package collections;

import instrument.Key;
import music.Note;
import music.NoteAccidental;
import music.NoteName;
import utility.NoteCollection;

import java.util.HashSet;
import java.util.Set;

public class NoteCollectionFactory {
        public static NoteCollection createFromKeys(Set<Key> keys){
            NoteCollectionImp pressedNotes = new NoteCollectionImp();
            for (Key key : keys){
                Set<NoteAccidental> accidentals = new HashSet<>();
                if (key.isNatural()){
                    accidentals.add(NoteAccidental.NATURAL);
                    if (key.sharpExistsAlso(keys)){
                        accidentals.add(NoteAccidental.SHARP);
                    }
                } else {
                    accidentals.add(NoteAccidental.SHARP);
                    if (key.naturalExistsAlso(keys)){
                        accidentals.add(NoteAccidental.NATURAL);
                    }
                }
                Note noteToAdd = new Note(NoteName.values()[key.getNaturalIndex()], key.getOctave(), accidentals);
                pressedNotes.add(noteToAdd);
            }
            return pressedNotes;
        }
}
