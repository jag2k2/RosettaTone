package uicomponents.util;

public interface SelectableState<T> extends State<T> {
    T[] getOptions();
}
