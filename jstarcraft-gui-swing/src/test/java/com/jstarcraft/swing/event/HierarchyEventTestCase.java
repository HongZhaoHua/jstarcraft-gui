package com.jstarcraft.swing.event;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.junit.Assert;
import org.junit.Test;

public class HierarchyEventTestCase {

    @Test
    public void testHierarchyEvent() throws Exception {
        EventQueue.invokeAndWait(() -> {
            JFrame frame = new JFrame("testHierarchyEvent");
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            // 适配大小
            frame.pack();
            // 窗体居中
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            MockHierarchyListener listener = new MockHierarchyListener();
            panel.addHierarchyListener(listener);

            // 不会触发任何事件
            panel.setVisible(false);
            Assert.assertTrue(listener.check(false, false, false));
            panel.setVisible(true);
            Assert.assertTrue(listener.check(false, false, false));

            listener.reset();
            // 触发Hierarchy变化
            frame.setContentPane(panel);
            Assert.assertTrue(listener.check(true, false, false));
            // 触发Display变化
            frame.pack();
            Assert.assertTrue(listener.check(true, true, false));
            // 触发Visible变化
            frame.setVisible(true);
            Assert.assertTrue(listener.check(true, true, true));

            listener.reset();
            // 不触发Visible变化
            panel.setVisible(true);
            Assert.assertTrue(listener.check(false, false, false));
            // 触发Visible变化
            panel.setVisible(false);
            Assert.assertTrue(listener.check(false, false, true));
            listener.reset();
            // 不触发Visible变化
            panel.setVisible(false);
            Assert.assertTrue(listener.check(false, false, false));
            // 触发Visible变化
            panel.setVisible(true);
            Assert.assertTrue(listener.check(false, false, true));

            listener.reset();
            // 触发Visible变化
            frame.setVisible(false);
            Assert.assertTrue(listener.check(false, false, true));
            // 触发Display变化
            frame.dispose();
            Assert.assertTrue(listener.check(false, true, true));
            // 触发Hierarchy变化
            frame.setContentPane(new JPanel());
            Assert.assertTrue(listener.check(true, true, true));
        });
    }

}
