package uicomponents;

import instrument.KeyChangeObserver;
import imageprocessing.ResizableImage;
import music.*;

import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import javax.imageio.*;

public class StaffRendererImp extends Component implements KeyChangeObserver {
    private final NoteState noteState;
    private final JTextArea textArea;

    public StaffRendererImp(NoteState noteState, JTextArea texArea){
        this.noteState = noteState;
        this.textArea = texArea;
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
        try {
            File trebleClefFile = new File("./Images/Treble-clef.jpg");
            File bassClefFile = new File("./Images/Bass-clef.jpg");
            File quarterNoteFile = new File("./Images/Quarter-Note.jpg");
            File invertedQuarterNoteFile = new File("./Images/Quarter-Note-Invt.jpg");

            ResizableImage trebleClefImage = new ResizableImage(ImageIO.read(trebleClefFile));
            ResizableImage bassClefImage = new ResizableImage(ImageIO.read(bassClefFile));
            ResizableImage quarterNoteImage = new ResizableImage(ImageIO.read(quarterNoteFile));
            ResizableImage invertedQuarterNoteImage = new ResizableImage((ImageIO.read(invertedQuarterNoteFile)));
            trebleClefImage.resize(0.5);
            bassClefImage.resize(0.4);
            quarterNoteImage.resize(0.21);
            invertedQuarterNoteImage.resize(0.21);

            graphics2D.setColor(Color.WHITE);
            graphics2D.fillRect(0,0,750,750);

            int numberOfLines = 40;
            int leftMargin = 100;
            int lineSpacing = 15;
            for (Note note : noteState.getActiveNotes()){
                for (NoteAccidental accidental : note.getActiveAccidentals()){
                    int lineNumber = numberOfLines - NoteRenderer.calcLineNumber(note);
                    //System.out.println(lineNumber);
                    if ((14 <= lineNumber && lineNumber <= 20) || (31 <= lineNumber && lineNumber <=40)){
                        int noteY = (lineNumber-9) * lineSpacing;
                        graphics2D.drawImage(quarterNoteImage.getBufferedImage(), null, leftMargin + 200, noteY-2);
                    } else {
                        int noteY = (lineNumber-3) * lineSpacing;
                        graphics2D.drawImage(invertedQuarterNoteImage.getBufferedImage(), null, leftMargin + 200, noteY);
                    }
                }
            }

            graphics2D.setColor(Color.BLACK);
            int lineThickness = 2;
            graphics2D.setStroke(new BasicStroke(lineThickness));

            int trebleYPosition = lineSpacing * 6 - 12;
            graphics2D.drawImage(trebleClefImage.getBufferedImage(), null, leftMargin, trebleYPosition);
            int bassYPosition = lineSpacing * 24;
            graphics2D.drawImage(bassClefImage.getBufferedImage(), null, leftMargin, bassYPosition);

            Integer[] visibleLines = {8,10,12,14,16,24,26,28,30,32};
            for (int i = 0; i < numberOfLines; i++){
                int linePosition = i * lineSpacing;
                if(Arrays.asList(visibleLines).contains(i)) {
                    int lineLength = 500;
                    graphics2D.drawLine(leftMargin, linePosition, leftMargin + lineLength, linePosition);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
