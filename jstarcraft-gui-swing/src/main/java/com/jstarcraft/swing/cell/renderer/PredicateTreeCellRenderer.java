package com.jstarcraft.swing.cell.renderer;

import java.awt.Component;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

/**
 * 断言树渲染器
 * 
 * @author Birdy
 *
 */
// 参考TreeNodeFilter
public class PredicateTreeCellRenderer implements TreeCellRenderer {

    private final JLabel emptyLabel = new JLabel();

    /** 断言 */
    private Predicate nodePredicate;

    /** 渲染器 */
    private Supplier<TreeCellRenderer> rendererSupplier;

    public PredicateTreeCellRenderer(Predicate predicate, Supplier<TreeCellRenderer> rendererSupplier) {
        this.nodePredicate = predicate;
        this.rendererSupplier = rendererSupplier;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int index, boolean focused) {
        if (nodePredicate.test(value)) {
            return rendererSupplier.get().getTreeCellRendererComponent(tree, value, selected, expanded, leaf, index, focused);
        } else {
            return emptyLabel;
        }
    }

}
