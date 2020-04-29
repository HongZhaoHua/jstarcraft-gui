package com.jstarcraft.swing.cell;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.junit.Assert;
import org.junit.Test;

import com.jstarcraft.swing.support.cell.TreeCell;

public class TreeCellTestCase {

    @Test
    public void test() {
        ArrayList<DefaultMutableTreeNode> nodes = new ArrayList<>(10);
        DefaultMutableTreeNode parent = new DefaultMutableTreeNode("parent");
        nodes.add(parent);
        for (int index = 0; index < 9; index++) {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode("child" + index);
            parent.add(child);
            nodes.add(child);
        }
        DefaultTreeModel model = new DefaultTreeModel(parent);
        JTree tree = new JTree(model);
        ArrayList<TreeCell<String>> cells = new ArrayList<>(10);
        for (int index = 0, size = nodes.size(); index < size; index++) {
            DefaultMutableTreeNode node = nodes.get(index);
            TreeCell<String> cell = new TreeCell<>(tree, node);
            cells.add(cell);
        }

        // 设置树为可编辑
        tree.setEditable(true);
        AtomicInteger count = new AtomicInteger();
        // 显示根节点
        tree.setRootVisible(true);
        for (int index = 0, size = cells.size(); index < size; index++) {
            TreeCell<String> cell = cells.get(index);
            Assert.assertEquals(index, cell.getIndex());
            Assert.assertFalse(cell.isSelected());
            tree.getSelectionModel().setSelectionPath(cell.getPath());
            Assert.assertTrue(cell.isSelected());

            // 启动编辑
            cell.startEditing(count::incrementAndGet);
            Assert.assertTrue(cell.isEditing());
            // 停止编辑
            cell.stopEditing(false, count::incrementAndGet);
            Assert.assertFalse(cell.isEditing());
        }
        Assert.assertEquals(20, count.get());
        // 隐藏根节点
        tree.setRootVisible(false);
        for (int index = 0, size = cells.size(); index < size; index++) {
            TreeCell<String> cell = cells.get(index);
            Assert.assertEquals(index - 1, cell.getIndex());
            if (index == 0) {
                Assert.assertTrue(cell.isToggle());
            } else {
                Assert.assertFalse(cell.isToggle());
            }
            cell.setData("data");
            Assert.assertEquals("data", cell.getData());
        }
    }

}
