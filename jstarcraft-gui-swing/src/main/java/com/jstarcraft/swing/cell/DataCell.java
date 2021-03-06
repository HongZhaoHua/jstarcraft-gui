package com.jstarcraft.swing.cell;

/**
 * 数据单元格
 * 
 * @author Birdy
 *
 */
public interface DataCell<T> {

    /**
     * 是否选择中
     * 
     * @return
     */
    boolean isSelected();

    /**
     * 是否编辑中
     * 
     * @return
     */
    boolean isEditing();

    /**
     * 启动编辑
     * 
     * @param runable
     */
    default void startEditing() {
        startEditing(null);
    }

    /**
     * 启动编辑
     * 
     * @param runable
     */
    void startEditing(Runnable runable);

    /**
     * 停止编辑
     * 
     * @param cancel
     * @param runable
     */
    default void stopEditing(boolean cancel) {
        stopEditing(cancel, null);
    }

    /**
     * 停止编辑
     * 
     * @param cancel
     * @param runable
     */
    void stopEditing(boolean cancel, Runnable runable);

    /**
     * 获取数据
     * 
     * @return
     */
    T getData();

    /**
     * 设置数据
     * 
     * @param data
     */
    void setData(T data);

}
