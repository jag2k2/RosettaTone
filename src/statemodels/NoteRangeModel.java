package statemodels;

import music.Note;

public interface NoteRangeModel {
    void changeUpperLimit(Note note);
    void changeLowerLimit(Note note);
    Note getUpperLimit();
    Note getLowerLimit();
}
