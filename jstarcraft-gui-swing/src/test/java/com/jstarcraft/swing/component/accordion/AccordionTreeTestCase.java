package com.jstarcraft.swing.component.accordion;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Window;
import java.util.stream.IntStream;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.junit.Assert;
import org.junit.Test;

import com.jstarcraft.core.utility.KeyValue;
import com.jstarcraft.swing.component.dnd.DragDropScrollPane;
import com.jstarcraft.swing.component.skin.SwitchSkinMemu;

public class AccordionTreeTestCase {

    @Test
    public void testAccordion() throws Exception {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new KeyValue<>("root", false));
        DefaultMutableTreeNode first = new DefaultMutableTreeNode(new KeyValue<>("first", false));
        DefaultMutableTreeNode last = new DefaultMutableTreeNode(new KeyValue<>("last", false));
        root.add(first);
        root.add(last);
        IntStream.rangeClosed(1, 5).forEach((index) -> {
            KeyValue<String, Boolean> keyValue = new KeyValue<>(String.valueOf(index), index % 2 == 0);
            first.add(new DefaultMutableTreeNode(keyValue));
        });
        DefaultTreeModel model = new DefaultTreeModel(root);
        AccordionTree tree = new AccordionTree(model);
        AccordionTreeCellSupporter<KeyValue<String, Boolean>> supporter = new AccordionTreeCellSupporter<>(new SupportPanel(), new SupportPanel());
        tree.setCellRenderer(supporter);
        tree.setCellEditor(supporter);
        tree.addPropertyChangeListener("UI", supporter);
        tree.setEditable(true);
        tree.setInvokesStopCellEditing(true);

        Assert.assertEquals(3, tree.getRowCount());
        DragDropScrollPane panel = new DragDropScrollPane(tree);

        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        SwitchSkinMemu memu = new SwitchSkinMemu("切换皮肤", Window.getWindows());
        menuBar.add(memu);
        frame.getRootPane().setJMenuBar(menuBar);
        // 适配大小
        frame.pack();
        // 窗体居中
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Thread.sleep(1000);
        EventQueue.invokeAndWait(() -> {
            root.setUserObject(new KeyValue<>("title", true));
            model.nodeChanged(root);
        });
        Thread.sleep(1000);
        EventQueue.invokeAndWait(() -> {
            Dimension dimension = new Dimension(500, 500);
            tree.setSize(dimension);
        });
        Thread.sleep(1000);
        TreePath path = tree.getPathForRow(0);
        Rectangle bound = tree.getPathBounds(path);
        Assert.assertEquals(500, bound.width);
        EventQueue.invokeAndWait(() -> {
            IntStream.rangeClosed(6, 10).forEach((index) -> {
                KeyValue<String, Boolean> keyValue = new KeyValue<>(String.valueOf(index), index % 2 == 0);
                last.insert(new DefaultMutableTreeNode(keyValue), 0);
            });
            model.nodeStructureChanged(root);
        });
        Assert.assertEquals(3, tree.getRowCount());
        Thread.sleep(5000);
    }

}
