package statemodels;

import music.Note;

public interface NoteRangeLimits {
    Note getLowerLimitNote();
    Note getUpperLimitNote();
}
