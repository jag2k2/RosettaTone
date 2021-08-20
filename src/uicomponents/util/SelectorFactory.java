package uicomponents.util;

import uicomponents.util.selectors.JSelector;

public interface SelectorFactory<T> {
    JSelector<T> makeSelector(SelectableState<T> state);
}
