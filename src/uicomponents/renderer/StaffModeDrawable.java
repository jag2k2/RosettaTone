package uicomponents.renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Set;

public interface StaffModeDrawable {
    void draw(Graphics2D graphics2D, BufferedImage trebleImage, BufferedImage bassImage);
    Set<Integer> getLedgerLines(int lineNumber);
}
