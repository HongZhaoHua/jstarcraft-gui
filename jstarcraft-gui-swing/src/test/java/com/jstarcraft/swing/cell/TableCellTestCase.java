package com.jstarcraft.swing.cell;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.junit.Assert;
import org.junit.Test;

import com.jstarcraft.swing.cell.TableCell;

public class TableCellTestCase {

    @Test
    public void test() {
        DefaultTableModel model = new DefaultTableModel(3, 3);
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                model.setValueAt(row + "-" + column, row, column);
            }
        }

        JTable table = new JTable(model);
        ArrayList<TableCell<String>> cells = new ArrayList<>(10);
        for (int row = 0; row < model.getRowCount(); row++) {
            for (int column = 0; column < model.getColumnCount(); column++) {
                TableCell<String> cell = new TableCell<>(table, row, column);
                cells.add(row * 3 + column, cell);
            }
        }

        table.setCellSelectionEnabled(true);
        AtomicInteger count = new AtomicInteger();
        for (int row = 0; row < model.getRowCount(); row++) {
            for (int column = 0; column < model.getColumnCount(); column++) {
                TableCell<String> cell = cells.get(row * 3 + column);
                Assert.assertEquals(row, cell.getRow());
                Assert.assertEquals(column, cell.getColumn());
                Assert.assertFalse(cell.isSelected());
                table.getSelectionModel().setSelectionInterval(row, row);
                table.getColumnModel().getSelectionModel().setSelectionInterval(column, column);
                Assert.assertTrue(cell.isSelected());

                // 启动编辑
                cell.startEditing(count::incrementAndGet);
                Assert.assertTrue(cell.isEditing());
                // 停止编辑
                cell.stopEditing(false, count::incrementAndGet);
                Assert.assertFalse(cell.isEditing());

                cell.setData("data");
                Assert.assertEquals("data", cell.getData());
            }
        }
        Assert.assertEquals(18, count.get());
    }

}
