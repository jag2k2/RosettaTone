package uicomponents.util.selectors;

import javax.swing.*;

public abstract class JListSelector<T> extends JSelector<T>{
    public abstract void setRenderer(ListCellRenderer<T> renderer);
}
