package com.jstarcraft.swing.cell;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * 列单元
 * 
 * @author Birdy
 *
 */
public class ListCell<T> implements DataCell<T> {

    private JList<T> list;

    private int index;

    public ListCell(JList<T> list, int index) {
        this.list = list;
        this.index = index;
    }

    @Override
    public boolean isSelected() {
        return list.isSelectedIndex(index);
    }

    @Override
    public boolean isEditing() {
        return false;
    }

    @Override
    public void startEditing(Runnable runable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void stopEditing(boolean cancel, Runnable runable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T getData() {
        return list.getModel().getElementAt(index);
    }

    @Override
    public void setData(T data) {
        DefaultListModel model = (DefaultListModel) list.getModel();
        model.setElementAt(data, index);
    }

    public int getIndex() {
        return index;
    }

}
