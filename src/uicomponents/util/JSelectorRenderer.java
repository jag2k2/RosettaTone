package uicomponents.util;

import javax.swing.*;
import java.awt.*;

public class JSelectorRenderer<T> implements ListCellRenderer<T> {
    private final ListCellRenderer<? super T> listCellRenderer;

    public JSelectorRenderer(ListCellRenderer<? super T> listCellRenderer){
        this.listCellRenderer = listCellRenderer;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected, boolean cellHasFocus) {
        Component rendererComponent = listCellRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (rendererComponent instanceof JLabel){
            JLabel rendererLabel = (JLabel) rendererComponent;
            Font oldFont = rendererLabel.getFont();
            Font newFont = new Font(oldFont.getName(), Font.PLAIN, 20);
            rendererLabel.setFont(newFont);
            return rendererLabel;
        }
        return rendererComponent;
    }
}
