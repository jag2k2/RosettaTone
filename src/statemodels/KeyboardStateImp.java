package statemodels;

import instrument.Key;
import instrument.KeyStateManipulator;
import music.*;
import trainer.KeyboardEvaluator;
import uicomponents.renderer.KeyboardStateNoteGetter;
import utility.NoteCollection;

import java.util.HashSet;
import java.util.Set;

public class KeyboardStateImp implements KeyStateManipulator, KeyboardEvaluator, KeyboardStateNoteGetter {
    private final Set<Key> keys;
    private final KeyboardChangeNotifier keyboardChangeNotifier;

    public KeyboardStateImp(KeyboardChangeNotifier keyboardChangeNotifier){
        this.keyboardChangeNotifier = keyboardChangeNotifier;
        this.keys = new HashSet<>();
    }

    @Override
    public void keyPressed(Key key) {
        keys.add(key);
        keyboardChangeNotifier.notifyKeyboardChanged();
    }

    @Override
    public void keyReleased(Key key) {
        keys.remove(key);
        keyboardChangeNotifier.notifyKeyboardChanged();
    }

    @Override
    public boolean contains(NoteCollection noteCollection) {
        return getActiveNotes().contains(noteCollection);
    }

    @Override
    public NoteCollection getActiveNotes() {
        NoteCollection pressedNotes = new NoteCollectionImp();
        for (Key key : keys){
            Set<NoteAccidental> accidentals = new HashSet<>();
            if (key.isNatural()){
                accidentals.add(NoteAccidental.NATURAL);
                if (sharpExistsAlso(key)){
                    accidentals.add(NoteAccidental.SHARP);
                }
            } else {
                accidentals.add(NoteAccidental.SHARP);
                if (naturalExistsAlso(key)){
                    accidentals.add(NoteAccidental.NATURAL);
                }
            }
            Note noteToAdd = new Note(NoteName.values()[key.getNaturalIndex()], key.getOctave(), accidentals);
            pressedNotes.add(noteToAdd);
        }
        return pressedNotes;
    }

    protected boolean sharpExistsAlso(Key key){
        if (key.getNaturalIndex() == 2 || key.getNaturalIndex() == 6)
            return false;
        Key nextKey = key.getNext();
        return keys.contains(nextKey);
    }

    protected boolean naturalExistsAlso(Key key){
        Key previousKey = key.getPrevious();
        return keys.contains(previousKey);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof KeyboardStateImp){
            KeyboardStateImp compare = (KeyboardStateImp) obj;
            return keys.equals(compare.keys);
        }
        return false;
    }
}