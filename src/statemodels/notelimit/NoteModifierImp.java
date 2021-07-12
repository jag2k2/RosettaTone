package statemodels.notelimit;

import music.Note;
import music.NoteAccidental;
import uicomponents.rangeselector.noteselector.NoteModifier;
import uicomponents.rangeselector.noteselector.NotePreviewer;

public class NoteModifierImp implements NoteModifier, NotePreviewer {
    private Note limit;
    private final LimitChangeNotifier limitChangeNotifier;

    public NoteModifierImp(Note limit, LimitChangeNotifier limitChangeNotifier){
        this.limit = limit;
        this.limitChangeNotifier = limitChangeNotifier;
    }

    @Override
    public Note getLimit() {
        return limit;
    }

    @Override
    public void setLimit(Note note){
        if (!limit.equals(note)) {
            limit = note;
            limitChangeNotifier.notifyObservers();
        }
    }

    @Override
    public void preview(Note note) {

    }

    @Override
    public void increment() {
        setLimit(limit.getNext(NoteAccidental.NATURAL));
    }

    @Override
    public void decrement() {
        setLimit(limit.getPrevious(NoteAccidental.NATURAL));
    }

    @Override
    public int compareTo(Note note) {
        return limit.compareTo(note);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NoteModifierImp){
            NoteModifierImp toCompare = (NoteModifierImp) obj;
            return limit.equals(toCompare.limit);
        }
        return false;
    }

    @Override
    public String toString() {
        return "active: " + limit;
    }
}
