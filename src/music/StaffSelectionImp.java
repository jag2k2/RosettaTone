package music;

import uicomponents.staffselector.StaffOptions;

public class StaffSelectionImp implements StaffSelection {
    private StaffOptions staffSelection;

    public StaffSelectionImp(StaffOptions staffSelection){
        this.staffSelection = staffSelection;
    }

    @Override
    public void setSelection(StaffOptions staffSelection){
        this.staffSelection = staffSelection;
    }

    @Override
    public StaffOptions getSelection(){
        return staffSelection;
    }

    @Override
    public boolean trebleEnabled() {
        return (staffSelection == StaffOptions.Treble || staffSelection == StaffOptions.Grand);
    }

    @Override
    public boolean bassEnabled() {
        return (staffSelection == StaffOptions.Bass || staffSelection == StaffOptions.Grand);
    }
}
