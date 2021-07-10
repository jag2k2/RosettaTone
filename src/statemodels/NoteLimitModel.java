package statemodels;

import music.Note;

public interface NoteLimitModel {
    void setLimit(Note note);
    Note getLimit();
}
