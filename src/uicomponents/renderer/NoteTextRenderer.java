package uicomponents.renderer;

import notification.KeyboardChangeObserver;
import uicomponents.UIComponent;

import javax.swing.*;
import java.awt.*;

public class NoteTextRenderer implements UIComponent, KeyboardChangeObserver {
    private final JTextArea textArea;
    private final Object keyboardState;

    public NoteTextRenderer(Object keyboardState){
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
    public void boardChangedWithKeyDown() {
        textArea.setText(keyboardState.toString());
    }

    @Override
    public void boardChangedWithKeyUp() {
        textArea.setText(keyboardState.toString());
    }
}
