package com.jstarcraft.swing.model;

import java.beans.PropertyDescriptor;
import java.util.Objects;

/**
 * 单实例表模型
 * 
 * @author Birdy
 *
 * @param <T>
 */
//参考PropertyTable
public class SingleInstanceTableModel<T> extends InstanceTableModel<T> {

    private T instance;

    public SingleInstanceTableModel(T instance, Class<T> clazz, String... names) {
        super(clazz, names);
        if (instance == null) {
            throw new NullPointerException();
        }
        this.instance = instance;
    }

    @Override
    public PropertyDescriptor getProperty(int rowIndex, int columnIndex) {
        PropertyDescriptor property = properties[rowIndex];
        return property;
    }

    @Override
    public int getRowCount() {
        return properties.length;
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        PropertyDescriptor property = properties[rowIndex];
        return property.getWriteMethod() != null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        try {
            PropertyDescriptor property = properties[rowIndex];
            property.getWriteMethod().invoke(instance, value);
            fireTableCellUpdated(rowIndex, columnIndex);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PropertyDescriptor property = properties[rowIndex];
        try {
            return property.getReadMethod().invoke(instance);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * 设置实例
     * 
     * @param instance
     */
    public void setInstance(T instance) {
        if (instance == null) {
            throw new NullPointerException();
        }
        if (!Objects.equals(this.instance, instance)) {
            this.instance = instance;
            fireTableRowsUpdated(0, properties.length - 1);
        }
    }

    /**
     * 获取实例
     * 
     * @return
     */
    public T getInstance() {
        return instance;
    }

}
