package music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uicomponents.renderer.records.RenderConstants;

import static org.junit.jupiter.api.Assertions.*;

class StaffTest {
    private Staff trebleStaff;
    private Staff bassStaff;

    @BeforeEach
    void setup(){
        trebleStaff = new Staff(RenderConstants.trebleStaff);
        bassStaff = new Staff(RenderConstants.bassStaff);
    }
}