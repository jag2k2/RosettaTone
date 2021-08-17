package uicomponents.util;

import javax.swing.*;

public abstract class JSelector<T> extends JComponent {
    public abstract void setSelectedItem(T selectedItem);
}
