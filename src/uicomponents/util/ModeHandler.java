package uicomponents.util;

public interface ModeHandler<T> {
    JSelector<T> createModeSelector(SelectorFactory<T> selectorFactory);
}
