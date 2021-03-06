package com.jstarcraft.swing.cell;

import java.util.Objects;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * 树单元格
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
        TreePath path = getPath();
        return tree.isPathSelected(path);
    }

    @Override
    public boolean isEditing() {
        TreePath path = getPath();
        return Objects.equals(path, tree.getEditingPath());
    }

    @Override
    public void startEditing(Runnable runable) {
        TreePath path = getPath();
        tree.startEditingAtPath(path);
        if (runable != null) {
            runable.run();
        }
    }

    @Override
    public void stopEditing(boolean cancel, Runnable runable) {
        if (cancel) {
            tree.cancelEditing();
        } else {
            tree.stopEditing();
        }
        if (runable != null) {
            runable.run();
        }
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
        TreePath path = getPath();
        tree.getModel().valueForPathChanged(path, data);
    }

    public int getIndex() {
        TreePath path = getPath();
        return tree.getRowForPath(path);
    }

    public boolean isToggle() {
        TreePath path = getPath();
        return tree.isExpanded(path);
    }

    public void setToggle(boolean toggle) {
        TreePath path = getPath();
        if (toggle) {
            tree.expandPath(path);
        } else {
            tree.collapsePath(path);
        }
    }

    public boolean isLeaf() {
        return node.isLeaf();
    }

    public TreePath getPath() {
        return new TreePath(node.getPath());
    }

}
