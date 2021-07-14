package uicomponents.alphabetmode;

import javax.swing.*;
import java.awt.*;

public class AlphabetModeRenderer implements ListCellRenderer<AlphabetMode> {
    private final ListCellRenderer<? super AlphabetMode> listCellRenderer;

    public AlphabetModeRenderer(ListCellRenderer<? super AlphabetMode> listCellRenderer){
        this.listCellRenderer = listCellRenderer;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends AlphabetMode> list, AlphabetMode value, int index, boolean isSelected, boolean cellHasFocus) {
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
