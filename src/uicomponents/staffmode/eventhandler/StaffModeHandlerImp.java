package uicomponents.staffmode.eventhandler;

import uicomponents.staffmode.SelectorFactory;
import uicomponents.staffmode.StaffModeHandler;
import uicomponents.staffmode.StaffMode;

/*This class uses an abstract factory to create the selector used to change how the staff is displayed.  This pattern was
chosen because I haven't decided whether this functionality should be selectable via a combobox or a menu or a radio button
(or multiple).  So the functionality of handling UI events and setting the staffMode is decoupled from the actual element
constructed to sends those events.
*/

public class StaffModeHandlerImp implements StaffModeHandler {
    private final StaffModeChangeObserver staffMode;

    public StaffModeHandlerImp(StaffModeChangeObserver staffMode) {
        this.staffMode = staffMode;
    }

    @Override
    public JSelector createModeSelector(SelectorFactory selectorFactory, StaffMode defaultMode) {
        JSelector selector = selectorFactory.makeSelector(staffMode);
        selector.setSelectedItem(defaultMode);

        return selector;
    }
}
