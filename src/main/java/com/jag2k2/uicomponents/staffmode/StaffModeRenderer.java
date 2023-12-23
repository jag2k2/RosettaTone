package com.jag2k2.uicomponents.staffmode;

import javax.swing.*;
import java.awt.*;

public class StaffModeRenderer implements ListCellRenderer<StaffMode> {
    private final ListCellRenderer<? super StaffMode> listCellRenderer;

    public StaffModeRenderer(ListCellRenderer<? super StaffMode> listCellRenderer){
        this.listCellRenderer = listCellRenderer;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends StaffMode> list, StaffMode value, int index, boolean isSelected, boolean cellHasFocus) {
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
