package uicomponents.rangeselector.noteselector;

import music.Note;
import javax.swing.*;
import java.awt.*;

public class NoteListRenderer implements ListCellRenderer<Note> {
    private final ListCellRenderer<? super Note> listCellRenderer;
    private final NotePreviewer notePreviewer;

    public NoteListRenderer(ListCellRenderer<? super Note> listCellRenderer, NotePreviewer notePreviewer){
        this.listCellRenderer = listCellRenderer;
        this.notePreviewer = notePreviewer;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Note> list, Note note, int index, boolean isSelected, boolean cellHasFocus) {
        if(isSelected) {
            notePreviewer.preview(note);
        }
        Component rendererComponent = listCellRenderer.getListCellRendererComponent(list, note, index, isSelected, cellHasFocus);
        if (rendererComponent instanceof JLabel){
            JLabel rendererLabel = (JLabel) rendererComponent;
            Font oldFont = rendererLabel.getFont();
            Font newFont = new Font(oldFont.getName(), Font.PLAIN, 20);
            rendererLabel.setFont(newFont);
            rendererLabel.setHorizontalAlignment(SwingConstants.CENTER);
            return rendererLabel;
        }
        return rendererComponent;
    }
}
