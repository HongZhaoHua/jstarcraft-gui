package com.jstarcraft.swing.support.cell;

import javax.swing.JTable;

import com.jstarcraft.swing.support.SupportCell;

/**
 * 表单元
 * 
 * @author Birdy
 *
 */
public class TableCell<T> implements SupportCell<T> {

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
        runable.run();
    }

    @Override
    public void stopEdting(boolean cancel, Runnable runable) {
        if (cancel) {
            table.getCellEditor().cancelCellEditing();
        } else {
            table.getCellEditor().stopCellEditing();
        }
        runable.run();
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
        return 0;
    }

    public int getColumn() {
        return 0;
    }

}
