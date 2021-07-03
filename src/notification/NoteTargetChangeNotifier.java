package notification;

public interface NoteTargetChangeNotifier {
    void addObserver(NoteTargetChangeObserver observer);
    void notifyObservers();
}
