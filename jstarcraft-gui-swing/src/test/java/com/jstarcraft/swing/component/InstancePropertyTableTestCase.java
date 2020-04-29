package com.jstarcraft.swing.component;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import org.junit.Assert;
import org.junit.Test;

import com.jstarcraft.swing.MockInstance;
import com.jstarcraft.swing.model.SingleInstanceTableModel;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

public class InstancePropertyTableTestCase {

    @Test
    public void testAccessProperty() throws Exception {
        MockInstance instance = new MockInstance();
        instance.setTitle("Mickey");
        SingleInstanceTableModel<MockInstance> model = new SingleInstanceTableModel<>(instance, MockInstance.class, "title", "marks");
        Assert.assertEquals("Mickey", model.getValueAt(0, 0));
        Assert.assertNull(model.getValueAt(1, 0));

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        InstancePropertyTable<MockInstance> table = new InstancePropertyTable<>(model);
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        // 适配大小
        frame.pack();
        // 窗体居中
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Thread.sleep(5000);
        EventQueue.invokeAndWait(() -> {
            model.setValueAt("JStarCraft", 0, 0);
            model.setValueAt(new Int2IntOpenHashMap(), 1, 0);
            Assert.assertEquals("JStarCraft", model.getValueAt(0, 0));
            Assert.assertNotNull(model.getValueAt(1, 0));
        });
        Thread.sleep(5000);
    }

}
