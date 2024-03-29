package com.jag2k2.music;

import com.jag2k2.imageprocessing.StaffImage;
import com.jag2k2.uicomponents.renderer.records.NoteImages;
import com.jag2k2.uicomponents.renderer.records.RenderConstants;
import com.jag2k2.utility.NoteSet;

import java.awt.*;
import java.util.Set;

public class Note implements Comparable<Note> {

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

    public Note (int lineNumber){
        int notePosition = (RenderConstants.numberOfLines - 1) - lineNumber + 5;

        this.noteName = NoteName.values()[notePosition % 7];
        this.octave = notePosition / 7;
        this.accidental = NoteAccidental.NATURAL;
    }

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

    public int getLineNumber(){
        return (RenderConstants.numberOfLines - 1) - (noteValue() - 5);
    }

    public boolean noteHeadEquals(Note note){
        return this.noteName == note.noteName && this.octave == note.octave;
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

    public int noteValue(){
        return octave * 7 + noteName.getPosition();
    }

    public void draw(Graphics2D graphics2D, NoteImages noteImages, NoteSet notes, int xPos, Set<Integer> ledgerLines, boolean drawName) {

        int lineNumber = getLineNumber();
        StaffImage noteImage = noteImages.noteImage;
        StaffImage sharpImage = noteImages.sharpImage;

        int noteWidth = noteImage.getWidth();
        int noteHeight = noteImage.getHeight();
        int noteY = RenderConstants.getLineYOffset(lineNumber) - (noteHeight / 2);

        if (accidental == NoteAccidental.SHARP) {
            int sharpXPos = xPos - (int) (sharpImage.getWidth() * 1.3);
            int sharpYPos = noteY - (sharpImage.getHeight() / 3);
            sharpImage.draw(graphics2D, sharpXPos, sharpYPos);
            }

        int lineThickness = RenderConstants.ledgerLineThickness;
        graphics2D.setStroke(new BasicStroke(lineThickness));

        for (int ledgerLineNumber : ledgerLines) {
            int helperLineYPos = RenderConstants.getLineYOffset(ledgerLineNumber);
            int lineXPosStart = xPos - 2;
            int lineXPosEnd = lineXPosStart + noteWidth + 2;
            graphics2D.drawLine(lineXPosStart, helperLineYPos, lineXPosEnd, helperLineYPos);
        }

        if (isSqueezed(notes)) {
            xPos += noteWidth;
        }

        noteImage.draw(graphics2D, xPos, noteY);

        if(drawName){
            graphics2D.setFont(new Font("Dialog", Font.BOLD, RenderConstants.nameFontSize));
            int nameY = RenderConstants.getLineYOffset(lineNumber) - 2;
            if ((lineNumber % 2) == 1)
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

    @Override
    public int compareTo(Note o) {
        return noteValue() - o.noteValue();
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
