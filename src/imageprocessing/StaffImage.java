package imageprocessing;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class StaffImage {
    private BufferedImage bufferedImage;

    public StaffImage(File file, double scaleFactor){
        try {
            this.bufferedImage = ImageIO.read(file);
        } catch (IOException exception){
            exception.printStackTrace();
        }
        resize(scaleFactor);
    }

    public BufferedImage getBufferedImage(){
        return bufferedImage;
    }

    protected void resize(double scaleFactor){
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
}
