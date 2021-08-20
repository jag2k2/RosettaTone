package uicomponents.rangeselector.noteselector;

import uicomponents.util.JSelector;

public interface CanRefreshSelector<T> {
    void refreshSelector(JSelector<T> selector);
}
