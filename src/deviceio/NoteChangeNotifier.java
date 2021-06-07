package deviceio;

public interface NoteChangeNotifier {
    void addNoteChangeObserver(NoteChangeObserver noteChangeObserver);
    void notifyNoteChange();
}
