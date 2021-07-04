package uicomponents.rangeselector;

import music.Note;
import music.NoteAccidental;
import statemodels.NoteLimitModel;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NoteSelectorImp implements PopupMenuListener, ActionListener {
    private final NoteLimitModel noteLimitModel;
    private final JComboBox<Note> noteComboBox;

    public NoteSelectorImp(NoteLimitModel noteLimitModel){
        this.noteLimitModel = noteLimitModel;
        this.noteComboBox = new JComboBox<>();
        this.noteComboBox.setRenderer(new NoteListRenderer(noteComboBox.getRenderer(), noteLimitModel));

        this.noteComboBox.addPopupMenuListener(this);
        this.noteComboBox.addActionListener(this);
    }

    public JPanel getPanel(){
        JPanel panel = new JPanel();
        Dimension boxSize = new Dimension(80, 40);
        noteComboBox.setPreferredSize(boxSize);
        panel.add(noteComboBox);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("comboBoxChanged")){
            Note selectedNote = (Note) noteComboBox.getSelectedItem();
            noteLimitModel.changeLimit(selectedNote);
        }
    }

    public void addActionListener(ActionListener actionListener){
        this.noteComboBox.addActionListener(actionListener);
    }

    public void refreshSelection(Note upperNote, Note lowerNote, Note selectedNote){
        noteComboBox.removeAllItems();
        for (Note noteIterator = upperNote; noteIterator.compareTo(lowerNote) >= 0; noteIterator = noteIterator.getPrevious(NoteAccidental.NATURAL)){
            noteComboBox.addItem(noteIterator);
            if (noteIterator.equals(selectedNote)){
                noteComboBox.setSelectedItem(noteIterator);
            }
        }
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {
        ComboBoxModel<Note> listModel = noteComboBox.getModel();
        Note selectedNote = listModel.getElementAt(noteComboBox.getSelectedIndex());
        noteLimitModel.changeLimit(selectedNote);
    }
}