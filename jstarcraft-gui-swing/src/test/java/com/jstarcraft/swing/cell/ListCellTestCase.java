package com.jstarcraft.swing.cell;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.junit.Assert;
import org.junit.Test;

import com.jstarcraft.swing.cell.ListCell;

public class ListCellTestCase {

    @Test
    public void test() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (int index = 0; index < 10; index++) {
            model.addElement("element" + index);
        }
        JList<String> list = new JList<>(model);
        ArrayList<ListCell<String>> cells = new ArrayList<>(10);
        for (int index = 0, size = model.size(); index < size; index++) {
            ListCell<String> cell = new ListCell<>(list, index);
            cells.add(cell);
        }

        AtomicInteger count = new AtomicInteger();
        for (int index = 0, size = cells.size(); index < size; index++) {
            ListCell<String> cell = cells.get(index);
            Assert.assertEquals(index, cell.getIndex());
            Assert.assertFalse(cell.isSelected());
            list.setSelectedIndex(index);
            Assert.assertTrue(cell.isSelected());

            // 启动编辑
            try {
                cell.startEditing(count::incrementAndGet);
                Assert.fail();
            } catch (UnsupportedOperationException exception) {
            }
            Assert.assertFalse(cell.isEditing());
            // 停止编辑
            try {
                cell.stopEditing(false, count::incrementAndGet);
                Assert.fail();
            } catch (UnsupportedOperationException exception) {
            }
            Assert.assertFalse(cell.isEditing());
            
            cell.setData("data");
            Assert.assertEquals("data", cell.getData());
        }
        Assert.assertEquals(0, count.get());
    }

}
