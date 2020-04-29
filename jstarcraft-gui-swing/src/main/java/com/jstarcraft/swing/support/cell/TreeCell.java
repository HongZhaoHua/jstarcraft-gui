package com.jstarcraft.swing.support.cell;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import com.jstarcraft.swing.support.SupportCell;

/**
 * 树单元
 * 
 * @author Birdy
 *
 */
public class TreeCell implements SupportCell {

    private JTree tree;

    private MutableTreeNode parent;

    private MutableTreeNode child;

    private int number;

    private int index;

    public TreeCell(JTree tree, MutableTreeNode node, int index) {
        this.tree = tree;
        this.child = node;
        this.parent = (MutableTreeNode) this.child.getParent();
        if (this.parent != null) {
            this.number = this.parent.getIndex(this.child);
        }
        this.index = index;
    }

    @Override
    public void attach() {
        if (this.parent != null && parent.getIndex(child) == -1) {
            parent.insert(child, number);
            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            model.reload(parent);
        }
    }

    @Override
    public void detach() {
        if (this.parent != null && parent.getIndex(child) != -1) {
            child.removeFromParent();
            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            model.reload(parent);
        }
    }

    @Override
    public void cancel() {
        tree.getCellEditor().cancelCellEditing();
    }

    @Override
    public void complete() {
        tree.getCellEditor().stopCellEditing();
    }

    @Override
    public boolean selected() {
        if (parent.getIndex(child) != -1) {
            return tree.isPathSelected(tree.getPathForRow(index));
        }
        return false;
    }

    public int getIndex() {
        if (parent.getIndex(child) != -1) {
            return index;
        }
        return -1;
    }

    public boolean isToggle() {
        if (this.parent == null || parent.getIndex(child) != -1) {
            return tree.isExpanded(index);
        }
        return false;
    }

    public void setToggle(boolean toggle) {
        if (toggle) {
            tree.expandRow(index);
        } else {
            tree.collapseRow(index);
        }
    }

}
