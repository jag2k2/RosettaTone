package uicomponents.renderer;

import java.awt.image.BufferedImage;

public interface ImageLoader {
    BufferedImage getNoteImage();
    BufferedImage getSharpImage();
    BufferedImage getNaturalImage();
    BufferedImage getFlatImage();
}
