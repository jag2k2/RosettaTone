package uicomponents.renderer;

import notification.KeyboardChangeObserver;
import statemodels.KeyboardState;
import uicomponents.UIComponent;

import javax.swing.*;
import java.awt.*;

public class NoteTextRenderer implements UIComponent, KeyboardChangeObserver {
    private final JTextArea textArea;
    private final KeyboardState keyboardState;

    public NoteTextRenderer(KeyboardState keyboardState){
        this.keyboardState = keyboardState;
        this.textArea = new JTextArea();

    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(BorderLayout.CENTER, textArea);
        return panel;
    }

    @Override
    public void keyboardChanged() {
        textArea.setText(keyboardState.getActiveNotes().toString());
    }
}
