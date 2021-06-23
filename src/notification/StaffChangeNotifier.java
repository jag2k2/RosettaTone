package notification;

public interface StaffChangeNotifier {
    void add(StaffChangeObserver staffChangeObserver);
    void notifyObservers();
}
