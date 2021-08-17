package application;

import javax.swing.*;
import uicomponents.*;

import java.awt.*;

public class RosettaTone {
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        SwingUtilities.invokeLater(new RosettaTone.InitializeJob());
    }

    static class InitializeJob implements Runnable {
        @Override
        public void run() {
            MainGUI mainGui = new MainGUI();

            JFrame frame = new JFrame();
            frame.setTitle("Rosetta Tone");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(mainGui);
            frame.setLocation(10, 10);
            frame.setMinimumSize(new Dimension(500, 500));
            frame.pack();
            frame.setVisible(true);
        }
    }
}