package uicomponents.util.selectors;

import uicomponents.util.State;

import javax.swing.*;

public abstract class JComboSelector<T> extends JSelector<T> {
    public abstract void setPreviewState(State<T> previewState);
    public abstract void setRenderer(ListCellRenderer<T> renderer);
}
