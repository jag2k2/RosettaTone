package statemodels;

import instrument.Key;
import music.*;
import notification.KeyboardChangeNotifier;
import notification.KeyboardChangeObserver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class KeyboardStateImp implements KeyboardState, KeyboardChangeNotifier {
    private final Set<Key> keys;
    private final ArrayList<KeyboardChangeObserver> keyboardChangeObservers;

    public KeyboardStateImp(){
        this.keyboardChangeObservers = new ArrayList<>();
        this.keys = new HashSet<>();
    }

    @Override
    public void KeyPressed(Key key) {
        keys.add(key);
        notifyObservers();
    }

    @Override
    public void KeyReleased(Key key) {
        keys.remove(key);
        notifyObservers();
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
        Key nextKey = key.getNext();
        return keys.contains(nextKey);
    }

    protected boolean naturalExistsAlso(Key key){
        Key previousKey = key.getPrevious();
        return keys.contains(previousKey);
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
