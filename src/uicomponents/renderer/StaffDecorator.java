package uicomponents.renderer;

import music.Staff;
import java.util.Set;

public interface StaffDecorator {
    Set<Staff> getEnabledStaffs();
    Set<Integer> getHelperLines(int lineNumber);
}
