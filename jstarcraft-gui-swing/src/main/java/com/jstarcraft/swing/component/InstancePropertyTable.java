package com.jstarcraft.swing.component;

import java.beans.PropertyDescriptor;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.jstarcraft.swing.model.InstanceTableModel;

/**
 * 实例属性表
 * 
 * @author Birdy
 *
 * @param <T>
 */
// 参考PropertyTable
public class InstancePropertyTable<T> extends JTable {
    
    protected Map<PropertyDescriptor, TableCellRenderer> propertyRenderers;

    protected Map<PropertyDescriptor, TableCellEditor> propertyEditor;


    public InstancePropertyTable(InstanceTableModel<T> model) {
        super(model);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        TableColumn tableColumn = getColumnModel().getColumn(column);
        TableCellRenderer renderer = tableColumn.getCellRenderer();
        if (renderer == null) {
            int rowIndex = convertRowIndexToModel(row);
            int columnIndex = convertColumnIndexToModel(column);
            InstanceTableModel<T> model = (InstanceTableModel) dataModel;
            renderer = getDefaultRenderer(model.getProperty(rowIndex, columnIndex).getPropertyType());
        }
        return renderer;
    }

    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        TableColumn tableColumn = getColumnModel().getColumn(column);
        TableCellEditor editor = tableColumn.getCellEditor();
        if (editor == null) {
            int rowIndex = convertRowIndexToModel(row);
            int columnIndex = convertColumnIndexToModel(column);
            InstanceTableModel<T> model = (InstanceTableModel) dataModel;
            editor = getDefaultEditor(model.getProperty(rowIndex, columnIndex).getPropertyType());
        }
        return editor;
    }

}
