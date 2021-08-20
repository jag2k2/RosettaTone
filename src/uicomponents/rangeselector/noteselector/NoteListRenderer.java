package uicomponents.rangeselector.noteselector;

import music.note.Note;
import uicomponents.util.Updatable;

import javax.swing.*;
import java.awt.*;

public class NoteListRenderer implements ListCellRenderer<Note> {
    private final ListCellRenderer<? super Note> listCellRenderer;
    private final Updatable<Note> previewLimit;

    public NoteListRenderer(ListCellRenderer<? super Note> listCellRenderer, Updatable<Note> previewLimit){
        this.listCellRenderer = listCellRenderer;
        this.previewLimit = previewLimit;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Note> list, Note note, int index, boolean isSelected, boolean cellHasFocus) {
        if(isSelected) {
            previewLimit.update(note);
        }
        Component rendererComponent = listCellRenderer.getListCellRendererComponent(list, note, index, isSelected, cellHasFocus);

        if (rendererComponent instanceof JLabel){
            JLabel rendererLabel = (JLabel) rendererComponent;
            rendererLabel.setHorizontalAlignment(SwingConstants.CENTER);
            return rendererLabel;
        }
        return rendererComponent;
    }
}
