package uicomponents.clefmode;

import javax.swing.*;

public interface ClefModeModifier {
    void setState(ClefMode clefMode);
    void setUISelected(JComboBox<ClefMode> comboBox);
}
