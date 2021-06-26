package notification;

public interface StaffChangeNotifier {
    void addObserver(StaffChangeObserver observer);
    void notifyObservers();
}
