package notelimit;

import notification.LimitChangeObserver;

public interface LimitChangeNotifier {
    void addObserver(LimitChangeObserver observer);
    void notifyObservers();
}
