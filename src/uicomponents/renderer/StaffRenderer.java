package uicomponents.renderer;

import imageprocessing.StaffImage;
import music.ActiveNotes;
import music.Note;
import music.NoteAccidental;
import music.Staff;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class StaffRenderer {
    private static final int numberOfLines = 52;
    private static final int lineSpacing = 15;
    private static final int leftMargin = 100;
    private static final int noteXSpacing = 200;

    private final Staff staff;
    private final StaffImage staffImage;

    private final StaffImage noteImage;
    private final StaffImage naturalImage;
    private final StaffImage sharpImage;
    private final StaffImage flatImage;

    public StaffRenderer(Staff staff){
        this.staff = staff;
        this.staffImage = new StaffImage(staff.getImageFile(), staff.getScaleFactor());

        File noteFile = new File("./Images/Whole-Note.png");
        File naturalFile = new File("./Images/Natural.png");
        File sharpFile = new File("./Images/Sharp.png");
        File flatFile = new File("./Images/Flat.png");

        noteImage = new StaffImage(noteFile, 0.22);
        naturalImage = new StaffImage(naturalFile, 0.4);
        sharpImage = new StaffImage(sharpFile, 0.4);
        flatImage = new StaffImage(flatFile, 0.4);
    }

    public void drawStaff(Graphics2D graphics2D){
        graphics2D.drawImage(staffImage.getBufferedImage(), null, leftMargin, staff.getClefYOffset(lineSpacing));

        configLineDraw(graphics2D);
        int lineLength = 500;
        for (int i = 0; i < numberOfLines; i++){
            if ((staff.getTopVisibleLine() <= i) && (i <= staff.getBottomVisibleLine()) && ((i % 2) == 0)){
                int linePosition =  i * lineSpacing + staff.getStaffYOffset();
                graphics2D.drawLine(leftMargin, linePosition, leftMargin + lineLength, linePosition);
            }
        }
    }

    protected void configLineDraw(Graphics2D graphics2D){
        graphics2D.setColor(Color.BLACK);
        int lineThickness = 2;
        graphics2D.setStroke(new BasicStroke(lineThickness));
    }

    protected void drawNotes(Graphics2D graphics2D, ActiveNotes activeNotes){
        for (Note note : activeNotes){
            drawNote(graphics2D, note, activeNotes);
            drawAccidentals(graphics2D, note);
            drawHelperLines(graphics2D, note);
        }
    }

    protected void drawNote(Graphics2D graphics2D, Note note, ActiveNotes activeNotes){
        int lineNumber = numberOfLines - note.getLineNumber() - 1;
        int noteX = leftMargin + noteXSpacing;
        int noteHeight = noteImage.getBufferedImage().getHeight();
        int noteY = (lineNumber * lineSpacing) - (noteHeight / 2) + staff.getStaffYOffset();

        int noteWidth = noteImage.getBufferedImage().getWidth();
        if (activeNotes.isShifted(note)) {
            noteX += noteWidth;
        }
        graphics2D.drawImage(noteImage.getBufferedImage(), null, noteX, noteY);
    }


    protected void drawAccidentals(Graphics2D graphics2D, Note note) {

        int lineNumber = numberOfLines - note.getLineNumber() - 1;
        int noteX = leftMargin + noteXSpacing;
        int noteHeight = noteImage.getBufferedImage().getHeight();
        int noteY = (lineNumber * lineSpacing) - (noteHeight / 2) + staff.getStaffYOffset();
        for (NoteAccidental accidental : note.getActiveAccidentals()) {
            if (accidental == NoteAccidental.SHARP) {
                BufferedImage sharpBufferedImage = sharpImage.getBufferedImage();
                int sharpXPos = noteX - (int) (sharpBufferedImage.getWidth() * 1.3);
                int sharpYPos = noteY - (sharpBufferedImage.getHeight() / 3);
                graphics2D.drawImage(sharpBufferedImage, null, sharpXPos, sharpYPos);
            }
        }
    }

    protected void drawHelperLines(Graphics2D graphics2D, Note note){
        int lineNumber = numberOfLines - note.getLineNumber() - 1;

        configLineDraw(graphics2D);
        for (int i = lineNumber; i > staff.getTopVisibleLine(); i--){
            drawHelperLine(graphics2D, i);
        }
        for (int i = lineNumber; i < staff.getBottomVisibleLine(); i++){
            drawHelperLine(graphics2D, i);
        }
    }

    protected void drawHelperLine(Graphics2D graphics2D, int lineNumber){
        int noteX = leftMargin + noteXSpacing;
        int helperLineYPos = (lineSpacing * lineNumber) + staff.getStaffYOffset();
        if ((lineNumber % 2) == 0) {
            graphics2D.drawLine(noteX - 2, helperLineYPos, noteX + 2 + noteImage.getBufferedImage().getWidth(), helperLineYPos);
        }
    }
}
