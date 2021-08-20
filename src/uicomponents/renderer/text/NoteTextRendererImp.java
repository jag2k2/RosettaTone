package uicomponents.renderer.text;

import notification.KeyboardChangeObserver;
import uicomponents.UIComponent;

import javax.swing.*;
import java.awt.*;

public class NoteTextRendererImp implements UIComponent, KeyboardChangeObserver {
    private final JTextArea textArea;
    private final Object keyboardState;

    public NoteTextRendererImp(Object keyboardState){
        this.keyboardState = keyboardState;
        this.textArea = new JTextArea();

    }

    @Override
    public Component makeComponent() {
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
