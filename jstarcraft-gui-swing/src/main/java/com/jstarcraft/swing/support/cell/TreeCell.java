package com.jstarcraft.swing.support.cell;

import java.util.Objects;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.jstarcraft.swing.support.DataCell;

/**
 * 树单元
 * 
 * @author Birdy
 *
 */
public class TreeCell<T> implements DataCell<T> {

    private JTree tree;

    private DefaultMutableTreeNode node;

    public TreeCell(JTree tree, DefaultMutableTreeNode node) {
        this.tree = tree;
        this.node = node;
    }

    @Override
    public boolean isSelected() {
        TreePath path = new TreePath(node.getPath());
        return tree.isPathSelected(path);
    }

    @Override
    public boolean isEditing() {
        TreePath path = new TreePath(node.getPath());
        return Objects.equals(path, tree.getEditingPath());
    }

    @Override
    public void startEditing(Runnable runable) {
        TreePath path = new TreePath(node.getPath());
        tree.startEditingAtPath(path);
        runable.run();
    }

    @Override
    public void stopEdting(boolean cancel, Runnable runable) {
        if (cancel) {
            tree.cancelEditing();
        } else {
            tree.stopEditing();
        }
        runable.run();
    }

    @Override
    public T getData() {
        return (T) node.getUserObject();
    }

    @Override
    public void setData(Object data) {
        if (isEditing()) {
            throw new IllegalStateException();
        }
        TreePath path = new TreePath(node.getPath());
        tree.getModel().valueForPathChanged(path, data);
    }

    public boolean isToggle() {
        TreePath path = new TreePath(node.getPath());
        return tree.isExpanded(path);
    }

    public void setToggle(boolean toggle) {
        TreePath path = new TreePath(node.getPath());
        if (toggle) {
            tree.expandPath(path);
        } else {
            tree.collapsePath(path);
        }
    }

    public boolean isLeaf() {
        return node.isLeaf();
    }

}
