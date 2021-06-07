package music;

import imageprocessing.ResizableImage;

import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.*;

public class NoteRendererImp extends Component implements NoteRenderer{
    JTextArea textArea;
    int preferredWidth = 750;
    int preferredHeight = 750;
    int numberOfLines = 20;
    int lineSpacing = 30;
    int lineThickness = 2;
    int lineLength = 500;
    int trebleYPosition = lineSpacing * 3 - 12;
    int bassYPosition = lineSpacing * 12;
    int leftMargin = 100;
    List<Note> staffNotes = new ArrayList<>();


    public NoteRendererImp(JTextArea texArea){
        this.textArea = texArea;
        for (int i = 0; i < 88; i++){

        }
    }

    public void drawNote(Note note){
        textArea.append(note + "\n");
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(preferredWidth, preferredHeight);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;
        try {
            File trebleClefFile = new File("./Images/Treble-clef.jpg");
            File bassClefFile = new File("./Images/Bass-clef.jpg");
            File quarterNoteFile = new File("./Images/Quarter-Note.jpg");

            ResizableImage trebleClefImage = new ResizableImage(ImageIO.read(trebleClefFile));
            ResizableImage bassClefImage = new ResizableImage(ImageIO.read(bassClefFile));
            ResizableImage quarterNoteImage = new ResizableImage(ImageIO.read(quarterNoteFile));
            trebleClefImage.resize(0.5);
            bassClefImage.resize(0.4);
            quarterNoteImage.resize(0.2);

            graphics2D.setColor(Color.WHITE);
            graphics2D.fillRect(0,0,750,750);

            graphics2D.drawImage(trebleClefImage.getBufferedImage(), null, leftMargin, trebleYPosition);
            graphics2D.drawImage(bassClefImage.getBufferedImage(), null, leftMargin, bassYPosition);
            graphics2D.drawImage(quarterNoteImage.getBufferedImage(), null, leftMargin + 100, 50);

            graphics2D.setColor(Color.BLACK);
            graphics2D.setStroke(new BasicStroke(lineThickness));

            for (int i = 0; i < numberOfLines; i++){
                int linePosition = i*lineSpacing;
                //if
                graphics2D.drawLine(leftMargin, linePosition, leftMargin + lineLength, linePosition);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
