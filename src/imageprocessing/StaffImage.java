package imageprocessing;

import java.awt.*;
import java.awt.image.*;

public class StaffImage {
    private BufferedImage bufferedImage;

    public StaffImage(BufferedImage bufferedImage){
        Image transparentImage = makeWhiteTransparent(bufferedImage);
        this.bufferedImage = convertImageToBufferedImage(transparentImage, bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    public BufferedImage getBufferedImage(){
        return bufferedImage;
    }

    public void resize(double scaleFactor){
        int newWidth = (int) (bufferedImage.getWidth() * scaleFactor);
        int newHeight = (int) (bufferedImage.getHeight() * scaleFactor);
        Image scaledImage = bufferedImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
        bufferedImage = convertImageToBufferedImage(scaledImage, newWidth, newHeight);
    }

    protected BufferedImage convertImageToBufferedImage(Image image, int width, int height)
    {
        BufferedImage destinationImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = destinationImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return destinationImage;
    }

    protected Image makeWhiteTransparent(BufferedImage bufferedImage) {
        ImageFilter filter = new RGBImageFilter() {
            @Override
            public int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == 0xFFFFFFFF) {
                    return 0x00FFFFFF;
                }
                return rgb;
            }
        };
        ImageProducer ip = new FilteredImageSource(bufferedImage.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }
}
