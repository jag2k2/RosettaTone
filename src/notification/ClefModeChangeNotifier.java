package notification;

public interface ClefModeChangeNotifier {
    void addObserver(ClefModeChangeObserver observer);
    void notifyObservers();
}
