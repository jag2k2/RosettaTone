package uicomponents.util;

import uicomponents.util.selectors.JSelector;

public interface ModeHandler<T> {
    JSelector<T> createModeSelector(SelectorFactory<T> selectorFactory);
}
