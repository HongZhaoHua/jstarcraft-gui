package com.jstarcraft.swing.model;

import java.util.Comparator;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 * 可排序树节点
 * 
 * @author Birdy
 *
 */
// 参考SortTree
public class SortableTreeNode extends DefaultMutableTreeNode {

    private final Comparator<TreeNode> comparator;

    public SortableTreeNode(Comparator<TreeNode> comparator) {
        this(comparator, null);
    }

    public SortableTreeNode(Comparator<TreeNode> comparator, Object data) {
        this(comparator, data, true);
    }

    public SortableTreeNode(Comparator<TreeNode> comparator, Object data, boolean allowed) {
        super(data, allowed);
        this.comparator = comparator;
    }

    public void sort() {
        children.sort(comparator);
    }

}
