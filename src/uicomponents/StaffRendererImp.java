package uicomponents;

import instrument.KeyChangeObserver;
import imageprocessing.StaffImage;
import music.*;

import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import javax.imageio.*;

public class StaffRendererImp extends Component implements KeyChangeObserver {
    private static final int numberOfLines = 40;
    private static final int leftMargin = 100;
    private static final int lineSpacing = 15;

    private final NoteState noteState;
    private final JTextArea textArea;
    private StaffImage trebleClefImage;
    private StaffImage bassClefImage;
    private StaffImage noteImage;

    public StaffRendererImp(NoteState noteState, JTextArea texArea){
        this.noteState = noteState;
        this.textArea = texArea;

        File trebleClefFile = new File("./Images/Treble-clef.png");
        File bassClefFile = new File("./Images/Bass-clef.png");
        File noteFile = new File("./Images/Whole-Note.png");

        try {
            trebleClefImage = new StaffImage(ImageIO.read(trebleClefFile));
            bassClefImage = new StaffImage(ImageIO.read(bassClefFile));
            noteImage = new StaffImage(ImageIO.read(noteFile));
            trebleClefImage.resize(0.5);
            bassClefImage.resize(0.4);
            noteImage.resize(0.22);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void update() {
        textArea.setText(noteState.getActiveNotes().toString());
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        int preferredWidth = 750;
        int preferredHeight = 750;
        return new Dimension(preferredWidth, preferredHeight);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;
        paintBackground(graphics2D);
        paintTrebleClef(graphics2D);
        paintBassClef(graphics2D);
        paintLines(graphics2D);
        paintNotes(graphics2D);
    }

    protected void paintBackground(Graphics2D graphics2D){
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0,0,750,750);
    }

    protected void paintTrebleClef(Graphics2D graphics2D){
        int trebleYPosition = lineSpacing * 6 - 12;
        graphics2D.drawImage(trebleClefImage.getBufferedImage(), null, leftMargin, trebleYPosition);
    }

    protected void paintBassClef(Graphics2D graphics2D){
        int bassYPosition = lineSpacing * 24;
        graphics2D.drawImage(bassClefImage.getBufferedImage(), null, leftMargin, bassYPosition);
    }

    protected void paintLines(Graphics2D graphics2D){
        graphics2D.setColor(Color.BLACK);
        int lineThickness = 2;
        graphics2D.setStroke(new BasicStroke(lineThickness));

        Integer[] visibleLines = {8,10,12,14,16,24,26,28,30,32};
        for (int i = 0; i < numberOfLines; i++){
            int linePosition = i * lineSpacing;
            if(Arrays.asList(visibleLines).contains(i)) {
                int lineLength = 500;
                graphics2D.drawLine(leftMargin, linePosition, leftMargin + lineLength, linePosition);
            }
        }
    }

    protected void paintNotes(Graphics2D graphics2D){
        ActiveNotes activeNotes = noteState.getActiveNotes();
        int quarterWidth = noteImage.getBufferedImage().getWidth();
        for (Note note : activeNotes){
            int lineNumber = numberOfLines - NoteRenderer.calcLineNumber(note);
            int noteY = (lineNumber - 3) * lineSpacing - 1;
            int noteX = leftMargin + 200;
            if (activeNotes.isShifted(note)) {
                noteX += quarterWidth;
            }
            graphics2D.drawImage(noteImage.getBufferedImage(), null, noteX, noteY);
        }
    }
}
