package uicomponents.renderer.text;

import javax.swing.*;
import java.awt.*;

public class NoteTextRendererImp extends JComponent {
    private final JTextArea textArea;
    private final Object keyboardState;

    public NoteTextRendererImp(Object keyboardState){
        this.keyboardState = keyboardState;
        this.textArea = new JTextArea();

        this.setLayout(new BorderLayout());
        this.add(BorderLayout.CENTER, textArea);
    }

    @Override
    public void repaint(){
        textArea.setText(keyboardState.toString());
    }
}
