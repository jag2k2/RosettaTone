package com.jag2k2.uicomponents.renderer.grandstaff;

import com.jag2k2.uicomponents.renderer.records.ClefImages;

import java.awt.*;
import java.util.Set;

public interface StaffModeDrawable {
    void draw(Graphics2D graphics2D, ClefImages clefImages);
    Set<Integer> getLedgerLines(int lineNumber);
}
