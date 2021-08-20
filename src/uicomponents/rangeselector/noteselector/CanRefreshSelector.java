package uicomponents.rangeselector.noteselector;

import uicomponents.util.selectors.JSelector;

public interface CanRefreshSelector<T> {
    void refreshSelector(JSelector<T> selector);
}
