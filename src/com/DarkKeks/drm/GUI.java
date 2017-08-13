package com.DarkKeks.drm;

import javax.swing.*;
import java.awt.*;

public class GUI {
    public static void showMessage(String from, String text){
        Toolkit.getDefaultToolkit().beep();
        JOptionPane optionPane = new JOptionPane("From: " + from + "\n" + text, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog("Message");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }
}
