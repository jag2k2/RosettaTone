package uicomponents.util;

import uicomponents.util.JSelector;
import uicomponents.util.CanSetMode;

public interface SelectorFactory<T> {
    JSelector<T> makeSelector(CanSetMode<T> defaultMode);
}
