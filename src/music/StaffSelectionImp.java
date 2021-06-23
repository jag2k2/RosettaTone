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
}
