package statemodels;

import collections.NoteCollectionFactory;
import instrument.Key;
import instrument.KeyStateManipulator;
import music.Note;
import trainer.KeyStateEvaluator;
import uicomponents.renderer.KeyStateDrawable;
import uicomponents.renderer.StaffModeDrawable;
import uicomponents.renderer.records.RenderConstants;
import utility.NoteCollection;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class KeyboardStateImp implements KeyStateManipulator, KeyStateEvaluator, KeyStateDrawable {
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
    public boolean containsAll(NoteCollection noteCollection) {
        NoteCollection activeNotes = NoteCollectionFactory.createFromKeys(keys);
        return activeNotes.containsAll(noteCollection);
    }

    @Override
    public void draw(Graphics2D graphics2D, BufferedImage noteImage, BufferedImage sharpImage, BufferedImage naturalImage, BufferedImage flatImage, StaffModeDrawable staffMode) {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        NoteCollection activeNotes = NoteCollectionFactory.createFromKeys(keys);
        for (Note note : activeNotes){
            int lineNumber = RenderConstants.getLineNumber(note);
            int xPos = RenderConstants.getNoteXOffset(1);
            note.draw(graphics2D, noteImage, sharpImage, naturalImage, flatImage, activeNotes, xPos, staffMode.getLedgerLines(lineNumber));
        }
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
        StringBuilder outputString = new StringBuilder();
        for (Key key : keys){
            outputString.append(key.getNote().toString());
            outputString.append(", ");
        }
        return outputString.toString();
    }
}
