package com.jstarcraft.swing.support;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;
import java.util.LinkedHashSet;

import javax.swing.AbstractCellEditor;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jstarcraft.swing.component.CellPanel;
import com.jstarcraft.swing.support.cell.TreeCell;

/**
 * 树单元支撑器
 * 
 * @author Birdy
 *
 */
// 参考CheckBoxNodeEditor,MultipleButtonsInTreeNode
public abstract class TreeCellSupporter<D> extends AbstractCellEditor implements TreeCellRenderer, TreeCellEditor, PropertyChangeListener {

    protected static final Logger logger = LoggerFactory.getLogger(TreeCellSupporter.class);

    /** 渲染面板 */
    protected CellPanel<TreeCell<D>, D> renderPanel;

    /** 编辑面板 */
    protected CellPanel<TreeCell<D>, D> editPanel;

    protected ChangeListener innerListener = (event) -> {
        triggerCellListeners();
    };

    protected LinkedHashSet<ChangeListener> outerListeners = new LinkedHashSet<>();

    public TreeCellSupporter(CellPanel<TreeCell<D>, D> renderPanel, CellPanel<TreeCell<D>, D> editPanel) {
        this.renderPanel = renderPanel;
        this.editPanel = editPanel;
    }

    @Override
    public CellPanel<TreeCell<D>, D> getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int index, boolean focused) {
        TreeCell<D> cell = new TreeCell<>(tree, (DefaultMutableTreeNode) value);
        renderPanel.setData(cell);
        return renderPanel;
    }

    @Override
    public boolean isCellEditable(EventObject event) {
        if (logger.isDebugEnabled()) {
            String message = event.toString();
            logger.debug(message);
        }

        if (event.getSource() instanceof JTree && event instanceof MouseEvent) {
            JTree tree = (JTree) event.getSource();
            Point point = ((MouseEvent) event).getPoint();
            TreePath path = tree.getPathForLocation(point.x, point.y);
            Rectangle bound = tree.getPathBounds(path);
            if (bound.contains(point)) {
                int index = tree.getRowForPath(path);
                Object value = path.getLastPathComponent();
                boolean selected = tree.isRowSelected(index);
                boolean expanded = tree.isExpanded(path);
                boolean leaf = tree.getModel().isLeaf(value);
                point.translate(-bound.x, -bound.y);
                CellPanel<TreeCell<D>, D> panel = getTreeCellRendererComponent(tree, value, selected, expanded, leaf, index, true);
                // 因为渲染器与编辑器不在组件树之中,所以不能使用SwingUtilities.convertPoint
                panel.setLocation(0, 0);
                panel.setSize(bound.getSize());
                // 判断坐标是否在编辑区域
                return panel.isVariable(point);
            }
        }
        return false;
    }

    @Override
    public CellPanel<TreeCell<D>, D> getTreeCellEditorComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int index) {
        TreeCell<D> cell = new TreeCell<>(tree, (DefaultMutableTreeNode) value);
        editPanel.setData(cell);
        // 保证仅在编辑过程监控可变面板
        editPanel.attachDataListener(innerListener);
        return editPanel;
    }

    @Override
    public boolean stopCellEditing() {
        // 保证仅在编辑过程监控可变面板
        editPanel.detachDataListener(innerListener);
        return super.stopCellEditing();
    }

    @Override
    public void cancelCellEditing() {
        // 保证仅在编辑过程监控可变面板
        editPanel.detachDataListener(innerListener);
        super.cancelCellEditing();
    }

    @Override
    public D getCellEditorValue() {
        return editPanel.getData();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("UI")) {
            // 兼容换肤
            // 由于组件作为Renderer或者Editor时,不在组件树之中,所以需要触发updateUI.
            SwingUtilities.updateComponentTreeUI(renderPanel);
            SwingUtilities.updateComponentTreeUI(editPanel);
        }
    }

    /**
     * 触发格子监控器
     */
    protected void triggerCellListeners() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener listener : outerListeners) {
            listener.stateChanged(event);
        }
    }

    /**
     * 添加格子监控器
     * 
     * @param listener
     */
    public void attachCellListener(ChangeListener listener) {
        outerListeners.add(listener);
    }

    /**
     * 移除格子更监控器
     * 
     * @param listener
     */
    public void detachCellListener(ChangeListener listener) {
        outerListeners.remove(listener);
    }

}
