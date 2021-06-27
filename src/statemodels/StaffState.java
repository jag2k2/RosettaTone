package statemodels;

import uicomponents.staffselector.StaffOptions;

public interface StaffState {
    void setSelection(StaffOptions staffSelection);
    StaffOptions getSelection();
    boolean trebleEnabled();
    boolean bassEnabled();
}
