package com.jag2k2.uicomponents.staffmode;

import javax.swing.*;

public interface StaffModeModifier {
    void setMode(StaffMode staffMode);
    void setUISelected(JComboBox<StaffMode> comboBox);
}
