package uicomponents.renderer;

import imageprocessing.StaffImage;

import java.awt.*;
import java.util.Set;

public interface StaffModeDrawable {
    void draw(Graphics2D graphics2D, StaffImage trebleImage, StaffImage bassImage);
    Set<Integer> getLedgerLines(int lineNumber);
}
