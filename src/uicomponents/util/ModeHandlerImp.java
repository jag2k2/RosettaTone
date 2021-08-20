package uicomponents.util;

/*This class uses an abstract factory to create the selector used to change how the staff is displayed.  This pattern was
chosen because I haven't decided whether this functionality should be selectable via a combobox or a menu or a radio button
(or multiple).  So the functionality of handling UI events and setting the staffMode is decoupled from the actual element
constructed to sends those events.
*/

public class ModeHandlerImp<T> implements ModeHandler<T> {
    private final SelectableState<T> selectableMode;

    public ModeHandlerImp(SelectableState<T> selectableMode) {
        this.selectableMode = selectableMode;
    }

    @Override
    public JSelector<T> createModeSelector(SelectorFactory<T> selectorFactory) {
        JSelector<T> selector = selectorFactory.makeSelector(selectableMode);
        selector.refreshSelections();
        return selector;
    }
}
