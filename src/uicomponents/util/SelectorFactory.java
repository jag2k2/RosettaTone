package uicomponents.util;

public interface SelectorFactory<T> {
    JSelector<T> makeSelector(SelectableState<T> state);
}
