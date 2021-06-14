package uicomponents;

import imageprocessing.StaffImage;
import music.ActiveNotes;
import music.Note;
import music.NoteAccidental;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class StaffRenderer {
    private static final int numberOfLines = 52;
    private static final int lineSpacing = 15;
    private static final int leftMargin = 100;

    private final StaffInfo staffInfo;
    private final StaffImage staffImage;

    private final StaffImage noteImage;
    private final StaffImage naturalImage;
    private final StaffImage sharpImage;
    private final StaffImage flatImage;


    public StaffRenderer(StaffInfo staffInfo){
        this.staffInfo = staffInfo;
        this.staffImage = new StaffImage(staffInfo.getImageFile(), staffInfo.getScaleFactor());

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
        graphics2D.drawImage(staffImage.getBufferedImage(), null, leftMargin, staffInfo.getClefYOffset(lineSpacing));

        configLineDraw(graphics2D);
        int lineLength = 500;
        for (int i = 0; i < numberOfLines; i++){
            if ((staffInfo.getTopVisibleLine() <= i) && (i <= staffInfo.getBottomVisibleLine()) && ((i % 2) == 0)){
                int linePosition =  i * lineSpacing + staffInfo.getStaffYOffset();
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
            int lineNumber = numberOfLines - note.getLineNumber() - 1;
            System.out.println(lineNumber);
            int noteWidth = noteImage.getBufferedImage().getWidth();
            int noteHeight = noteImage.getBufferedImage().getHeight();
            int noteY = (lineNumber * lineSpacing) - (noteHeight / 2) + staffInfo.getStaffYOffset();
            int noteX = leftMargin + 200;

            if (activeNotes.isShifted(note)) {
                noteX += noteWidth;
            }
            graphics2D.drawImage(noteImage.getBufferedImage(), null, noteX, noteY);

            configLineDraw(graphics2D);
            int topVisibleLine = staffInfo.getTopVisibleLine();
            int bottomVisibleLine = staffInfo.getBottomVisibleLine();

                        if (lineNumber > bottomVisibleLine){
                System.out.println("Below " + bottomVisibleLine);
                for (int i = lineNumber; i > bottomVisibleLine; i--){
                    int helperLineYPos = (lineSpacing * i) + staffInfo.getStaffYOffset();
                    if ((i % 2) == 0) {
                        graphics2D.drawLine(noteX - 2, helperLineYPos, noteX + 2 + noteWidth, helperLineYPos);
                    }
                }
            } else if (lineNumber < topVisibleLine){
                System.out.println("Above " + topVisibleLine);
                for (int i = lineNumber; i < topVisibleLine; i++){
                    int helperLineYPos = (lineSpacing * i) + staffInfo.getStaffYOffset();
                    if ((i % 2) == 0) {
                        graphics2D.drawLine(noteX - 2, helperLineYPos, noteX + 2 + noteWidth, helperLineYPos);
                    }
                }
            }

            for (NoteAccidental accidental : note.getActiveAccidentals()){
                if (accidental == NoteAccidental.SHARP){
                    BufferedImage sharpBufferedImage = sharpImage.getBufferedImage();
                    int sharpXPos = noteX - (int)(sharpBufferedImage.getWidth() * 1.3);
                    int sharpYPos = noteY - (sharpBufferedImage.getHeight() / 3);
                    graphics2D.drawImage(sharpBufferedImage, null, sharpXPos, sharpYPos);
                }
            }
        }
    }

}
