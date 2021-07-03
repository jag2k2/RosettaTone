package notification;

public interface ModeChangeNotifier {
    void addObserver(ModeChangeObserver observer);
    void notifyObservers();
}
