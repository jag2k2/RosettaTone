package uicomponents.alphabetmode;

import uicomponents.UIComponent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlphabetModeSelectorImp implements UIComponent, ActionListener {
    private final JComboBox<AlphabetMode> comboBox;
    private final AlphabetModeModifier alphabetModeModifier;

    public AlphabetModeSelectorImp(AlphabetModeModifier alphabetModeModifier){
        this.comboBox = new JComboBox<>(AlphabetMode.values());
        this.alphabetModeModifier = alphabetModeModifier;
        this.comboBox.setRenderer(new AlphabetModeRenderer(comboBox.getRenderer()));
        comboBox.addActionListener(this);
    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel();
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setBorder(border);
        Dimension boxSize = new Dimension(100, 40);
        comboBox.setPreferredSize(boxSize);
        panel.add(comboBox);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = comboBox.getSelectedIndex();
        AlphabetMode selectedMode = comboBox.getItemAt(selectedIndex);
        alphabetModeModifier.setMode(selectedMode);
    }
}
