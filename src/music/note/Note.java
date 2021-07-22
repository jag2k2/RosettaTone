package music.note;

import imageprocessing.StaffImage;
import music.LineDrawable;
import music.line.Line;
import statemodels.NoteDrawable;
import uicomponents.renderer.grandstaff.StaffModeEvaluator;
import uicomponents.renderer.records.NoteImages;
import uicomponents.renderer.records.RenderConstants;
import utility.NoteSet;

import java.awt.*;

public class Note implements NoteDrawable, Comparable<Note> {

    private final NoteName noteName;
    private final int octave;
    private final NoteAccidental accidental;

    public Note(NoteName noteName, int octave) {
        this.noteName = noteName;
        this.octave = octave;
        this.accidental = NoteAccidental.NATURAL;
    }

    public Note(NoteName noteName, int octave, NoteAccidental noteAccidental) {
        this.noteName = noteName;
        this.octave = octave;
        this.accidental = noteAccidental;

    }

    /*public Note (int lineNumber){
        int notePosition = (RenderConstants.numberOfLines - 1) - lineNumber + 5;

        this.noteName = NoteName.values()[notePosition % 7];
        this.octave = notePosition / 7;
        this.accidental = NoteAccidental.NATURAL;
    }*/

    public int getMidiNumber(){
        int midiSum = 12 + (octave * 12);
        int notePosition = noteName.getPosition();
        if (notePosition >= 3) {
            midiSum--;
        }
        midiSum += 2 * notePosition;

        if (accidental == NoteAccidental.SHARP){
            midiSum++;
        }

        if (accidental == NoteAccidental.FLAT){
            midiSum--;
        }
        return midiSum;
    }

    public Line getCenterLine(){
        return new Line((RenderConstants.numberOfLines - 1) - (getNoteValue() - 5));
    }

    public Note generateRandom(Note otherNote){
        int max = Math.max(getNoteValue(), otherNote.getNoteValue());
        int min = Math.min(getNoteValue(), otherNote.getNoteValue());
        int randomLine = (int) Math.floor(Math.random()*(max-min+1) + min);
        NoteName noteName = NoteName.values()[randomLine % 7];
        int octave = randomLine / 7;
        return new Note(noteName, octave);
    }

    protected int getNoteValue(){
        return octave * 7 + noteName.getPosition();
    }

    public Note getNext(NoteAccidental noteAccidental) {
        int newOctave = octave;
        NoteName newNoteName;

        if (noteName.getPosition() >= NoteName.B.getPosition()){
            newOctave++;
            newNoteName = NoteName.C;
        }
        else {
            newNoteName = NoteName.values()[noteName.getPosition() + 1];
        }
        return new Note(newNoteName, newOctave, noteAccidental);
    }

    public Note getPrevious(NoteAccidental noteAccidental) {
        int newOctave = octave;
        NoteName newNoteName;

        if (noteName.getPosition() <= NoteName.C.getPosition()){
            newOctave--;
            newNoteName = NoteName.B;
        }
        else {
            newNoteName = NoteName.values()[noteName.getPosition() - 1];
        }
        return new Note(newNoteName, newOctave, noteAccidental);
    }

    public void draw(Graphics2D graphics2D, NoteImages noteImages, NoteSet notes, int xPos, StaffModeEvaluator staffMode, boolean drawName) {
        StaffImage noteImage = noteImages.noteImage;
        StaffImage sharpImage = noteImages.sharpImage;

        int noteWidth = noteImage.getWidth();
        int noteHeight = noteImage.getHeight();
        Line centerLine = getCenterLine();
        int noteY = centerLine.getYOffset() - (noteHeight / 2);

        if (accidental == NoteAccidental.SHARP) {
            int sharpXPos = xPos - (int) (sharpImage.getWidth() * 1.3);
            int sharpYPos = noteY - (sharpImage.getHeight() / 3);
            sharpImage.draw(graphics2D, sharpXPos, sharpYPos);
            }

        for (LineDrawable ledgerLine : centerLine.getLedgerLines(staffMode, xPos, noteWidth)) {
            ledgerLine.draw(graphics2D);
        }

        if (isSqueezed(notes)) {
            xPos += noteWidth;
        }

        noteImage.draw(graphics2D, xPos, noteY);

        if(drawName){
            graphics2D.setFont(new Font("Dialog", Font.BOLD, RenderConstants.nameFontSize));
            int nameY = centerLine.getYOffset() - 2;
            if (!centerLine.isEven())
            nameY += noteHeight / 2;
            graphics2D.drawString(noteName.toString(), xPos + noteWidth, nameY);
        }
    }

    protected boolean isSqueezed(NoteSet notes){
        Note adjacentNote = getPrevious(NoteAccidental.NATURAL);
        for (Note noteInSet : notes){
            if (noteInSet.noteHeadEquals(adjacentNote)){
                return !adjacentNote.isSqueezed(notes);
            }
        }
        return false;
    }

    protected boolean noteHeadEquals(Note note){
        return this.noteName == note.noteName && this.octave == note.octave;
    }

    @Override
    public int compareTo(Note o) {
        return getNoteValue() - o.getNoteValue();
    }

    @Override
    public int hashCode() {
        String nameOctave = noteName.name() + octave;
        return nameOctave.hashCode();
    }

    @Override
    public String toString() {
        String noteString = noteName.toString();
        if (accidental == NoteAccidental.SHARP) {
            noteString += "#";
        }
        if (accidental == NoteAccidental.FLAT) {
            noteString += "b";
        }
        noteString += Integer.toString(octave);
        return noteString;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Note){
            Note noteCompare = (Note) obj;
            return this.noteName == noteCompare.noteName
                    && this.octave == noteCompare.octave
                    && this.accidental.equals(noteCompare.accidental);
        }
        return false;
    }
}
