package com.jag2k2;

import com.jag2k2.uicomponents.*;
import javax.swing.*;

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
            mainGui.launch();
        }
    }
}