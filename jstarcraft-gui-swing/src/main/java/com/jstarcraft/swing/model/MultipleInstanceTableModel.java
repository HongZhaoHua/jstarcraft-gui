package com.jstarcraft.swing.model;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 多实例表模型
 * 
 * @author Birdy
 *
 * @param <T>
 */
// 参考PropertyTable
public class MultipleInstanceTableModel<T> extends InstanceTableModel<T> {

    private ArrayList<T> instances;

    public MultipleInstanceTableModel(Collection<T> instances, Class<T> clazz, String... names) {
        super(clazz, names);
        this.instances = new ArrayList<>(instances);
    }

    @Override
    public PropertyDescriptor getProperty(int rowIndex, int columnIndex) {
        PropertyDescriptor property = properties[columnIndex];
        return property;
    }

    @Override
    public int getRowCount() {
        return instances.size();
    }

    @Override
    public int getColumnCount() {
        return properties.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        PropertyDescriptor property = properties[columnIndex];
        return property.getWriteMethod() != null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        try {
            T instance = instances.get(rowIndex);
            PropertyDescriptor property = properties[columnIndex];
            property.getWriteMethod().invoke(instance, value);
            fireTableCellUpdated(rowIndex, columnIndex);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            T instance = instances.get(rowIndex);
            PropertyDescriptor property = properties[columnIndex];
            return property.getReadMethod().invoke(instance);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public T getInstance(int index) {
        return instances.get(index);
    }
    
    public void setInstance(int index, T instance) {
        instances.set(index, instance);
        fireTableRowsUpdated(index, index);
    }

    public void attachInstance(int index, T instance) {
        instances.add(index, instance);
        fireTableRowsInserted(index, index);
    }

    public void detachInstance(int index) {
        instances.remove(index);
        fireTableRowsDeleted(index, index);
    }

}
