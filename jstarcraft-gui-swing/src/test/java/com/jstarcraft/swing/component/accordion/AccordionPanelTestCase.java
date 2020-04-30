package com.jstarcraft.swing.component.accordion;

import java.awt.EventQueue;
import java.util.stream.IntStream;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.junit.Assert;
import org.junit.Test;

import com.jstarcraft.core.utility.KeyValue;

public class AccordionPanelTestCase {

    @Test
    public void testAccordion() throws Exception {
        DefaultListModel<Integer> model = new DefaultListModel<>();
        IntStream.rangeClosed(1, 5).forEach(model::addElement);
        AccordionPanel<Integer> panel = new AccordionPanel<>(model, (data) -> {
            MockTitlePanel title = new MockTitlePanel();
            title.setData(data);
            return title;
        }, (data) -> {
            MockContentPanel content = new MockContentPanel();
            content.setData(new KeyValue<>("Content", data));
            return content;
        });
        Assert.assertEquals(5, panel.getCellSize());

        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // 适配大小
        frame.pack();
        // 窗体居中
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        for (int index = 0; index < 5; index++) {
            Thread.sleep(1000);
            Assert.assertFalse(panel.isCellToggle(index));
            int cell = index;
            EventQueue.invokeAndWait(() -> {
                panel.setCellToggle(cell, true);
            });
            Assert.assertTrue(panel.isCellToggle(index));
        }
        EventQueue.invokeAndWait(() -> {
            IntStream.rangeClosed(6, 10).forEach(model::addElement);
            Assert.assertEquals(10, panel.getCellSize());
        });
        Thread.sleep(5000);
    }

}
