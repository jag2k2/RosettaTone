package uicomponents.rangeselector;

import music.Note;
import music.NoteName;
import statemodels.NoteLimitModel;
import uicomponents.UIComponent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RangeSelectorImp implements UIComponent, ActionListener {
    static private final Note lowerBoundNote = new Note(NoteName.A, 0);
    static private final Note upperBoundNote = new Note(NoteName.C, 8);

    private final NoteLimitModel lowerLimitModel;
    private final NoteLimitModel upperLimitModel;
    private final NoteSelectorImp lowerNoteSelector;
    private final NoteSelectorImp upperNoteSelector;

    public RangeSelectorImp(NoteLimitModel lowerLimitModel, NoteLimitModel upperLimitModel){
        this.lowerLimitModel = lowerLimitModel;
        this.upperLimitModel = upperLimitModel;
        this.lowerNoteSelector = new NoteSelectorImp(lowerLimitModel);
        this.upperNoteSelector = new NoteSelectorImp(upperLimitModel);

        lowerNoteSelector.addActionListener(this);
        upperNoteSelector.addActionListener(this);

        refreshSelectors();
    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel(new FlowLayout());
        Border border = BorderFactory.createLineBorder(Color.GRAY);
        panel.setBorder(border);
        panel.add(lowerNoteSelector.getPanel());
        panel.add(upperNoteSelector.getPanel());
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("comboBoxChanged")){
            refreshSelectors();
        }
    }

    protected void refreshSelectors(){
        Note lowerSelectedNote = lowerLimitModel.getLimit();
        Note upperSelectedNote = upperLimitModel.getLimit();

        lowerNoteSelector.refreshSelection(upperSelectedNote, lowerBoundNote, lowerSelectedNote);
        upperNoteSelector.refreshSelection(upperBoundNote, lowerSelectedNote, upperSelectedNote);
    }
}
