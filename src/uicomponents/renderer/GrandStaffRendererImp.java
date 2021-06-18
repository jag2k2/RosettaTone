package uicomponents.renderer;

import instrument.KeyChangeObserver;
import music.*;
import music.Staff;

import java.awt.*;
import javax.swing.*;
import java.io.File;

public class GrandStaffRendererImp extends Component implements KeyChangeObserver {
    private static final int canvasWidth = 750;
    private static final int canvasHeight = 750;

    private final NoteState noteState;
    private final JTextArea textArea;
    private final StaffRenderer trebleRenderer;
    private final StaffRenderer bassRenderer;


    public GrandStaffRendererImp(NoteState noteState, JTextArea texArea){
        this.noteState = noteState;
        this.textArea = texArea;

        File trebleClefFile = new File("./Images/Treble-clef.png");
        File bassClefFile = new File("./Images/Bass-clef.png");

        Staff trebleStaff = new Staff(trebleClefFile, 0.5, 15, 3, 0,18, 26);
        Staff bassStaff = new Staff(bassClefFile, 0.4, 30, 0, 50,30, 38);

        trebleRenderer = new StaffRenderer(trebleStaff);
        bassRenderer = new StaffRenderer(bassStaff);
    }

    @Override
    public void update() {
        textArea.setText(noteState.getActiveNotes(NoteClef.Treble).toString());
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(canvasWidth, canvasHeight);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;
        paintBackground(graphics2D);
        trebleRenderer.drawStaff(graphics2D);
        bassRenderer.drawStaff(graphics2D);
        trebleRenderer.drawNotes(graphics2D, noteState.getActiveNotes(NoteClef.Treble));
        bassRenderer.drawNotes(graphics2D, noteState.getActiveNotes(NoteClef.Bass));
    }

    protected void paintBackground(Graphics2D graphics2D){
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0,0,canvasWidth,canvasHeight);
    }
}
