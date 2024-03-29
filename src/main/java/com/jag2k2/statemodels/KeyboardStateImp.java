package com.jag2k2.statemodels;

import com.jag2k2.collections.NoteSetImp;
import com.jag2k2.instrument.Key;
import com.jag2k2.instrument.KeyStateManipulator;
import com.jag2k2.music.Note;
import com.jag2k2.trainer.KeyStateEvaluator;
import com.jag2k2.uicomponents.renderer.grandstaff.KeyStateDrawable;
import com.jag2k2.uicomponents.renderer.grandstaff.StaffModeDrawable;
import com.jag2k2.uicomponents.renderer.records.NoteImages;
import com.jag2k2.uicomponents.renderer.records.RenderConstants;
import com.jag2k2.utility.Maybe;
import com.jag2k2.utility.NoteSet;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

public class KeyboardStateImp implements KeyStateManipulator, KeyStateEvaluator, KeyStateDrawable {
    private final Set<Key> keys;
    private Maybe<KeyboardChangeNotifier> keyboardChangeNotifier = new Maybe<>();

    public KeyboardStateImp(){
        this.keys = new HashSet<>();
    }

    public void addKeyboardChangeNotifier(KeyboardChangeNotifier keyboardChangeNotifier){
        this.keyboardChangeNotifier = new Maybe<>(keyboardChangeNotifier);
    }

    @Override
    public void keyPressed(Key key) {
        keys.add(key);
        for (KeyboardChangeNotifier notifier : keyboardChangeNotifier){
            notifier.notifyKeyDown();
        }
    }

    @Override
    public void keyReleased(Key key) {
        keys.remove(key);
        for (KeyboardChangeNotifier notifier : keyboardChangeNotifier){
            notifier.notifyKeyUp();
        }
    }

    @Override
    public boolean containsAll(NoteSet noteSet) {
        NoteSet activeNotes = convertToNotes();
        return activeNotes.containsAll(noteSet);
    }

    @Override
    public void draw(Graphics2D graphics2D, NoteImages noteImages, StaffModeDrawable staffMode) {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        NoteSet activeNotes = convertToNotes();
        for (Note note : activeNotes){
            int lineNumber = note.getLineNumber();
            int xPos = RenderConstants.getNoteXOffset(0);
            note.draw(graphics2D, noteImages, activeNotes, xPos, staffMode.getLedgerLines(lineNumber), false);
        }
    }

    protected NoteSet convertToNotes(){
        NoteSetImp pressedNotes = new NoteSetImp();
        for (Key key : keys){
            Note noteToAdd = key.convertToNote();
            pressedNotes.add(noteToAdd);
        }
        return pressedNotes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof KeyboardStateImp){
            KeyboardStateImp compare = (KeyboardStateImp) obj;
            return keys.equals(compare.keys);
        }
        return false;
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (Key key : keys){
            stringJoiner.add(key.convertToNote().toString());
        }
        return stringJoiner.toString();
    }
}
