package notification;

import java.util.ArrayList;

public class StaffChangeNotifierImp implements StaffChangeNotifier {
    private final ArrayList<StaffChangeObserver> staffChangeObservers;

    public StaffChangeNotifierImp(){
        this.staffChangeObservers = new ArrayList<>();
    }

    @Override
    public void addObserver(StaffChangeObserver observer) {
        staffChangeObservers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (StaffChangeObserver staffChangeObserver : staffChangeObservers) {
            staffChangeObserver.update();
        }
    }
}
