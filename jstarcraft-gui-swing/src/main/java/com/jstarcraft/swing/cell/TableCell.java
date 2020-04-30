package com.jstarcraft.swing.cell;

import javax.swing.JTable;

/**
 * 表单元格
 * 
 * @author Birdy
 *
 */
public class TableCell<T> implements DataCell<T> {

    private JTable table;

    private int row, column;

    public TableCell(JTable table, int row, int column) {
        this.table = table;
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean isSelected() {
        return table.isCellSelected(row, column);
    }

    @Override
    public boolean isEditing() {
        return table.getEditingRow() == row && table.getEditingColumn() == column;
    }

    @Override
    public void startEditing(Runnable runable) {
        table.editCellAt(row, column);
        if (runable != null) {
            runable.run();
        }
    }

    @Override
    public void stopEditing(boolean cancel, Runnable runable) {
        if (cancel) {
            table.getCellEditor().cancelCellEditing();
        } else {
            table.getCellEditor().stopCellEditing();
        }
        if (runable != null) {
            runable.run();
        }
    }

    @Override
    public T getData() {
        return (T) table.getValueAt(row, column);
    }

    @Override
    public void setData(T data) {
        if (isEditing()) {
            throw new IllegalStateException();
        }
        table.setValueAt(data, row, column);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

}
