package instrument;

public interface KeyChangeNotifier {
    void addKeyChangeObserver(KeyChangeObserver keyChangeObserver);
    void keyNoteChange();
}
