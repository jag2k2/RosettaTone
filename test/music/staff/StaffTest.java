package music.staff;

import org.junit.jupiter.api.BeforeEach;
import uicomponents.renderer.records.RenderConstants;

class StaffTest {
    private Staff trebleStaff;
    private Staff bassStaff;

    @BeforeEach
    void setup(){
        trebleStaff = new Staff(RenderConstants.trebleStaff);
        bassStaff = new Staff(RenderConstants.bassStaff);
    }
}