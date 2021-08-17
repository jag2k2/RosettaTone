package uicomponents.util;

import uicomponents.util.SelectorFactory;
import uicomponents.util.ModeHandler;
import uicomponents.util.CanSetMode;
import uicomponents.util.JSelector;

/*This class uses an abstract factory to create the selector used to change how the staff is displayed.  This pattern was
chosen because I haven't decided whether this functionality should be selectable via a combobox or a menu or a radio button
(or multiple).  So the functionality of handling UI events and setting the staffMode is decoupled from the actual element
constructed to sends those events.
*/

public class ModeHandlerImp<T> implements ModeHandler<T> {
    private final CanSetMode<T> selectableMode;

    public ModeHandlerImp(CanSetMode<T> selectableMode) {
        this.selectableMode = selectableMode;
    }

    @Override
    public JSelector<T> createModeSelector(SelectorFactory<T> selectorFactory, T defaultMode) {
        JSelector<T> selector = selectorFactory.makeSelector(selectableMode);
        selector.setSelectedItem(defaultMode);

        return selector;
    }
}
