package uicomponents.staffmode;

import javax.swing.*;

public interface StaffModeModifier {
    void setMode(StaffMode staffMode);
    void setUISelected(JComboBox<StaffMode> comboBox);
}
