package com.jag2k2.uicomponents.rangeselector.noteselector;

import com.jag2k2.music.Note;
import com.jag2k2.notification.LimitChangeObserver;
import com.jag2k2.uicomponents.UIComponent;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Objects;

public class NoteSelectorImp implements UIComponent, ActionListener, PopupMenuListener, LimitChangeObserver {
    private static final String upButtonName = "upButton";
    private static final String downButtonName = "downButton";
    private final BoundedNoteModifier boundedNoteModel;
    private final LimitModifier previewLimit;
    private final JComboBox<Note> noteComboBox;
    private JButton upButton;
    private JButton downButton;

    public NoteSelectorImp(BoundedNoteModifier boundedNoteModel, NoteListRenderer noteListRenderer, LimitModifier previewLimit){
        this.boundedNoteModel = boundedNoteModel;
        this.previewLimit = previewLimit;
        this.noteComboBox = new JComboBox<>();
        this.noteComboBox.setRenderer(noteListRenderer);
        try{
            URL upArrowURL = getClass().getResource("UpArrow.png");
            URL downArrowURL = getClass().getResource("DownArrow.png");
            Image upArrowImage = ImageIO.read(Objects.requireNonNull(upArrowURL));
            Image downArrowImage = ImageIO.read(Objects.requireNonNull(downArrowURL));
            ImageIcon upArrowIcon = new ImageIcon(upArrowImage);
            ImageIcon downArrowIcon = new ImageIcon(downArrowImage);
            this.upButton = new JButton(upArrowIcon);
            this.downButton = new JButton(downArrowIcon);
        } catch (Exception e) {
            this.upButton = new JButton();
            this.downButton = new JButton();
        }

        this.upButton.setName("upButton");
        this.downButton.setName("downButton");

        this.noteComboBox.addPopupMenuListener(this);

        this.noteComboBox.addActionListener(this);
        this.upButton.addActionListener(this);
        this.downButton.addActionListener(this);
    }

    @Override
    public Component getComponent(){
        JPanel panel = new JPanel(new BorderLayout());
        Dimension boxSize = new Dimension(80, 40);
        noteComboBox.setPreferredSize(boxSize);
        panel.add(BorderLayout.NORTH, upButton);
        panel.add(BorderLayout.CENTER, noteComboBox);
        panel.add(BorderLayout.SOUTH, downButton);
        boundedNoteModel.refreshJComboBoxOptions(noteComboBox);
        return panel;
    }

    @Override
    public void limitChanged() {
        boundedNoteModel.refreshJComboBoxOptions(noteComboBox);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("comboBoxChanged")){
            if (noteComboBox.getSelectedIndex() > 0) {
                Note selectedNote = (Note) noteComboBox.getSelectedItem();
                boundedNoteModel.setLimit(selectedNote);
            }
        }
        else if (e.getSource() instanceof JButton){
            JButton pushedButton = (JButton) e.getSource();
            if (pushedButton.getName().equals(upButtonName)){
                boundedNoteModel.increment();

            }
            else if (pushedButton.getName().equals(downButtonName)){
                boundedNoteModel.decrement();
            }
            previewLimit.setLimit(boundedNoteModel.getLimit());
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
        boundedNoteModel.setLimit(selectedNote);
        previewLimit.setLimit(boundedNoteModel.getLimit());
    }
}
