package uicomponents.util;

public interface State<T> extends Updatable<T> {
    T getActive();
}
