package com.jstarcraft.swing.component.accordion;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.Instant;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.plaf.IconUIResource;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.jstarcraft.swing.component.empty.EmptyIcon;

/**
 * 折叠树
 * 
 * @author Birdy
 *
 */
// 参考TableOfContentsTree,ToggleNodeTree,TreeChildIndent,TreeLineStyle,NimbusTreeLinesStyle,TreeLineColor,TreeExpandedIcon
public class AccordionTree extends JTree {

    private static final Icon emptyIcon = new EmptyIcon();

    // 关键代码
    // 使节点与树的宽度保持一致
    private class ResizeHandler extends ComponentAdapter {

        private Instant ignore = Instant.now();

        @Override
        public void componentResized(ComponentEvent event) {
            // 忽略50毫秒,防止循环触发大小变更
            Instant now = Instant.now();
            if (now.isAfter(ignore)) {
                ignore = now.plusMillis(50);
                firePropertyChange(JTree.CELL_RENDERER_PROPERTY, null, getCellRenderer());
            }
        }

    }

    private void configure() {
        ResizeHandler resizeHandler = new ResizeHandler();
        this.addComponentListener(resizeHandler);

        this.putClientProperty("JTree.lineStyle", "None");
    }

    public AccordionTree() {
        super();
        this.configure();
    }

    public AccordionTree(Hashtable<?, ?> data) {
        super(data);
        this.configure();
    }

    public AccordionTree(Object[] data) {
        super(data);
        this.configure();
    }

    public AccordionTree(Vector<?> data) {
        super(data);
        this.configure();
    }

    public AccordionTree(TreeModel model) {
        super(model);
        this.configure();
    }

    public AccordionTree(TreeNode root) {
        super(root);
        this.configure();
    }

    public AccordionTree(TreeNode root, boolean allowed) {
        super(root, allowed);
        this.configure();
    }

    @Override
    public void updateUI() {
        Object expandedIcon = UIManager.get("Tree.expandedIcon");
        Object collapsedIcon = UIManager.get("Tree.collapsedIcon");
        UIManager.put("Tree.expandedIcon", new IconUIResource(emptyIcon));
        UIManager.put("Tree.collapsedIcon", new IconUIResource(emptyIcon));
        super.updateUI();
        UIManager.put("Tree.expandedIcon", expandedIcon);
        UIManager.put("Tree.collapsedIcon", collapsedIcon);
    }

}
