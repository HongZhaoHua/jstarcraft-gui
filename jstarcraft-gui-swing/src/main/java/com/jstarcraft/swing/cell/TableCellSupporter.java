package com.jstarcraft.swing.cell;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;
import java.util.LinkedHashSet;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jstarcraft.swing.component.CellPanel;

/**
 * 表单元格支撑器
 * 
 * @author Birdy
 *
 */
// 参考CheckBoxesInTableCell,MultipleButtonsInTableCell,RadioButtonsInTableCell,TableCellProgressBar,ComboCellEditor,ComboCellRenderer 
public class TableCellSupporter<D> extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, PropertyChangeListener {

    protected static final Logger logger = LoggerFactory.getLogger(TableCellSupporter.class);

    /** 渲染面板 */
    protected CellPanel<TableCell<D>, D> renderPanel;

    /** 编辑面板 */
    protected CellPanel<TableCell<D>, D> editPanel;

    protected ChangeListener innerListener = (event) -> {
        triggerChangeListeners();
    };

    protected LinkedHashSet<ChangeListener> outerListeners = new LinkedHashSet<>();

    public TableCellSupporter(CellPanel<TableCell<D>, D> renderPanel, CellPanel<TableCell<D>, D> editPanel) {
        this.renderPanel = renderPanel;
        this.editPanel = editPanel;
    }

    @Override
    public CellPanel<TableCell<D>, D> getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        TableCell<D> cell = new TableCell<>(table, row, column);
        renderPanel.setData(cell);
        return renderPanel;
    }

    @Override
    public boolean isCellEditable(EventObject event) {
        if (logger.isDebugEnabled()) {
            String message = event.toString();
            logger.debug(message);
        }

        if (event.getSource() instanceof JTable && event instanceof MouseEvent) {
            JTable table = (JTable) event.getSource();
            Point point = ((MouseEvent) event).getPoint();
            int row = table.rowAtPoint(point);
            int column = table.columnAtPoint(point);
            Rectangle bound = table.getCellRect(row, column, false);
            if (bound.contains(point)) {
                Object value = table.getValueAt(row, column);
                boolean selected = table.isCellSelected(row, column);
                point.translate(-bound.x, -bound.y);
                CellPanel panel = getTableCellRendererComponent(table, value, selected, true, row, column);
                // 因为渲染器与编辑器不在组件树之中,所以不能使用SwingUtilities.convertPoint
                panel.setLocation(0, 0);
                panel.setSize(bound.getSize());
                // 判断是否为可编辑组件
                Component component = SwingUtilities.getDeepestComponentAt(panel, point.x, point.y);
                return panel.isEditable(component);
            }
        }
        return false;
    }

    @Override
    public CellPanel<TableCell<D>, D> getTableCellEditorComponent(JTable table, Object value, boolean selected, int row, int column) {
        TableCell<D> cell = new TableCell<>(table, row, column);
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
     * 触发数据变更监控器
     */
    protected void triggerChangeListeners() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener listener : outerListeners) {
            listener.stateChanged(event);
        }
    }

    /**
     * 添加数据变更监控器
     * 
     * @param listener
     */
    public void attachChangeListener(ChangeListener listener) {
        outerListeners.add(listener);
    }

    /**
     * 移除数据变更监控器
     * 
     * @param listener
     */
    public void detachChangeListener(ChangeListener listener) {
        outerListeners.remove(listener);
    }

}
