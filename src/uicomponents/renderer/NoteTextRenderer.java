package uicomponents.renderer;

import notification.KeyboardChangeObserver;
import uicomponents.UIComponent;

import javax.swing.*;
import java.awt.*;

public class NoteTextRenderer implements UIComponent, KeyboardChangeObserver {
    private final JTextArea textArea;
    private final KeyboardStateNoteGetter keyboardStateNoteGetter;

    public NoteTextRenderer(KeyboardStateNoteGetter keyboardStateNoteGetter){
        this.keyboardStateNoteGetter = keyboardStateNoteGetter;
        this.textArea = new JTextArea();

    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(BorderLayout.CENTER, textArea);
        return panel;
    }

    @Override
    public void notifyKeyboardChanged() {
        textArea.setText(keyboardStateNoteGetter.getActiveNotes().toString());
    }
}
