package uicomponents.rangeselector;

import music.Note;
import notification.RangeChangeNotifier;

import javax.swing.*;
import java.awt.*;

public class NoteRangeRenderer implements ListCellRenderer<Note> {
    ListCellRenderer<? super Note> listCellRenderer;
    RangeChangeNotifier rangeChangeNotifier;

    public NoteRangeRenderer(ListCellRenderer<? super Note> listCellRenderer, RangeChangeNotifier rangeChangeNotifier){
        this.listCellRenderer = listCellRenderer;
        this.rangeChangeNotifier = rangeChangeNotifier;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Note> list, Note value, int index, boolean isSelected, boolean cellHasFocus) {
        if(isSelected) {
            rangeChangeNotifier.notifyObservers();
        }
        return listCellRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }
}
