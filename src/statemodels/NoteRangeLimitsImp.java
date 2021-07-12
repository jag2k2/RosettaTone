package statemodels;

import music.Note;
import music.NoteName;
import trainer.NoteRangeLimits;
import uicomponents.rangeselector.noteselector.NoteModifier;

public class NoteRangeLimitsImp implements NoteRangeLimits {
    private final NoteModifier lowerLimitModel;
    private final NoteModifier upperLimitModel;

    public NoteRangeLimitsImp(NoteModifier lowerLimitModel, NoteModifier upperLimitModel){
        this.lowerLimitModel = lowerLimitModel;
        this.upperLimitModel = upperLimitModel;
    }

    @Override
    public Note generateRandomNote() {
        return new Note(NoteName.A, 4);
    }
}
