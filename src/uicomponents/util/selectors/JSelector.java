package uicomponents.util.selectors;

import javax.swing.*;

public abstract class JSelector<T> extends JComponent {
    public abstract void setSelectedIndex(int index);
    public abstract void refreshSelections();
    protected abstract void addItem(T item);
}
