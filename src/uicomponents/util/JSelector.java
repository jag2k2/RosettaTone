package uicomponents.util;

import javax.swing.*;

public abstract class JSelector<T> extends JComponent {
    public abstract void setSelectedItem(T selectedItem);
    public abstract void refreshSelections();
    public abstract void setPreviewState(State<T> previewState);
    protected abstract void addItem(T item);
}
