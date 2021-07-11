package uicomponents.rangeselector;

import music.Note;
import music.NoteName;
import notification.RangeChangeObserver;
import uicomponents.UIComponent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class RangeSelectorImp implements UIComponent, RangeChangeObserver {
    static private final Note lowerBoundNote = new Note(NoteName.A, 0);
    static private final Note upperBoundNote = new Note(NoteName.C, 8);

    private final NoteSelector lowerNoteSelector;
    private final NoteSelector upperNoteSelector;

    public RangeSelectorImp(NoteSelector lowerNoteSelector, NoteSelector upperNoteSelector){
        this.lowerNoteSelector = lowerNoteSelector;
        this.upperNoteSelector = upperNoteSelector;
        refreshSelectors();
    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(lowerNoteSelector.getComponent());
        panel.add(upperNoteSelector.getComponent());
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setBorder(border);
        return panel;
    }

    @Override
    public void rangeChanged() {
        refreshSelectors();
    }

    protected void refreshSelectors(){
        Note lowerSelectedNote = lowerNoteSelector.getSelectedNote();
        Note upperSelectedNote = upperNoteSelector.getSelectedNote();

        lowerNoteSelector.refreshSelection(upperSelectedNote, lowerBoundNote, lowerSelectedNote);
        upperNoteSelector.refreshSelection(upperBoundNote, lowerSelectedNote, upperSelectedNote);
    }
}
