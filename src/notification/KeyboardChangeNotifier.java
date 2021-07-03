package notification;

public interface KeyboardChangeNotifier {
    void addObserver(KeyboardChangeObserver observer);
    void notifyObservers();
}
