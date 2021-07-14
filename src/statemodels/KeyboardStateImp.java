package statemodels;

import collections.NoteSetImp;
import instrument.Key;
import instrument.KeyStateManipulator;
import music.Note;
import music.NoteAccidental;
import trainer.KeyStateEvaluator;
import uicomponents.renderer.KeyStateDrawable;
import uicomponents.renderer.StaffModeDrawable;
import uicomponents.renderer.records.RenderConstants;
import utility.Maybe;
import utility.NoteSet;

import java.awt.*;
import java.awt.image.BufferedImage;
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
            notifier.notifyKeyboardChanged();
        }
    }

    @Override
    public void keyReleased(Key key) {
        keys.remove(key);
        for (KeyboardChangeNotifier notifier : keyboardChangeNotifier){
            notifier.notifyKeyboardChanged();
        }
    }

    @Override
    public boolean containsAll(NoteSet noteSet) {
        NoteSet activeNotes = convertToNotes();
        return activeNotes.containsAll(noteSet);
    }

    @Override
    public void draw(Graphics2D graphics2D, BufferedImage noteImage, BufferedImage sharpImage, BufferedImage naturalImage, BufferedImage flatImage, StaffModeDrawable staffMode) {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        NoteSet activeNotes = convertToNotes();
        for (Note note : activeNotes){
            int lineNumber = RenderConstants.getLineNumber(note);
            int xPos = RenderConstants.getNoteXOffset(1);
            note.draw(graphics2D, noteImage, sharpImage, naturalImage, flatImage, activeNotes, xPos, staffMode.getLedgerLines(lineNumber));
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
