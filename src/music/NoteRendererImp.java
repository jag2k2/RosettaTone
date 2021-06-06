package music;

import java.awt.*;
import javax.swing.*;

public class NoteRendererImp implements NoteRenderer{
    Canvas canvas;
    JTextArea textArea;

    public NoteRendererImp(Canvas canvas, JTextArea texArea){
        this.canvas = canvas;
        this.textArea = texArea;
    }

    public void drawNote(Note note){
        textArea.append(note + "\n");
    }
}
