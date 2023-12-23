package uicomponents.notenamemode;

import javax.swing.*;
import java.awt.*;

public class NoteNameModeRenderer implements ListCellRenderer<NoteNameMode> {
    private final ListCellRenderer<? super NoteNameMode> listCellRenderer;

    public NoteNameModeRenderer(ListCellRenderer<? super NoteNameMode> listCellRenderer){
        this.listCellRenderer = listCellRenderer;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends NoteNameMode> list, NoteNameMode value, int index, boolean isSelected, boolean cellHasFocus) {
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
