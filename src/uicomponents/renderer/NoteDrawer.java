package uicomponents.renderer;

import imageprocessing.StaffImage;
import music.*;
import utility.NoteCollection;
import trainer.NoteCollectionList;
import java.awt.*;
import java.awt.image.BufferedImage;

public class NoteDrawer {
    private final Graphics2D graphics2D;
    private final StaffDecorator staffDecorator;
    private final ImageLoader imageLoader;

    public NoteDrawer(Graphics2D graphics2D, StaffDecorator staffDecorator, ImageLoader imageLoader){
        this.graphics2D = graphics2D;
        this.staffDecorator = staffDecorator;
        this.imageLoader = imageLoader;

        graphics2D.setColor(Color.BLACK);
        int lineThickness = 2;
        graphics2D.setStroke(new BasicStroke(lineThickness));
    }

    public void drawEnabledStaffs(){
        for (Staff staff : staffDecorator.getEnabledStaffs()){
            drawClef(staff);
            drawStaffLines(staff);
        }
    }

    protected void drawClef(Staff staff){
        int clefImageXPos = RenderConstants.getClefXOffset();
        int clefImageYPos = staff.getClefYOffset();
        StaffImage staffImage = staff.createStaffImage();
        graphics2D.drawImage(staffImage.getBufferedImage(), null, clefImageXPos, clefImageYPos);
    }

    protected void drawStaffLines(Staff staff){
        int lineXPosStart = RenderConstants.getLineXStart();
        int lineXPosEnd = RenderConstants.getLineXEnd();
        for (int visibleLine : staff.getVisibleLines()) {
            int lineYPos = RenderConstants.getLineYOffset(visibleLine);
            graphics2D.drawLine(lineXPosStart, lineYPos, lineXPosEnd, lineYPos);
        }
    }

    public void drawKeyboardNotes(NoteCollection noteCollection){
        int noteX = RenderConstants.getNoteXOffset(1);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        drawNoteCollection(noteCollection, noteX, false);
    }

    public void drawFlashcardNotes(NoteCollectionList noteCollectionList, int xTraveled, boolean drawLeadingName){
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        int i = 0;
        for (NoteCollection noteCollection: noteCollectionList){
            boolean drawName = (i == 0) & drawLeadingName;
            i++;
            int noteX = RenderConstants.getNoteXOffset(i) + RenderConstants.noteXSpacing - xTraveled;
            drawNoteCollection(noteCollection, noteX, drawName);
        }
    }

    protected void drawNoteCollection(NoteCollection noteCollection, int xPosition, boolean drawName){
        for (Note note : noteCollection){
            drawNote(note, noteCollection, xPosition, drawName);
            drawAccidentals(note, xPosition);
            drawHelperLines(note, xPosition);
        }
    }

    protected void drawNote(Note note, NoteCollection noteCollection, int xPos, boolean drawName){
        int lineNumber = RenderConstants.getLineNumber(note);
        BufferedImage noteImage = imageLoader.getNoteImage();
        int noteWidth = noteImage.getWidth();
        int noteHeight = noteImage.getHeight();
        int noteY = RenderConstants.getLineYOffset(lineNumber) - (noteHeight / 2);

        if (noteCollection.isSqueezed(note)) {
            xPos += noteWidth;
        }
        graphics2D.drawImage(noteImage, null, xPos, noteY);

        if(drawName){
            NoteName noteName = note.getNoteName();
            graphics2D.setFont(new Font("Dialog", Font.BOLD, RenderConstants.nameFontSize));
            int nameY = RenderConstants.getLineYOffset(lineNumber) + noteHeight / 2 - 2;
            graphics2D.drawString(noteName.toString(), xPos + noteWidth, nameY);
        }
    }


    protected void drawAccidentals(Note note, int xPos) {
        int lineNumber = RenderConstants.getLineNumber(note);
        BufferedImage noteImage = imageLoader.getNoteImage();
        int noteHeight = noteImage.getHeight();
        int noteY = RenderConstants.getLineYOffset(lineNumber) - (noteHeight / 2);
        for (NoteAccidental accidental : note.getActiveAccidentals()) {
            if (accidental == NoteAccidental.SHARP) {
                BufferedImage sharpImage = imageLoader.getSharpImage();
                int sharpXPos = xPos - (int) (sharpImage.getWidth() * 1.3);
                int sharpYPos = noteY - (sharpImage.getHeight() / 3);
                graphics2D.drawImage(sharpImage, null, sharpXPos, sharpYPos);
            }
        }
    }

    protected void drawHelperLines(Note note, int xPos) {
        int lineNumber = RenderConstants.getLineNumber(note);
        for (int helperLineNumber : staffDecorator.getHelperLines(lineNumber)) {
            int helperLineYPos = RenderConstants.getLineYOffset(helperLineNumber);
            int lineXPosStart = xPos - 2;
            BufferedImage noteImage = imageLoader.getNoteImage();
            int lineXPosEnd = lineXPosStart + noteImage.getWidth() + 2;
            graphics2D.drawLine(lineXPosStart, helperLineYPos, lineXPosEnd, helperLineYPos);
        }
    }
}
