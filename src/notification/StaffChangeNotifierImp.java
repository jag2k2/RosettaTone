package notification;

import java.util.ArrayList;

public class StaffChangeNotifierImp implements StaffChangeNotifier {
    private final ArrayList<StaffChangeObserver> staffChangeObservers;

    public StaffChangeNotifierImp(){
        this.staffChangeObservers = new ArrayList<>();
    }

    @Override
    public void add(StaffChangeObserver staffChangeObserver) {
        staffChangeObservers.add(staffChangeObserver);
    }

    @Override
    public void notifyObservers() {
        for (StaffChangeObserver staffChangeObserver : staffChangeObservers) {
            staffChangeObserver.update();
        }
    }
}
