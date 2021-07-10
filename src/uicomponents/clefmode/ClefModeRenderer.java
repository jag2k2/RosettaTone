package uicomponents.clefmode;

import javax.swing.*;
import java.awt.*;

public class ClefModeRenderer implements ListCellRenderer<ClefMode> {
    private final ListCellRenderer<? super ClefMode> listCellRenderer;

    public ClefModeRenderer(ListCellRenderer<? super ClefMode> listCellRenderer){
        this.listCellRenderer = listCellRenderer;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ClefMode> list, ClefMode value, int index, boolean isSelected, boolean cellHasFocus) {
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
