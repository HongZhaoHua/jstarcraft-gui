package com.jstarcraft.swing.model;

import java.beans.PropertyDescriptor;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import com.jstarcraft.core.common.reflection.ReflectionUtility;

/**
 * 属性表模型
 * 
 * @author Birdy
 *
 */
// 参考PropertyTable
public abstract class InstanceTableModel<T> extends AbstractTableModel {

    protected final Class<T> clazz;

    protected final PropertyDescriptor[] properties;

    protected InstanceTableModel(Class<T> clazz, String... names) {
        this.clazz = clazz;
        int size = names.length;
        Map<String, PropertyDescriptor> properties = ReflectionUtility.getPropertyDescriptors(clazz);
        this.properties = new PropertyDescriptor[size];
        for (int index = 0; index < size; index++) {
            PropertyDescriptor property = properties.get(names[index]);
            if (property == null) {
                throw new IllegalArgumentException();
            }
            this.properties[index] = property;
        }
    }

    public int countProperty() {
        return properties.length;
    }

    public PropertyDescriptor getProperty(int index) {
        return properties[index];
    }

    public abstract PropertyDescriptor getProperty(int rowIndex, int columnIndex);

}
