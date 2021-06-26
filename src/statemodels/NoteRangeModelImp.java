package statemodels;

import music.Note;

public class NoteRangeModelImp implements NoteRangeModel{
    private Note lowerNote;
    private Note upperNote;

    public NoteRangeModelImp(Note lowerNote, Note upperNote){
        this.lowerNote = lowerNote;
        this.upperNote = upperNote;
    }

    public void changeUpperLimit(Note note){
        this.upperNote = note;
    }

    public void changeLowerLimit(Note note){
        this.lowerNote = note;
    }

    public Note getUpperLimit(){
        return upperNote;
    }

    public Note getLowerLimit(){
        return lowerNote;
    }

    @Override
    public String toString() {
        return "[" + lowerNote.toString() + ", " + upperNote.toString() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NoteRangeModelImp){
            NoteRangeModelImp toCompare = (NoteRangeModelImp) obj;
            return (upperNote.equals(toCompare.upperNote) && lowerNote.equals(toCompare.lowerNote));
        }
        return false;
    }
}
