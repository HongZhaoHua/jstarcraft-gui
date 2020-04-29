package com.jstarcraft.swing.component;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.IllegalComponentStateException;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import org.junit.Assert;
import org.junit.Test;

public class FrameTestCase {

    @Test
    public void testDecorate() throws Exception {
        // setDefaultLookAndFeelDecorated会影响所有JFrame,效果相当于:
        // 1.frame.setUndecorated(true);
        // 2.frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("testDecorate");
        frame.setPreferredSize(new Dimension(1000, 1000));
        // 适配大小
        frame.pack();
        // 窗体居中
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Assert.assertTrue(frame.isUndecorated());
        Thread.sleep(5000);
        try {
            // displayable的JFrame不可以设置setUndecorated
            frame.setUndecorated(false);
            Assert.fail();
        } catch (IllegalComponentStateException exception) {
        }
        frame.dispose();
        frame.setUndecorated(false);
        Assert.assertFalse(frame.isUndecorated());
        // 注意setUndecorated与setWindowDecorationStyle的区别
        frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        frame.setResizable(true);
        frame.setVisible(true);
        Thread.sleep(5000);
    }

}
