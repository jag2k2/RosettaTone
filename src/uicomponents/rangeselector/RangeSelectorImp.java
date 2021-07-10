package uicomponents.rangeselector;

import music.Note;
import music.NoteName;
import statemodels.NoteRangeLimits;
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

    private final NoteSelector lowerNoteSelector;
    private final NoteSelector upperNoteSelector;

    public RangeSelectorImp(NoteSelector lowerNoteSelector, NoteSelector upperNoteSelector){
        this.lowerNoteSelector = lowerNoteSelector;
        this.upperNoteSelector = upperNoteSelector;

        lowerNoteSelector.addActionListener(this);
        upperNoteSelector.addActionListener(this);

        refreshSelectors();
    }

    @Override
    public Component getComponent() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(lowerNoteSelector.getComponent());
        buttonPanel.add(upperNoteSelector.getComponent());
        JPanel panel = new JPanel();
        BoxLayout panelLayout = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(panelLayout);
        JLabel title = new JLabel();
        title.setText("Note Range");
        title.setPreferredSize(new Dimension(10,10));
        panel.add(title);
        panel.add(buttonPanel);
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setBorder(border);
        return buttonPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("comboBoxChanged")){
            refreshSelectors();
        }
    }

    protected void refreshSelectors(){
        Note lowerSelectedNote = lowerNoteSelector.getSelectedNote();
        Note upperSelectedNote = upperNoteSelector.getSelectedNote();

        lowerNoteSelector.refreshSelection(upperSelectedNote, lowerBoundNote, lowerSelectedNote);
        upperNoteSelector.refreshSelection(upperBoundNote, lowerSelectedNote, upperSelectedNote);
    }
}
