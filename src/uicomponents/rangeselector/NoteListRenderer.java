package uicomponents.rangeselector;

import music.Note;
import statemodels.NoteLimitModel;

import javax.swing.*;
import java.awt.*;

public class NoteListRenderer implements ListCellRenderer<Note> {
    private final ListCellRenderer<? super Note> listCellRenderer;
    private final NoteLimitModel noteLimitModel;

    public NoteListRenderer(ListCellRenderer<? super Note> listCellRenderer, NoteLimitModel noteLimitModel){
        this.listCellRenderer = listCellRenderer;
        this.noteLimitModel = noteLimitModel;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Note> list, Note value, int index, boolean isSelected, boolean cellHasFocus) {
        if(isSelected) {
            noteLimitModel.changeLimit(value);
        }
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
