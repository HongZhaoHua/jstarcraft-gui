package com.jstarcraft.swing.component.accordion;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.TreeNode;

import com.jstarcraft.swing.component.CellPanel;
import com.jstarcraft.swing.support.TreeCellSupporter;
import com.jstarcraft.swing.support.cell.TreeCell;

public class AccordionTreeCellSupporter<D> extends TreeCellSupporter<D> {

    public AccordionTreeCellSupporter(CellPanel<TreeCell<D>, D> renderPanel, CellPanel<TreeCell<D>, D> editPanel) {
        super(renderPanel, editPanel);
    }

    /**
     * 获取节点深度
     * 
     * @param node
     * @return
     */
    protected int getNodeDepth(TreeNode node) {
        int level = 0;
        while (node != null) {
            node = node.getParent();
            level++;
        }
        return level;
    }

    /**
     * 获取树宽度
     * 
     * @param tree
     * @return
     */
    protected int getTreeWidth(JTree tree) {
        int width = tree.getWidth();
        return width;
    }

    /**
     * 计算面板大小
     * 
     * @param panel
     * @param tree
     * @param node
     */
    protected void calculatePanelSize(JPanel panel, JTree tree, TreeNode node) {
        Dimension dimension = panel.getPreferredSize();
        int depth = getNodeDepth(node);
        Integer leftIndent = (Integer) UIManager.get("Tree.leftChildIndent");
        Integer rightIndent = (Integer) UIManager.get("Tree.rightChildIndent");
        int width = getTreeWidth(tree);
        if (width <= 0) {
            width = dimension.width;
        }
        if (tree.isRootVisible()) {
            dimension.width = width - (depth - 1) * (leftIndent + rightIndent);
        } else {
            dimension.width = width - (depth - 2) * (leftIndent + rightIndent);
        }
        panel.setPreferredSize(dimension);
    }

    @Override
    public CellPanel<TreeCell<D>, D> getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int index, boolean focused) {
        CellPanel<TreeCell<D>, D> panel = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, index, focused);
        // 关键代码
        // 使渲染器与树的宽度保持一致
        calculatePanelSize(panel, tree, (TreeNode) value);
        return panel;
    }

    @Override
    public CellPanel<TreeCell<D>, D> getTreeCellEditorComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int index) {
        CellPanel<TreeCell<D>, D> panel = super.getTreeCellEditorComponent(tree, value, selected, expanded, leaf, index);
        // 关键代码
        // 使编辑器与树的宽度保持一致
        calculatePanelSize(panel, tree, (TreeNode) value);
        return panel;
    }

}
