package notification;

public interface RangeChangeNotifier {
    void addObserver(RangeChangeObserver observer);
    void notifyObservers();
}
