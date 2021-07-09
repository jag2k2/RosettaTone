package uicomponents.rangeselector;

import music.Note;
import music.NoteName;
import statemodels.NoteRangeLimits;
import statemodels.NoteRangeModel;
import uicomponents.UIComponent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RangeSelectorImp implements UIComponent, ActionListener {
    static private final Note lowerBoundNote = new Note(NoteName.A, 0);
    static private final Note upperBoundNote = new Note(NoteName.C, 8);

    private final NoteRangeLimits noteRangeLimits;
    private final NoteSelectorImp lowerNoteSelector;
    private final NoteSelectorImp upperNoteSelector;

    public RangeSelectorImp(NoteRangeModel noteRangeModel, NoteRangeLimits noteRangeLimits){
        this.noteRangeLimits = noteRangeLimits;
        this.lowerNoteSelector = new NoteSelectorImp(noteRangeModel.getLowerLimitModel());
        this.upperNoteSelector = new NoteSelectorImp(noteRangeModel.getUpperLimitModel());

        lowerNoteSelector.addActionListener(this);
        upperNoteSelector.addActionListener(this);

        refreshSelectors();
    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel(new FlowLayout());
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setBorder(border);
        panel.add(lowerNoteSelector.getComponent());
        panel.add(upperNoteSelector.getComponent());
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("comboBoxChanged")){
            refreshSelectors();
        }
    }

    protected void refreshSelectors(){
        Note lowerSelectedNote = noteRangeLimits.getLowerLimitNote();
        Note upperSelectedNote = noteRangeLimits.getUpperLimitNote();

        lowerNoteSelector.refreshSelection(upperSelectedNote, lowerBoundNote, lowerSelectedNote);
        upperNoteSelector.refreshSelection(upperBoundNote, lowerSelectedNote, upperSelectedNote);
    }
}
