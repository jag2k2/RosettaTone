package uicomponents.renderer;

import imageprocessing.StaffImage;
import music.*;
import statemodels.ClefModeState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class NoteDrawer {

    private static final String notePath = "/images/Whole-Note.png";
    private static final String naturalPath = "/images/Natural.png";
    private static final String sharpPath = "/images/Sharp.png";
    private static final String flatPath = "/images/Flat.png";

    private final StaffImage noteImage = new StaffImage(notePath);
    private final StaffImage naturalImage = new StaffImage(naturalPath);
    private final StaffImage sharpImage = new StaffImage(sharpPath);
    private final StaffImage flatImage = new StaffImage(flatPath);

    private final Graphics2D graphics2D;
    private final ClefModeState clefModeState;

    public NoteDrawer(Graphics2D graphics2D, ClefModeState clefModeState){
        this.graphics2D = graphics2D;
        this.clefModeState = clefModeState;
        noteImage.resize(0.22);
        naturalImage.resize(0.4);
        sharpImage.resize(0.4);
        flatImage.resize(0.4);
    }

    public void drawEnabledStaffs(){
        configLineDraw();
        for (Staff staff : enabledStaffs()){
            drawStaff(staff);
        }
    }

    public java.util.List<Staff> enabledStaffs(){
        List<Staff> enabledStaffs = new ArrayList<>();

        if (clefModeState.trebleEnabled()){
            enabledStaffs.add(RenderConstants.trebleStaff);
        }
        if (clefModeState.bassEnabled()){
            enabledStaffs.add(RenderConstants.bassStaff);
        }
        return enabledStaffs;
    }

    public void drawStaff(Staff staff){
        int clefImageXPos = RenderConstants.getClefXOffset();
        int clefImageYPos = staff.getClefYOffset();
        StaffImage staffImage = staff.createStaffImage();
        graphics2D.drawImage(staffImage.getBufferedImage(), null, clefImageXPos, clefImageYPos);
        int totalNumberOfLines = RenderConstants.numberOfLines;
        for (int i = 0; i < totalNumberOfLines; i++){
            if ((staff.getTopVisibleLine() <= i) && (i <= staff.getBottomVisibleLine()) && ((i % 2) == 0)){
                int lineYPos =  RenderConstants.getLineYOffset(i);
                int lineXPosStart = RenderConstants.getLineXStart();
                int lineXPosEnd = RenderConstants.getLineXEnd();
                graphics2D.drawLine(lineXPosStart, lineYPos, lineXPosEnd, lineYPos);
            }
        }
    }

    protected void configLineDraw(){
        graphics2D.setColor(Color.BLACK);
        int lineThickness = 2;
        graphics2D.setStroke(new BasicStroke(lineThickness));
    }

    public void drawKeyboardNotes(NoteCollection noteCollection){
        int noteX = RenderConstants.getNoteXOffset(1);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        drawNoteCollection(noteCollection, noteX);
    }

    public void drawFlashcardNotes(NoteCollectionList noteCollectionList, int xTraveled){
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        int i = 0;
        for (NoteCollection noteCollection: noteCollectionList){
            i++;
            int noteX = RenderConstants.getNoteXOffset(i) + RenderConstants.noteXSpacing - xTraveled;
            drawNoteCollection(noteCollection, noteX);
        }
    }

    protected void drawNoteCollection(NoteCollection noteCollection, int xPosition){
        for (Note note : noteCollection){
            drawNote(note, noteCollection, xPosition);
            drawAccidentals(note, xPosition);
            drawHelperLines(note, clefModeState, xPosition);
        }
    }

    protected void drawNote(Note note, NoteCollection noteCollection, int xPos){
        int lineNumber = RenderConstants.getLineNumber(note);
        int noteHeight = noteImage.getBufferedImage().getHeight();
        int noteY = RenderConstants.getLineYOffset(lineNumber) - (noteHeight / 2);

        int noteWidth = noteImage.getBufferedImage().getWidth();
        if (noteCollection.isSqueezed(note)) {
            xPos += noteWidth;
        }
        graphics2D.drawImage(noteImage.getBufferedImage(), null, xPos, noteY);
    }


    protected void drawAccidentals(Note note, int xPos) {
        int lineNumber = RenderConstants.getLineNumber(note);
        int noteHeight = noteImage.getBufferedImage().getHeight();
        int noteY = RenderConstants.getLineYOffset(lineNumber) - (noteHeight / 2);
        for (NoteAccidental accidental : note.getActiveAccidentals()) {
            if (accidental == NoteAccidental.SHARP) {
                BufferedImage sharpBufferedImage = sharpImage.getBufferedImage();
                int sharpXPos = xPos - (int) (sharpImage.getBufferedImage().getWidth() * 1.3);
                int sharpYPos = noteY - (sharpBufferedImage.getHeight() / 3);
                graphics2D.drawImage(sharpBufferedImage, null, sharpXPos, sharpYPos);
            }
        }
    }

    protected void drawHelperLines(Note note, ClefModeState clefModeState, int xPos){
        int lineNumber = RenderConstants.getLineNumber(note);
        Staff trebleStaff = RenderConstants.trebleStaff;
        Staff bassStaff = RenderConstants.bassStaff;
        int topVisibleLine = trebleStaff.getTopVisibleLine();
        int bottomVisibleLine = bassStaff.getBottomVisibleLine();

        if (!clefModeState.trebleEnabled()) {
            topVisibleLine = bassStaff.getTopVisibleLine();
        }

        if (!clefModeState.bassEnabled()) {
            bottomVisibleLine = trebleStaff.getBottomVisibleLine();
        }

        for (int i = lineNumber; i < topVisibleLine; i++){
            drawHelperLine(i, xPos);
        }
        for (int i = lineNumber; i > bottomVisibleLine; i--){
            drawHelperLine(i, xPos);
        }

        if (clefModeState.trebleEnabled() && clefModeState.bassEnabled()){
            if(lineNumber > trebleStaff.getBottomVisibleLine() && lineNumber < bassStaff.getTopVisibleLine()){
                drawHelperLine(lineNumber, xPos);
            }
        }
    }

    protected void drawHelperLine(int lineNumber, int xPos){
        int helperLineYPos = RenderConstants.getLineYOffset(lineNumber);
        if ((lineNumber % 2) == 0) {
            int lineXPosStart = xPos - 2;
            int lineXPosEnd = lineXPosStart + noteImage.getBufferedImage().getWidth() + 2;
            graphics2D.drawLine(lineXPosStart, helperLineYPos, lineXPosEnd, helperLineYPos);
        }
    }
}
